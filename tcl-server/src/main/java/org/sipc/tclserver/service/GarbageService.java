package org.sipc.tclserver.service;

import org.sipc.tclserver.exection.DateBaseException;
import org.sipc.tclserver.pojo.dto.CommonResult;
import org.sipc.tclserver.pojo.dto.param.EditTrashParam;
import org.sipc.tclserver.pojo.dto.param.GarbageAllParam;
import org.sipc.tclserver.pojo.dto.param.VerifyParam;
import org.sipc.tclserver.pojo.dto.result.DataResult;
import org.sipc.tclserver.pojo.dto.result.GarbageAllResult;
import org.sipc.tclserver.pojo.dto.result.VerifyResult;
import org.springframework.web.bind.annotation.RequestBody;

public interface GarbageService {

    CommonResult<GarbageAllResult> all(Integer type, Integer id);

    CommonResult<String> add(GarbageAllParam garbageAllParam);

    CommonResult<String> edit(EditTrashParam editTrashParam) throws DateBaseException;

    CommonResult<String> delete(Integer garbageId) throws DateBaseException;

    CommonResult<DataResult> data(Integer districtId);

    CommonResult<String> getCheckinQRCode(Integer garbageId);

    CommonResult<VerifyResult> verifyQRCode(VerifyParam verifyParam);

}
