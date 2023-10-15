package org.sipc.controlserver.service.tcl;

import org.sipc.controlserver.pojo.dto.CommonResult;
import org.sipc.controlserver.pojo.dto.tcl.result.GeoResult;

/**
 * @author tzih
 * @version v1.0
 * @since 2023.10.02
 */
public interface GeoService {

    CommonResult<GeoResult> geoAll();

}
