package org.sipc.controlserver.controller.user;

import org.apache.dubbo.config.annotation.DubboReference;
import org.sipc.controlserver.pojo.dto.CommonResult;
import org.sipc.controlserver.pojo.dto.user.param.userC.UserCLoginParam;
import org.sipc.controlserver.pojo.dto.user.param.userC.UserCRegistParam;
import org.sipc.controlserver.pojo.dto.user.result.userC.UserCLoginResult;
import org.sipc.controlserver.service.user.UserCService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/b")
//@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserCController {
    @DubboReference
    private UserCService userCService;

    @PostMapping("/login")
    public CommonResult<UserCLoginResult> userBLogin(@Validated @RequestBody UserCLoginParam param){
        return userCService.login(param);
    }

    @PostMapping("/regist")
    public CommonResult<String> userBRRegister(@Validated @RequestParam UserCRegistParam param){
        return userCService.register(param);
    }
}
