package org.sipc.controlserver.service.user;

import org.sipc.controlserver.pojo.dto.CommonResult;
import org.sipc.controlserver.pojo.dto.user.param.userB.UserBLoginParam;
import org.sipc.controlserver.pojo.dto.user.result.userB.UserBLoginResult;

public interface UserBService {
    /**
     * B 端用户登录
     *
     * @param param 用户名、密码
     * @return 用户ID、用户名、token
     */
    CommonResult<UserBLoginResult> login(UserBLoginParam param);
}
