package org.sipc.tclserver.pojo.dto.param;

import lombok.Data;

/**
 * @author tzih
 * @version v1.0
 * @since 2023.10.08
 */
@Data
public class EditTrashParam {

    private Integer id;

    private String name;

    private String location;

    private Double latitude;

    private Double longitude;
}
