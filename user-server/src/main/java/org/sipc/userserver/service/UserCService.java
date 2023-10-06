package org.sipc.userserver.service;

import org.sipc.userserver.pojo.domain.UserC;
import org.sipc.userserver.pojo.dto.CommonResult;
import org.sipc.userserver.pojo.dto.param.userC.CTokenVerifyParam;
import org.sipc.userserver.pojo.dto.param.userC.UserCLoginParam;
import org.sipc.userserver.pojo.dto.param.userC.UserCRegistParam;
import org.sipc.userserver.pojo.dto.result.userC.UserCLoginResult;

public interface UserCService {
    /**
     * B 端用户登录
     *
     * @param param 用户名、密码
     * @return 用户ID、用户名、token
     */
    CommonResult<UserCLoginResult> login(UserCLoginParam param);

    /**
     * B 端用户注册
     *
     * @param param 用户名与密码
     * @return 注册结果
     */
    CommonResult<String> register(UserCRegistParam param);

    /**
     * B 端 Token 校验
     *
     * @param param Token
     * @return 用户信息
     */
    CommonResult<UserC> verifyToken(CTokenVerifyParam param);
}
