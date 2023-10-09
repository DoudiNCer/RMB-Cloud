package org.sipc.controlserver.service.tcl;

import org.sipc.controlserver.exection.DateBaseException;
import org.sipc.controlserver.pojo.dto.CommonResult;
import org.sipc.controlserver.pojo.dto.tcl.param.EditTrashParam;
import org.sipc.controlserver.pojo.dto.tcl.param.GarbageAllParam;
import org.sipc.controlserver.pojo.dto.tcl.param.VerifyParam;
import org.sipc.controlserver.pojo.dto.tcl.result.DataResult;
import org.sipc.controlserver.pojo.dto.tcl.result.GarbageAllResult;
import org.sipc.controlserver.pojo.dto.tcl.result.VerifyResult;

public interface GarbageService {

    CommonResult<GarbageAllResult> all(Integer type, Integer id);

    CommonResult<String> add(GarbageAllParam garbageAllParam);

    CommonResult<String> edit(EditTrashParam editTrashParam) throws DateBaseException;

    CommonResult<String> delete(Integer garbageId) throws DateBaseException;

    CommonResult<DataResult> data(Integer districtId);

    CommonResult<String> getCheckinQRCode(Integer garbageId);

    CommonResult<VerifyResult> verifyQRCode(VerifyParam verifyParam);

}
