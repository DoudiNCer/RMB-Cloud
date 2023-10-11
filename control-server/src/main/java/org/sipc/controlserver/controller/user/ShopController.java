package org.sipc.controlserver.controller.user;

import org.apache.dubbo.config.annotation.DubboReference;
import org.sipc.controlserver.pojo.dto.CommonResult;
import org.sipc.controlserver.pojo.dto.user.param.shop.ConventGiftParam;
import org.sipc.controlserver.pojo.dto.user.param.shop.FinishOrderParam;
import org.sipc.controlserver.pojo.dto.user.result.shop.GetGiftsResult;
import org.sipc.controlserver.pojo.dto.user.result.shop.GetOrdersResult;
import org.sipc.controlserver.service.user.ShopService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shop")
//@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ShopController {
    @DubboReference
    private ShopService shopService;

    @GetMapping("/gifts")
    public CommonResult<GetGiftsResult> getGifts() {
        return shopService.getGifts();
    }

    @PostMapping("/convent")
    public CommonResult<String> conventGift(@Validated @RequestParam ConventGiftParam param) {
        return shopService.conventGift(param);
    }

    @GetMapping("/orders")
    public CommonResult<GetOrdersResult> getOrders() {
        return shopService.getOrders();
    }

    @PostMapping("/finish")
    public CommonResult<String> finishOrder(@Validated @RequestParam FinishOrderParam param) {
        return shopService.finishOrder(param);
    }
}