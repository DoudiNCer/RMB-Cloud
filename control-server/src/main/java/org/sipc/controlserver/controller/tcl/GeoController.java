package org.sipc.controlserver.controller.tcl;

import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.sipc.controlserver.pojo.dto.CommonResult;
import org.sipc.controlserver.pojo.dto.tcl.result.GeoResult;
import org.sipc.controlserver.service.tcl.GeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tzih
 * @version v1.0
 * @since 2023.10.02
 */
@RestController
//@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class GeoController {

    @DubboReference
    private GeoService geoService;

    @GetMapping("/geography/all")
    public CommonResult<GeoResult> geoAll() {
        return geoService.geoAll();
    }

}
