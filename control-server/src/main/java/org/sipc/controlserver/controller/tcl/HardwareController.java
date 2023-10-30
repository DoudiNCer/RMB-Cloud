package org.sipc.controlserver.controller.tcl;

import org.apache.http.entity.ContentType;
import org.sipc.controlserver.controller.WebSockerController;
import org.sipc.controlserver.pojo.dto.CommonResult;
import org.sipc.controlserver.pojo.dto.resultEnum.ResultEnum;
import org.sipc.controlserver.pojo.dto.tcl.param.UploadPhotoParam;
import org.sipc.controlserver.pojo.dto.tcl.result.UploadResult;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

@RestController
public class HardwareController {
    public static Map<String, Integer> garbageType = new HashMap<>(4);
    static {
        garbageType.put("可回收垃圾", 0);
        garbageType.put("有害垃圾", 1);
        garbageType.put("厨余垃圾", 2);
        garbageType.put("其他垃圾", 3);
    }
    @Resource
    GarbageController garbageController;

    @Resource
    WebSockerController webSockerController;

    @PostMapping("/hardware/photo")
    public CommonResult<String> uploadPhoto(@RequestBody UploadPhotoParam param){
        byte[] decode = Base64.getDecoder().decode(param.getPhoto());
        MultipartFile file = null;
        try {
            file = new MockMultipartFile(ContentType.APPLICATION_OCTET_STREAM.toString(), new ByteArrayInputStream(decode));
        } catch (IOException ignored) {

        }
        if (file == null){
            return CommonResult.fail("");
        }
        CommonResult<UploadResult> identify = null;
        try {
            identify = garbageController.identify(1, 1, file);
        } catch (Exception ignored) {

        }
        if (identify == null || !Objects.equals(identify.getCode(), ResultEnum.SUCCESS.getCode()) || identify.getData() == null){
            return CommonResult.fail("");
        }
        Set<Integer> result = new HashSet<>(4);
        for (String s : identify.getData().getList()) {
            if (garbageType.get(s) != null) {
                result.add(garbageType.get(s));
            }
        }
        StringBuilder sb = new StringBuilder();
        for (Object o : result.toArray()) {
            sb.append(o);
        }
        webSockerController.sendOneMessage("1", sb.toString());
        return CommonResult.success();
    }
}
