package org.sipc.tclserver.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author tzih
 * @since 2023-10-02
 */
@Getter
@Setter
@TableName("garbage_record")
public class GarbageRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * garbage表主键id，代表垃圾桶的id
     */
    @TableField("garbage_id")
    private Integer garbageId;

    /**
     * 用户id，投垃圾的用户
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 垃圾类型，1可回收垃圾，2不可回收垃圾，3有害垃圾，4厨余垃圾
     */
    @TableField("type")
    private Integer type;

    @TableField("content")
    private String content;

    /**
     * 投递时间
     */
    @TableField("time")
    private LocalDateTime time;

    /**
     * 创建时间
     */
    @TableField("gmt_create")
    private LocalDateTime gmtCreate;

    /**
     * 更新时间
     */
    @TableField("gmt_modified")
    private LocalDateTime gmtModified;

    /**
     * 逻辑删除字段
     */
    @TableField("is_deleted")
    @TableLogic
    private LocalDateTime isDeleted;
}
