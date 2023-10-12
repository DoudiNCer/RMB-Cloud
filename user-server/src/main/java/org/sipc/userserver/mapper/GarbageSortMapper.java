package org.sipc.userserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.sipc.userserver.pojo.domain.GarbageSort;

@Mapper
public interface GarbageSortMapper extends BaseMapper<GarbageSort> {
}
