package org.sipc.userserver.controller;

import lombok.RequiredArgsConstructor;
import org.sipc.userserver.pojo.domain.UserC;
import org.sipc.userserver.pojo.dto.CommonResult;
import org.sipc.userserver.pojo.dto.param.userC.CTokenVerifyParam;
import org.sipc.userserver.pojo.dto.param.userC.UserCLoginParam;
import org.sipc.userserver.pojo.dto.param.userC.UserCRegistParam;
import org.sipc.userserver.pojo.dto.result.userC.UserCLoginResult;
import org.sipc.userserver.service.UserCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/b")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserCController {
    private final UserCService userCService;

    @PostMapping("/login")
    public CommonResult<UserCLoginResult> userBLogin(@Validated @RequestBody UserCLoginParam param){
        return userCService.login(param);
    }

    @PostMapping("/regist")
    public CommonResult<String> userBRRegister(@Validated @RequestParam UserCRegistParam param){
        return userCService.register(param);
    }

    @PostMapping("/varify")
    public CommonResult<UserC> verifyBToken(@Validated @RequestParam CTokenVerifyParam param){
        return userCService.verifyToken(param);
    }
}
