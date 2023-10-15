package org.sipc.userserver.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * @author tzih
 * @version v1.0
 * @since 2023.10.11
 */
@Getter
@Setter
@TableName("garbage_sort")
public class GarbageSort {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("content")
    private String content;

    @TableField("type")
    private Integer type;

}
