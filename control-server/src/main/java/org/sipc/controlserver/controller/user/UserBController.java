package org.sipc.controlserver.controller.user;

import org.apache.dubbo.config.annotation.DubboReference;
import org.sipc.controlserver.pojo.dto.CommonResult;
import org.sipc.controlserver.pojo.dto.user.param.userB.UserBLoginParam;
import org.sipc.controlserver.pojo.dto.user.result.userB.UserBLoginResult;
import org.sipc.controlserver.service.user.UserBService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/b")
//@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserBController {
    @DubboReference
    private UserBService userBService;

    @PostMapping("/login")
    public CommonResult<UserBLoginResult> userBLogin(@Validated @RequestBody UserBLoginParam param){
        return userBService.login(param);
    }
}
