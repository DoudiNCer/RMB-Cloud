package org.sipc.userserver.pojo.dto.result.shop;

import lombok.Data;
import org.sipc.userserver.pojo.dto.result.shop.po.GetGiftsResultPo;

import java.util.List;

@Data
public class GetGiftsResult {
    private List<GetGiftsResultPo> gifts;
}
