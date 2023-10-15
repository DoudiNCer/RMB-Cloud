package org.sipc.controlserver.pojo.dto.tcl.result.po;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author tzih
 * @version v1.0
 * @since 2023.10.06
 */
@Data
@AllArgsConstructor
public class GarbageSortPo {

    private Integer garbageId;

    private String name;

    private Integer recyclable;

    private Integer notRecyclable;

    private Integer harmful;

    private Integer foodWaste;

    public GarbageSortPo() {

    }

}
