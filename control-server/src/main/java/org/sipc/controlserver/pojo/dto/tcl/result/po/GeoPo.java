package org.sipc.controlserver.pojo.dto.tcl.result.po;

import lombok.Data;

import java.util.List;

/**
 * @author tzih
 * @version v1.0
 * @since 2023.10.02
 */
@Data
public class GeoPo {

    private Integer id;

    private String content;

    private Integer type;

    private List<GeoPo> geoPoList;

}
