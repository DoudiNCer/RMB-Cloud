package org.sipc.tclserver.pojo.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author tzih
 * @since 2023-10-08
 */
@Getter
@Setter
@TableName("garbage")
public class Garbage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("province_id")
    private Integer provinceId;

    @TableField("municipality_id")
    private Integer municipalityId;

    /**
     * district表的主键id，表示垃圾桶所处地区id
     */
    @TableField("district_id")
    private Integer districtId;

    /**
     * 垃圾桶名称
     */
    @TableField("content")
    private String content;

    /**
     * 0正常，1已满，2烟雾报警
     */
    @TableField("status")
    private Integer status;

    /**
     * 位置
     */
    @TableField("location")
    private String location;

    /**
     * 纬度
     */
    @TableField("latitude")
    private Object latitude;

    /**
     * 经度
     */
    @TableField("longitude")
    private Object longitude;

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
