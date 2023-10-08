package org.sipc.tclserver.pojo.dto.result;

import lombok.Data;
import org.sipc.tclserver.pojo.dto.result.po.GarbagePo;

import java.util.List;

/**
 * @author tzih
 * @version v1.0
 * @since 2023.10.02
 */
@Data
public class GarbageAllResult {

    private List<GarbagePo> garbageList;

}
