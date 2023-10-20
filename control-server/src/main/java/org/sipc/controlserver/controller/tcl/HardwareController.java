package org.sipc.controlserver.controller.tcl;

import org.apache.dubbo.config.annotation.DubboReference;
import org.sipc.controlserver.pojo.dto.CommonResult;
import org.sipc.controlserver.pojo.dto.tcl.param.UploadPhotoParam;
import org.sipc.controlserver.service.tcl.HardwareService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HardwareController {
    @DubboReference
    HardwareService hardwareService;

    @PostMapping("/hardware/photo")
    public CommonResult<String> uploadPhoto(@RequestBody UploadPhotoParam param){
        hardwareService.uploadPhoto(param);
        return CommonResult.success();
    }
}
