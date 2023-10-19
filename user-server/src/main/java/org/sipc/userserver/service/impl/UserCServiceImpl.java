package org.sipc.userserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.sipc.controlserver.pojo.dto.CommonResult;
import org.sipc.controlserver.pojo.dto.user.param.userC.CTokenVerifyParam;
import org.sipc.controlserver.pojo.dto.user.param.userC.UserCLoginParam;
import org.sipc.controlserver.pojo.dto.user.param.userC.UserCRegistParam;
import org.sipc.controlserver.pojo.dto.user.result.userC.CreditResult;
import org.sipc.controlserver.pojo.dto.user.result.userC.SearchResult;
import org.sipc.controlserver.pojo.dto.user.result.userC.UserCLoginResult;
import org.sipc.controlserver.service.user.UserCService;
import org.sipc.userserver.mapper.GarbageSortMapper;
import org.sipc.userserver.mapper.UserCMapper;
import org.sipc.userserver.pojo.domain.GarbageSort;
import org.sipc.userserver.pojo.domain.UserC;
import org.sipc.userserver.util.CheckRoleUtil;
import org.sipc.userserver.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@DubboService
public class UserCServiceImpl implements UserCService {

    private final UserCMapper userCMapper;

    private final GarbageSortMapper garbageSortMapper;
    /**
     * B 端用户登录
     *
     * @param param 用户名、密码
     * @return 用户ID、用户名、token
     */
    @Override
    public CommonResult<UserCLoginResult> login(UserCLoginParam param) {
        UserC user = userCMapper.selectOne(new QueryWrapper<UserC>().eq("name", param.getUsername()));
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
        result.setCredit(user.getCredit());
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
        UserC user = userCMapper.selectOne(new QueryWrapper<UserC>().eq("name", param.getUsername()));
        if (user != null){
            return CommonResult.fail("用户已存在");
        }
        user = new UserC();
        user.setName(param.getUsername());
        user.setPassword(PasswordUtil.getCPassword(param.getPassword()));
        userCMapper.insert(user);
        return CommonResult.success("请求成功");
    }


//    /**
//     * B 端 Token 校验
//     *
//     * @param param Token
//     * @return 用户信息
//     */
//    @Override
//    public CommonResult<UserC> verifyToken(CTokenVerifyParam param) {
//        UserC userC = CheckRoleUtil.verifyCToken(param.getToken());
//        if (userC == null){
//            return CommonResult.userAuthError();
//        }
//        UserC userC1 = userCMapper.selectById(userC.getId());
//        if (!Objects.equals(userC1.getName(), userC.getName())){
//            return CommonResult.userAuthError();
//        }
//        userC.setCredit(userC1.getCredit());
//        return CommonResult.success(userC);
//    }


    @Override
    public CommonResult<SearchResult> search(String content) {

        GarbageSort garbageSort = garbageSortMapper.selectOne(new QueryWrapper<GarbageSort>().like("content", content).last("limit 1"));

        SearchResult result = new SearchResult();

        if (garbageSort != null) {
            result.setType(garbageSort.getType());
        } else {
            result.setType(0);
        }

        return CommonResult.success(result);
    }

    @Override
    public CommonResult<CreditResult> credit(Integer userId) {

        UserC userC = userCMapper.selectById(userId);
        if (userC == null) {
            return CommonResult.fail("用户不存在");
        }

        CreditResult result = new CreditResult();

        result.setCredit(userC.getCredit());

        return CommonResult.success(result);
    }

    @Override
    public CommonResult<String> addPoints(Integer userId, Integer points) {



        UserC userC = userCMapper.selectById(userId);
        if (userC == null) {
            return CommonResult.fail("用户不存在，请求失败");
        }
        if (userC.getCredit() == null) {
            userC.setCredit(0);
        }
        int updateNum = userCMapper.update(new UserC(),
                new UpdateWrapper<UserC>()
                        .eq("id", userId)
                        .set("credit", userC.getCredit() + 1)
        );
        if (updateNum != 1) {
            return CommonResult.fail("请求失败");
        } else {
            return CommonResult.success("请求成功");
        }
    }
}
