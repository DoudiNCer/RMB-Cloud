package org.sipc.tclserver.pojo.dto.result.po;

import lombok.Data;

/**
 * @author tzih
 * @version v1.0
 * @since 2023.10.02
 */
@Data
public class GarbagePo {

    private Integer id;

    private Integer districtId;

    private String district;

    private String content;

    private String createTime;

    private String location;

    private Double latitude;

    private Double longitude;

    private Integer status;

}
