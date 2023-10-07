package org.sipc.userserver.service;

import org.sipc.userserver.pojo.domain.UserB;
import org.sipc.userserver.pojo.dto.CommonResult;
import org.sipc.userserver.pojo.dto.param.userB.BTokenVerifyParam;
import org.sipc.userserver.pojo.dto.param.userB.UserBLoginParam;
import org.sipc.userserver.pojo.dto.result.userB.UserBLoginResult;

public interface UserBService {
    /**
     * B 端用户登录
     *
     * @param param 用户名、密码
     * @return 用户ID、用户名、token
     */
    CommonResult<UserBLoginResult> login(UserBLoginParam param);

    /**
     * B 端 Token 校验
     *
     * @param param Token
     * @return 用户信息
     */
    CommonResult<UserB> verifyToken(BTokenVerifyParam param);
}
