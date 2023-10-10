package org.sipc.controlserver.service.user;

import org.sipc.controlserver.pojo.dto.CommonResult;
import org.sipc.controlserver.pojo.dto.user.param.shop.ConventGiftParam;
import org.sipc.controlserver.pojo.dto.user.result.shop.GetGiftsResult;

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
