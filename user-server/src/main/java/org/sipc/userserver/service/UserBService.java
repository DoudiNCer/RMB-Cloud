package org.sipc.userserver.service;

import org.sipc.userserver.pojo.domain.UserB;
import org.sipc.userserver.pojo.dto.CommonResult;
import org.sipc.userserver.pojo.dto.param.userB.BTokenVerifyParam;
import org.sipc.userserver.pojo.dto.param.userB.UserBLoginParam;
import org.sipc.userserver.pojo.dto.param.userB.UserBRegistParam;
import org.sipc.userserver.pojo.dto.result.userB.UserBLoginResult;

public interface UserBService {
    /**
     * C 端用户登录
     *
     * @param param 用户名、密码
     * @return 用户ID、用户名、token
     */
    CommonResult<UserBLoginResult> login(UserBLoginParam param);

    /**
     * C 端用户注册
     *
     * @param param 用户名与密码
     * @return 注册结果
     */
    CommonResult<String> register(UserBRegistParam param);

    /**
     * C 端 Token 校验
     *
     * @param param Token
     * @return 用户信息
     */
    CommonResult<UserB> verifyToken(BTokenVerifyParam param);
}
