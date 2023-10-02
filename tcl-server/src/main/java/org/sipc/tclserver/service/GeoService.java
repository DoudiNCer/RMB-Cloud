package org.sipc.tclserver.service;

import org.sipc.tclserver.pojo.dto.CommonResult;
import org.sipc.tclserver.pojo.dto.result.GeoResult;

/**
 * @author tzih
 * @version v1.0
 * @since 2023.10.02
 */
public interface GeoService {

    CommonResult<GeoResult> geoAll();

}
