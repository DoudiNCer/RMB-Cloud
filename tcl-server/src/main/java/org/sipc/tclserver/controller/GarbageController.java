package org.sipc.tclserver.controller;

import lombok.RequiredArgsConstructor;
import org.sipc.tclserver.pojo.dto.CommonResult;
import org.sipc.tclserver.pojo.dto.result.GarbageAllResult;
import org.sipc.tclserver.service.GarbageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tzih
 * @version v1.0
 * @since 2023.10.02
 */
@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class GarbageController {

    private final GarbageService garbageService;

    @GetMapping("/tcl/garbage/all")
    public CommonResult<GarbageAllResult> all(@RequestParam Integer type, @RequestParam Integer id) {
        return garbageService.all(type, id);
    }

}
