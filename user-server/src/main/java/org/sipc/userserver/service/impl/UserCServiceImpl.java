package org.sipc.userserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sipc.userserver.mapper.UserCMapper;
import org.sipc.userserver.pojo.domain.UserC;
import org.sipc.userserver.pojo.dto.CommonResult;
import org.sipc.userserver.pojo.dto.param.userC.CTokenVerifyParam;
import org.sipc.userserver.pojo.dto.param.userC.UserCLoginParam;
import org.sipc.userserver.pojo.dto.param.userC.UserCRegistParam;
import org.sipc.userserver.pojo.dto.result.userC.UserCLoginResult;
import org.sipc.userserver.service.UserCService;
import org.sipc.userserver.util.CheckRoleUtil;
import org.sipc.userserver.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserCServiceImpl implements UserCService {

    private final UserCMapper userCMapper;
    /**
     * B 端用户登录
     *
     * @param param 用户名、密码
     * @return 用户ID、用户名、token
     */
    @Override
    public CommonResult<UserCLoginResult> login(UserCLoginParam param) {
        UserC user = userCMapper.selectOne(new QueryWrapper<UserC>().eq("user", param.getUsername()));
        if (user == null){
            return CommonResult.fail("用户名或密码错误");
        }
        if (!PasswordUtil.verifyCPassword(param.getPassword(), user.getPassword())){
            return CommonResult.fail("用户名或密码错误");
        }
        UserCLoginResult result = new UserCLoginResult();
        result.setUserId(user.getId());
        result.setUsername(user.getName());
        result.setToken(CheckRoleUtil.createCToken(user));
        return CommonResult.success(result);
    }

    /**
     * B 端用户注册
     *
     * @param param 用户名与密码
     * @return 注册结果
     */
    @Override
    public CommonResult<String> register(UserCRegistParam param) {
        UserC user = userCMapper.selectOne(new QueryWrapper<UserC>().eq("user", param.getUsername()));
        if (user != null){
            return CommonResult.fail("用户也已存在");
        }
        user = new UserC();
        user.setName(param.getUsername());
        user.setPassword(PasswordUtil.getCPassword(param.getPassword()));
        userCMapper.insert(user);
        return CommonResult.success();
    }

    /**
     * B 端 Token 校验
     *
     * @param param Token
     * @return 用户信息
     */
    @Override
    public CommonResult<UserC> verifyToken(CTokenVerifyParam param) {
        UserC userC = CheckRoleUtil.verifyCToken(param.getToken());
        if (userC == null){
            return CommonResult.userAuthError();
        }
        UserC userC1 = userCMapper.selectById(userC.getId());
        if (!Objects.equals(userC1.getName(), userC.getName())){
            return CommonResult.userAuthError();
        }
        return CommonResult.success(userC);
    }
}
