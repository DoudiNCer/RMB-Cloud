package org.sipc.controlserver.pojo.dto.user.result.shop;

import lombok.Data;
import org.sipc.controlserver.pojo.dto.user.result.shop.po.GetOrdersPo;

import java.util.List;

@Data
public class GetOrdersResult {
    private List<GetOrdersPo> orders;
}
