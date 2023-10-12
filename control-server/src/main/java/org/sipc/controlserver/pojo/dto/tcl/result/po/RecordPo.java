package org.sipc.controlserver.pojo.dto.tcl.result.po;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author tzih
 * @version v1.0
 * @since 2023.10.11
 */
@Data
public class RecordPo {

    private Integer id;

    private String url;

    private Integer type;

    private String time;

}
