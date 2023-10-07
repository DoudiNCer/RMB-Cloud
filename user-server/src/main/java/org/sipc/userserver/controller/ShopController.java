package org.sipc.userserver.controller;

import lombok.RequiredArgsConstructor;
import org.sipc.userserver.pojo.dto.CommonResult;
import org.sipc.userserver.pojo.dto.result.shop.GetGiftsResult;
import org.sipc.userserver.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ShopController {
    private final ShopService shopService;

    @GetMapping("/gifts")
    public CommonResult<GetGiftsResult> getGifts(){
        return shopService.getGifts();
    }
}
