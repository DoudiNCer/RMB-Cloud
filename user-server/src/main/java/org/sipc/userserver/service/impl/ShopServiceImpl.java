package org.sipc.userserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sipc.userserver.mapper.GiftMapper;
import org.sipc.userserver.pojo.domain.Gift;
import org.sipc.userserver.pojo.dto.CommonResult;
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
}
