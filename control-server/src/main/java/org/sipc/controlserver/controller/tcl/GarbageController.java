package org.sipc.controlserver.controller.tcl;

import org.apache.dubbo.config.annotation.DubboReference;
import org.sipc.controlserver.exection.DateBaseException;
import org.sipc.controlserver.pojo.dto.CommonResult;
import org.sipc.controlserver.pojo.dto.resultEnum.ResultEnum;
import org.sipc.controlserver.pojo.dto.tcl.param.EditTrashParam;
import org.sipc.controlserver.pojo.dto.tcl.param.GarbageAllParam;
import org.sipc.controlserver.pojo.dto.tcl.param.VerifyParam;
import org.sipc.controlserver.pojo.dto.tcl.result.*;
import org.sipc.controlserver.service.tcl.GarbageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author tzih
 * @version v1.0
 * @since 2023.10.02
 */
@RestController
//@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping("/garbage")
public class GarbageController {

    @DubboReference
    private GarbageService garbageService;

    @GetMapping("/all")
    public CommonResult<GarbageAllResult> all(@RequestParam Integer type, @RequestParam Integer id) {
        return garbageService.all(type, id);
    }

    @PostMapping("/add")
    public CommonResult<String> add(@RequestBody GarbageAllParam garbageAllParam) {
        return garbageService.add(garbageAllParam);
    }

    @PostMapping("/edit")
    public CommonResult<String> edit(@RequestBody EditTrashParam editTrashParam) {
        try {
            return garbageService.edit(editTrashParam);
        } catch (DateBaseException e) {
            return CommonResult.fail("请求异常，请稍后再试");
        }
    }

    @PostMapping("/delete")
    public CommonResult<String> delete(@RequestParam Integer garbageId) {
        try {
            return garbageService.delete(garbageId);
        } catch (DateBaseException e) {
            return CommonResult.fail("请求异常，请稍后再试");
        }
    }

    @GetMapping("/data")
    public CommonResult<DataResult> data(@RequestParam Integer districtId) {
        return garbageService.data(districtId);
    }

    @GetMapping("/qrcode")
    public CommonResult<String> getCheckinQRCode(@RequestParam Integer garbageId) {
        return garbageService.getCheckinQRCode(garbageId);
    }

    @PostMapping("/verify")
    public CommonResult<VerifyResult> verifyQRCode(@RequestBody VerifyParam verifyParam) {
        return garbageService.verifyQRCode(verifyParam);
    }

    @GetMapping("/test")
    public CommonResult<String> test() throws Exception {
        return garbageService.test();
    }

    @GetMapping("/info")
    public CommonResult<InfoResult> info(@RequestParam Integer garbageId) {
        return garbageService.info(garbageId);
    }

    @PostMapping("/identify")
    public CommonResult<UploadResult> identify(@RequestParam("garbageId") Integer garbageId, @RequestParam("userId") Integer userId, @RequestParam("file") MultipartFile file) throws Exception {

        // 检查文件是否为空
        if (file.isEmpty()) {
//            return ResponseEntity.badRequest().body("Please select a file to upload.");
            return CommonResult.fail("传参错误，图片不存在");
        }

//        Garbage garbage = garbageMapper.selectById(garbageId);
        Boolean isGarbage = garbageService.isGarbage(garbageId);
        if (!isGarbage) {
            return CommonResult.fail("垃圾桶不存在");
        }

        UploadResult result = new UploadResult();
        try {
            result = work(file);
        } catch (Exception e) {
            System.out.println("出异常了");
//            throw new RuntimeException("服务器执行错误");
            throw e;
        }
        // 在实际应用中，您可以将文件保存到服务器或进行其他操作
        // 这里只是简单地返
        if (result != null && !result.getList().isEmpty()) {
            CommonResult<UploadResult> identify = garbageService.identify(garbageId, userId, result.getUrl(), result.getList().get(0));

            if (identify != null ) {
                if (identify.getCode().equals(ResultEnum.FAILED.getCode())) {
                    return CommonResult.fail("请求失败，请稍后再试");
                }
            } else {
                return CommonResult.fail("请求失败，请稍后再试");
            }

            return CommonResult.success(result);
        } else if (result != null){

            CommonResult<UploadResult> identify = garbageService.identify(garbageId, userId, result.getUrl(), null);

            if (identify != null ) {
                if (identify.getCode().equals(ResultEnum.FAILED.getCode())) {
                    return CommonResult.fail("请求失败，请稍后再试");
                }
            } else {
                return CommonResult.fail("请求失败，请稍后再试");
            }

            return CommonResult.success(result);

        } else {
            return CommonResult.fail("请求失败，请稍后再试");
        }

//        return garbageService.identify(garbageId, userId, file);
    }

    private UploadResult work(MultipartFile multipartFile) throws Exception {

        if (multipartFile == null) {
            return null;
        }

        // 创建一个RestTemplate实例
        RestTemplate restTemplate = new RestTemplate();

//        // 设置文件路径
//        String filePath = "/path/to/your/file.txt";

        // 创建HTTP头部
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // 创建HTTP请求实体
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

//        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename())); // 创建临时文件
//        multipartFile.transferTo(file); // 将MultipartFile的内容写入文件

        // 将MultipartFile的内容读取为字节数组
//        byte[] fileBytes = multipartFile.getBytes();

        File file = multipartFileToFile(multipartFile);
        if (file != null) {
            body.add("file", new FileSystemResource(file));
        }

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // 设置目标服务器的URL
        String serverUrl = "http://127.0.0.1:5000/upload"; // 服务器的地址

        // 发送文件上传请求
        ResponseEntity<UploadResult> response = restTemplate.postForEntity(serverUrl, requestEntity, UploadResult.class);

        System.out.println(Objects.requireNonNull(response.getBody()).getUrl() + "\n" + Arrays.toString(response.getBody().getList().toArray()));
        // 处理响应
        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("File uploaded successfully.");
        } else {
            System.err.println("File upload failed with status code: " + response.getStatusCodeValue());
        }

        return response.getBody();
    }


    private File multipartFileToFile(MultipartFile file) throws Exception {

        File toFile = null;
        if (file.isEmpty() || file.getSize() <= 0) {
            return null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
//            inputStreamToFile(ins, toFile);
            copyInputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    private void copyInputStreamToFile(InputStream inputStream, File file)
            throws IOException {

        try (FileOutputStream outputStream = new FileOutputStream(file)) {

            int read;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }

            // commons-io
            //IOUtils.copy(inputStream, outputStream);

        }

    }
}
