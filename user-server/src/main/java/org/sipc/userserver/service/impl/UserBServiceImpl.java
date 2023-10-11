package org.sipc.userserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.sipc.controlserver.pojo.dto.CommonResult;
import org.sipc.controlserver.pojo.dto.user.param.userB.UserBLoginParam;
import org.sipc.controlserver.pojo.dto.user.result.userB.UserBLoginResult;
import org.sipc.controlserver.service.user.UserBService;
import org.sipc.userserver.mapper.UserBMapper;
import org.sipc.userserver.pojo.domain.UserB;
import org.sipc.userserver.util.CheckRoleUtil;
import org.sipc.userserver.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@DubboService
public class UserBServiceImpl implements UserBService {

    private final UserBMapper userBMapper;
    /**
     * B 端用户登录
     *
     * @param param 用户名、密码
     * @return 用户ID、用户名、token
     */
    @Override
    public CommonResult<UserBLoginResult> login(UserBLoginParam param) {
        UserB user = userBMapper.selectOne(new QueryWrapper<UserB>().eq("name", param.getUsername()));
        if (user == null){
            return CommonResult.fail("用户名或密码错误");
        }
        if (!PasswordUtil.verifyBPassword(param.getPassword(), user.getPassword())){
            return CommonResult.fail("用户名或密码错误");
        }
        UserBLoginResult result = new UserBLoginResult();
        result.setUserId(user.getId());
        result.setUsername(user.getName());
        result.setToken(CheckRoleUtil.createBToken(user));
        return CommonResult.success(result);
    }

    /**
     * B 端 Token 校验
     *
     * @param param Token
     * @return 用户信息
     */
//    @Override
//    public CommonResult<UserB> verifyToken(BTokenVerifyParam param) {
//        UserB userB = CheckRoleUtil.verifyBToken(param.getToken());
//        if (userB == null){
//            return CommonResult.userAuthError();
//        }
//        UserB userB1 = userBMapper.selectById(userB.getId());
//        if (!Objects.equals(userB1.getName(), userB.getName())){
//            return CommonResult.userAuthError();
//        }
//        return CommonResult.success(userB);
//    }

}
