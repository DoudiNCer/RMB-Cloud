package org.sipc.userserver.pojo.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author DoudiNCer
 * @since 2023-10-11
 */
@Getter
@Setter
@TableName("order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    @TableField("id")
    private Integer id;

    /**
     * C端用户ID
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 礼品ID
     */
    @TableField("gift_id")
    private Integer giftId;

    /**
     * 收件人
     */
    @TableField("receiver")
    private String receiver;

    /**
     * 收件人手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 收件地址
     */
    @TableField("address")
    private String address;

    /**
     * 是否完成
     */
    @TableField("finished")
    private Boolean finished;
}
