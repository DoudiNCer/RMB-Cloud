package org.sipc.tclserver.service;

import org.sipc.tclserver.pojo.dto.CommonResult;
import org.sipc.tclserver.pojo.dto.param.GarbageAllParam;
import org.sipc.tclserver.pojo.dto.result.GarbageAllResult;
import org.springframework.web.bind.annotation.RequestBody;

public interface GarbageService {

    CommonResult<GarbageAllResult> all(Integer type, Integer id);

    CommonResult<String> add(GarbageAllParam garbageAllParam);

}
