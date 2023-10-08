package org.sipc.userserver.controller;

import lombok.RequiredArgsConstructor;
import org.sipc.userserver.pojo.dto.CommonResult;
import org.sipc.userserver.pojo.dto.param.shop.ConventGiftParam;
import org.sipc.userserver.pojo.dto.result.shop.GetGiftsResult;
import org.sipc.userserver.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ShopController {
    private final ShopService shopService;

    @GetMapping("/gifts")
    public CommonResult<GetGiftsResult> getGifts(){
        return shopService.getGifts();
    }

    @PostMapping("/convent")
    public CommonResult<String> conventGift(@Validated @RequestParam ConventGiftParam param){
        return shopService.conventGift(param);
    }
}
