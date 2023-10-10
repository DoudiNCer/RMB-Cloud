package org.sipc.tclserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.sipc.tclserver.pojo.domain.GarbageRecord;
import org.apache.ibatis.annotations.Mapper;
import org.sipc.tclserver.pojo.domain.po.IdNameTypeNumPo;
import org.sipc.tclserver.pojo.domain.po.TypeNumPo;

import java.time.LocalDateTime;
import java.util.List;

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

    List<TypeNumPo> selectUseNumByDistrictId(Integer districtId);

    List<IdNameTypeNumPo> selectDetailsByDistrictId(@Param("districtId") Integer districtId,
                                                    @Param("firstTime") LocalDateTime firstTime,
                                                    @Param("lastTime") LocalDateTime lastTime);

}
