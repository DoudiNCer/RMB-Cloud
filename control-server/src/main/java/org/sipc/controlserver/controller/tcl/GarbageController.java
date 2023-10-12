package org.sipc.controlserver.controller.tcl;

import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.sipc.controlserver.exection.DateBaseException;
import org.sipc.controlserver.pojo.dto.CommonResult;
import org.sipc.controlserver.pojo.dto.tcl.param.EditTrashParam;
import org.sipc.controlserver.pojo.dto.tcl.param.GarbageAllParam;
import org.sipc.controlserver.pojo.dto.tcl.param.VerifyParam;
import org.sipc.controlserver.pojo.dto.tcl.result.*;
import org.sipc.controlserver.service.tcl.GarbageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public CommonResult<UploadResult> identify(@RequestParam("file") MultipartFile file) {
        return garbageService.identify(file);
    }
}
