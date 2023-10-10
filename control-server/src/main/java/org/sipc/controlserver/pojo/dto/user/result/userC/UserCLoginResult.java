package org.sipc.controlserver.pojo.dto.user.result.userC;

import lombok.Data;

@Data
public class UserCLoginResult {
    private Integer userId;
    private String username;
    private String token;
    private Integer credit;
}
