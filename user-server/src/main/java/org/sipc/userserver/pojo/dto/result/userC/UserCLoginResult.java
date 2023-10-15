package org.sipc.userserver.pojo.dto.result.userC;

import lombok.Data;

@Data
public class UserCLoginResult {
    private Integer userId;
    private String username;
    private String token;
    private Integer credit;
}
