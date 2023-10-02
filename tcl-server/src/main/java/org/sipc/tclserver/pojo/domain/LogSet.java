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
@TableName("log_set")
public class LogSet implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 表名称
     */
    @TableField("table_name")
    private String tableName;

    /**
     * 业务名称
     */
    @TableField("business_name")
    private String businessName;

    /**
     * 主键
     */
    @TableField("primary_key")
    private String primaryKey;

    /**
     * url模板
     */
    @TableField("url_template")
    private String urlTemplate;

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
