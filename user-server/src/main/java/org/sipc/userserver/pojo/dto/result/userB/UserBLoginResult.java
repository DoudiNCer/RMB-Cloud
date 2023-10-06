package org.sipc.userserver.pojo.dto.result.userB;

import lombok.Data;

@Data
public class UserBLoginResult {
    private Integer userId;
    private String username;
    private String token;
}
