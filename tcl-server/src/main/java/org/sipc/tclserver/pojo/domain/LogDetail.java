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
@TableName("log_detail")
public class LogDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * log表主键id
     */
    @TableField("log_id")
    private Integer logId;

    /**
     * log_opreate表主键id
     */
    @TableField("log_operate_id")
    private Integer logOperateId;

    /**
     * 字段名称
     */
    @TableField("column_name")
    private String columnName;

    /**
     * 字段类型
     */
    @TableField("column_type")
    private String columnType;

    /**
     * 字段之后的值
     */
    @TableField("cloumn_value")
    private String cloumnValue;

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
