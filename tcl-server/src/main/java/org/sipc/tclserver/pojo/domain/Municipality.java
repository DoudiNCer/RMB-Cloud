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
@TableName("municipality")
public class Municipality implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * province表主键id
     */
    @TableField("province_id")
    private Integer provinceId;

    /**
     * 市名
     */
    @TableField("content")
    private String content;

    /**
     * 创建时间
     */
    @TableField("gmt_created")
    private LocalDateTime gmtCreated;

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
