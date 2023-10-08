package org.sipc.tclserver.pojo.dto.result;

import lombok.Data;
import org.sipc.tclserver.pojo.dto.result.po.GarbageSortPo;
import org.sipc.tclserver.pojo.dto.result.po.GarbageUsePo;
import org.sipc.tclserver.pojo.dto.result.po.StatusPo;

import java.util.List;

/**
 * @author tzih
 * @version v1.0
 * @since 2023.10.06
 */
@Data
public class DataResult {

    private Integer districtId;

    private List<GarbageSortPo> detailList;

    private GarbageSortPo useNum;

    private StatusPo status;

    private List<GarbageUsePo> garbageUseList;
}
