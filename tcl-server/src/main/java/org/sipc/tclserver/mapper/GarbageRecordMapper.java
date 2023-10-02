package org.sipc.tclserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.sipc.tclserver.pojo.domain.GarbageRecord;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tzih
 * @since 2023-10-02
 */
@Mapper
public interface GarbageRecordMapper extends BaseMapper<GarbageRecord> {

}