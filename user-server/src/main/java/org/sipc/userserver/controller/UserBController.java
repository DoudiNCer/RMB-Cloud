package org.sipc.userserver.controller;

import lombok.RequiredArgsConstructor;
import org.sipc.userserver.pojo.domain.UserB;
import org.sipc.userserver.pojo.dto.CommonResult;
import org.sipc.userserver.pojo.dto.param.userB.BTokenVerifyParam;
import org.sipc.userserver.pojo.dto.param.userB.UserBLoginParam;
import org.sipc.userserver.pojo.dto.result.userB.UserBLoginResult;
import org.sipc.userserver.service.UserBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/b")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserBController {

    private final UserBService userBService;

    @PostMapping("/login")
    public CommonResult<UserBLoginResult> userBLogin(@Validated @RequestBody UserBLoginParam param){
        return userBService.login(param);
    }

    @PostMapping("/varify")
    public CommonResult<UserB> verifyBToken(@Validated @RequestParam BTokenVerifyParam param){
        return userBService.verifyToken(param);
    }

}
