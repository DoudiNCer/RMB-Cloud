package org.sipc.controlserver.pojo.dto.user.result.userB;

import lombok.Data;

@Data
public class UserBLoginResult {
    private Integer userId;
    private String username;
    private String token;
}
