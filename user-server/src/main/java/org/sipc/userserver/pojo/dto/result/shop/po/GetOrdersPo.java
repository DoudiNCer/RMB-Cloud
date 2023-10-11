package org.sipc.userserver.pojo.dto.result.shop.po;

import lombok.Data;

@Data
public class GetOrdersPo {
    private Integer id;
    private String user;
    private String gift;
    private String receiver;
    private String phone;
    private String address;
    private Boolean finished;
}
