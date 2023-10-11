package org.sipc.userserver.pojo.dto.result.shop;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.sipc.userserver.pojo.dto.result.shop.po.GetOrdersPo;

import java.util.List;

@Data
public class GetOrdersResult {
    private List<GetOrdersPo> orders;
}
