package org.sipc.userserver.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2023-10-07
 */
@Getter
@Setter
@TableName("gift")
public class Gift implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 礼品ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 礼品名称
     */
    @TableField("name")
    private String name;

    /**
     * 礼品积分
     */
    @TableField("credit")
    private Integer credit;
}
