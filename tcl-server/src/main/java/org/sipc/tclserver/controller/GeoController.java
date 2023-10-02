package org.sipc.tclserver.controller;

import lombok.RequiredArgsConstructor;
import org.sipc.tclserver.pojo.dto.CommonResult;
import org.sipc.tclserver.pojo.dto.result.GeoResult;
import org.sipc.tclserver.service.GeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tzih
 * @version v1.0
 * @since 2023.10.02
 */
@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class GeoController {

    private final GeoService geoService;

    @GetMapping("/tcl/geography/all")
    public CommonResult<GeoResult> geoAll() {
        return geoService.geoAll();
    }

}
