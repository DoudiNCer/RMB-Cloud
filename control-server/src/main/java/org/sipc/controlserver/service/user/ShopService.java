package org.sipc.controlserver.service.user;

import org.sipc.controlserver.pojo.dto.CommonResult;
import org.sipc.controlserver.pojo.dto.user.param.shop.ConventGiftParam;
import org.sipc.controlserver.pojo.dto.user.param.shop.FinishOrderParam;
import org.sipc.controlserver.pojo.dto.user.result.shop.GetGiftsResult;
import org.sipc.controlserver.pojo.dto.user.result.shop.GetOrdersResult;

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
    /**
     * 获取订单清单
     *
     * @return 订单清单
     */
    CommonResult<GetOrdersResult> getOrders();

    /**
     * 完成订单
     *
     * @param param 订单ID
     * @return 处理情况
     */
    CommonResult<String> finishOrder(FinishOrderParam param);
}
