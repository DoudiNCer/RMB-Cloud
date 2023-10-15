package org.sipc.controlserver.pojo.dto.user.result.shop;

import lombok.Data;
import org.sipc.controlserver.pojo.dto.user.result.shop.po.GetGiftsResultPo;

import java.util.List;

@Data
public class GetGiftsResult {
    private List<GetGiftsResultPo> gifts;
}
