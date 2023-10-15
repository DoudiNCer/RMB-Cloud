package org.sipc.userserver.pojo.dto.param.shop;

import lombok.Data;

@Data
public class ConventGiftParam {
//    @NotNull(message = "用户 ID 不能为空")
    private Integer userId;
//    @NotNull(message = "礼品 ID 不能为空")
    private Integer giftId;
//    @NotBlank(message = "收件人不能为空")
    private String receiver;
//    @NotBlank(message = "收件地址不能为空")
    private String address;
//    @NotBlank(message = "收件人电话不能为空")
    private String phone;
}