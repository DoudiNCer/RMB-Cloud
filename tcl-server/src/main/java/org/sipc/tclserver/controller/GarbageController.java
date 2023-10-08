package org.sipc.tclserver.controller;

import  lombok.RequiredArgsConstructor;
import org.sipc.tclserver.exection.DateBaseException;
import org.sipc.tclserver.pojo.dto.CommonResult;
import org.sipc.tclserver.pojo.dto.param.EditTrashParam;
import org.sipc.tclserver.pojo.dto.param.GarbageAllParam;
import org.sipc.tclserver.pojo.dto.result.DataResult;
import org.sipc.tclserver.pojo.dto.result.GarbageAllResult;
import org.sipc.tclserver.service.GarbageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author tzih
 * @version v1.0
 * @since 2023.10.02
 */
@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping("/tcl/garbage")
public class GarbageController {

    private final GarbageService garbageService;

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
}
