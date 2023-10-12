package org.sipc.controlserver.service.user;

import org.sipc.controlserver.pojo.dto.CommonResult;
import org.sipc.controlserver.pojo.dto.user.param.userC.UserCLoginParam;
import org.sipc.controlserver.pojo.dto.user.param.userC.UserCRegistParam;
import org.sipc.controlserver.pojo.dto.user.result.userC.CreditResult;
import org.sipc.controlserver.pojo.dto.user.result.userC.SearchResult;
import org.sipc.controlserver.pojo.dto.user.result.userC.UserCLoginResult;

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

    CommonResult<SearchResult> search(String content);

    CommonResult<CreditResult> credit(Integer userId);
}
