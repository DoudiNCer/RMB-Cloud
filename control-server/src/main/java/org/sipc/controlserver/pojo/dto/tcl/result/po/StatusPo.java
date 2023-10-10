package org.sipc.controlserver.pojo.dto.tcl.result.po;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author tzih
 * @version v1.0
 * @since 2023.10.06
 */
@Data
public class StatusPo {

    private Integer normal;

    private Integer fault;

    public StatusPo() {

    }

    public StatusPo(int normal, int fault) {
        this.normal = normal;
        this.fault = fault;
    }

}
