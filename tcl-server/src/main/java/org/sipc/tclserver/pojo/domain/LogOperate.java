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
@TableName("log_operate")
public class LogOperate implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * log_set表主键id
     */
    @TableField("log_id")
    private Integer logId;

    /**
     * 操作类型，1新增，2更新，3删除
     */
    @TableField("operate_type")
    private Integer operateType;

    /**
     * 主键值
     */
    @TableField("primary_key_value")
    private byte[] primaryKeyValue;

    /**
     * 内容
     */
    @TableField("content")
    private String content;

    @TableField("url")
    private String url;

    /**
     * 操作者id
     */
    @TableField("author_id")
    private Integer authorId;

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
