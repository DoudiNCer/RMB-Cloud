package org.sipc.controlserver.controller;

import org.sipc.controlserver.pojo.dto.tcl.result.UploadResult;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
 * @since 2023.10.11
 */
@RestController
public class UploadController {

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        // 检查文件是否为空
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a file to upload.");
        }

        try {
            work(file);
        } catch (Exception e) {
            System.out.println("出异常了");
        }
        // 在实际应用中，您可以将文件保存到服务器或进行其他操作
        // 这里只是简单地返回一个成功响应
        return ResponseEntity.ok("File uploaded successfully: " + file.getOriginalFilename());
    }

    private void work(MultipartFile multipartFile) throws Exception {

        if (multipartFile == null) {
            return;
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
