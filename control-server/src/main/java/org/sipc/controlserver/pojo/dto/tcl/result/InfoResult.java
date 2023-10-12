package org.sipc.controlserver.pojo.dto.tcl.result;

import lombok.Data;
import org.sipc.controlserver.pojo.dto.tcl.result.po.RecordPo;

import java.util.List;

/**
 * @author tzih
 * @version v1.0
 * @since 2023.10.11
 */
@Data
public class InfoResult {

    private String name;

    private List<RecordPo> recordPoList;

}
