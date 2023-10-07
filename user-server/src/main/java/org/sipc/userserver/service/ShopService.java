package org.sipc.userserver.service;

import org.sipc.userserver.pojo.dto.CommonResult;
import org.sipc.userserver.pojo.dto.result.shop.GetGiftsResult;

public interface ShopService {

    /**
     * 获取所有礼品
     *
     * @return 礼品
     */
    CommonResult<GetGiftsResult> getGifts();
}
