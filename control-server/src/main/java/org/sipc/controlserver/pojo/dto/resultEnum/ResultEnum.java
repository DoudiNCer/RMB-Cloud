package org.sipc.controlserver.pojo.dto.resultEnum;

import lombok.Getter;

@Getter
public enum ResultEnum {

    SUCCESS("00000", "请求正常"), FAILED("A0400", "请求失败"),
    AUTH_ERROR("A0300", "访问权限异常");

    private final String code;
    private final String message;

    ResultEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
