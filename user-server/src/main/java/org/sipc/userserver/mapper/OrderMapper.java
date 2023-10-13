package org.sipc.userserver.mapper;

import org.apache.ibatis.annotations.Select;
import org.sipc.controlserver.pojo.dto.user.result.shop.po.GetOrdersPo;
import org.sipc.userserver.pojo.domain.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author DoudiNCer
 * @since 2023-10-11
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    @Select(value = {"SELECT o.id, o.address, o.phone, o.receiver, o.finished, u.`name` AS 'user', g.`name` AS 'gift' FROM `order` o LEFT JOIN gift g ON o.gift_id = g.id LEFT JOIN user_c u ON o.user_id = u.id WHERE o.finished != 1"})
    List<GetOrdersPo> getOrders();
}
