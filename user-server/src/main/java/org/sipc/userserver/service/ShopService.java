package org.sipc.userserver.service;

import org.sipc.userserver.pojo.dto.CommonResult;
import org.sipc.userserver.pojo.dto.param.shop.ConventGiftParam;
import org.sipc.userserver.pojo.dto.result.shop.GetGiftsResult;

public interface ShopService {

    /**
     * 获取所有礼品
     *
     * @return 礼品
     */
    CommonResult<GetGiftsResult> getGifts();

    /**
     * 用户兑换礼品
     *
     * @param param 用户、礼品信息
     * @return 兑换结果
     */
    CommonResult<String> conventGift(ConventGiftParam param);
}
