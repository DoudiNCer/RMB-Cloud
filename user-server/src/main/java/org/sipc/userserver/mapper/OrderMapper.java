package org.sipc.userserver.mapper;

import org.sipc.userserver.pojo.domain.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author DoudiNCer
 * @since 2023-10-07
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}
