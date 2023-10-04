package org.sipc.tclserver.service;

import org.sipc.tclserver.pojo.dto.CommonResult;
import org.sipc.tclserver.pojo.dto.result.GarbageAllResult;

public interface GarbageService {

    CommonResult<GarbageAllResult> all(Integer type, Integer id);

}
