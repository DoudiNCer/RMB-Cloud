package org.sipc.userserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sipc.userserver.mapper.GiftMapper;
import org.sipc.userserver.mapper.OrderMapper;
import org.sipc.userserver.mapper.UserCMapper;
import org.sipc.userserver.pojo.domain.Gift;
import org.sipc.userserver.pojo.domain.Order;
import org.sipc.userserver.pojo.domain.UserC;
import org.sipc.userserver.pojo.dto.CommonResult;
import org.sipc.userserver.pojo.dto.param.shop.ConventGiftParam;
import org.sipc.userserver.pojo.dto.result.shop.GetGiftsResult;
import org.sipc.userserver.pojo.dto.result.shop.po.GetGiftsResultPo;
import org.sipc.userserver.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ShopServiceImpl implements ShopService {
    private final GiftMapper giftMapper;
    private final OrderMapper orderMapper;
    private final UserCMapper userCMapper;
    /**
     * 获取所有礼品
     *
     * @return 礼品
     */
    @Override
    public CommonResult<GetGiftsResult> getGifts() {
        List<Gift> gifts = giftMapper.selectList(new QueryWrapper<>());
        List<GetGiftsResultPo> results = new ArrayList<>(gifts.size());
        for (Gift gift : gifts) {
            GetGiftsResultPo po = new GetGiftsResultPo();
            po.setId(gift.getId());
            po.setName(gift.getName());
            po.setCredit(po.getCredit());
            results.add(po);
        }
        GetGiftsResult result = new GetGiftsResult();
        result.setGifts(results);
        return CommonResult.success(result);
    }

    /**
     * 用户兑换礼品
     *
     * @param param 用户、礼品信息
     * @return 兑换结果
     */
    @Override
    public CommonResult<String> conventGift(ConventGiftParam param) {
        UserC userC = userCMapper.selectById(param.getUserId());
        if (userC == null){
            return CommonResult.fail("用户不存在");
        }
        Gift gift = giftMapper.selectById(param.getGiftId());
        if (gift == null){
            return CommonResult.fail("礼品不存在");
        }
        if (userC.getCredit() < gift.getCredit()){
            return CommonResult.fail("兑换失败：积分不足");
        }
        Order order = new Order();
        order.setGiftId(gift.getId());
        order.setUserId(param.getUserId());
        order.setReceiver(param.getReceiver());
        order.setPhone(param.getPhone());
        order.setAddress(param.getAddress());
        orderMapper.insert(order);
        userC.setCredit(userC.getCredit() - gift.getCredit());
        userCMapper.updateById(userC);
        return CommonResult.success();
    }
}
