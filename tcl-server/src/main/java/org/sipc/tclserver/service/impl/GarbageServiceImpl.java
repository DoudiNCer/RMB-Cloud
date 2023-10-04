package org.sipc.tclserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.RequiredArgsConstructor;
import org.sipc.tclserver.common.Constant;
import org.sipc.tclserver.mapper.DistrictMapper;
import org.sipc.tclserver.mapper.GarbageMapper;
import org.sipc.tclserver.mapper.MunicipalityMapper;
import org.sipc.tclserver.mapper.ProvinceMapper;
import org.sipc.tclserver.pojo.domain.District;
import org.sipc.tclserver.pojo.domain.Garbage;
import org.sipc.tclserver.pojo.dto.CommonResult;
import org.sipc.tclserver.pojo.dto.result.GarbageAllResult;
import org.sipc.tclserver.pojo.dto.result.po.GarbagePo;
import org.sipc.tclserver.service.GarbageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tzih
 * @version v1.0
 * @since 2023.10.02
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class GarbageServiceImpl implements GarbageService {

    private final ProvinceMapper provinceMapper;

    private final MunicipalityMapper municipalityMapper;

    private final DistrictMapper districtMapper;

    private final GarbageMapper garbageMapper;

    @Override
    public CommonResult<GarbageAllResult> all(Integer type, Integer id) {

        GarbageAllResult result = new GarbageAllResult();

        List<GarbagePo> garbagePoList = new ArrayList<>();

        switch (type) {
            case 1:
                for (Garbage garbage : garbageMapper.selectList(new UpdateWrapper<Garbage>().eq("province_id", id))) {
                    setGarbagePo(garbagePoList, garbage);
                }
                break;
            case 2 :
                for (Garbage garbage : garbageMapper.selectList(new UpdateWrapper<Garbage>().eq("municipality_id", id))) {
                    setGarbagePo(garbagePoList, garbage);
                }
                break;
            case 3 :
                for (Garbage garbage : garbageMapper.selectList(new UpdateWrapper<Garbage>().eq("district_id", id))) {
                    setGarbagePo(garbagePoList, garbage);
                }
                break;
        }

        result.setGarbageList(garbagePoList);

        return CommonResult.success(result);
    }

    private void setGarbagePo(List<GarbagePo> garbagePoList, Garbage garbage) {
        GarbagePo garbagePo = new GarbagePo();

        garbagePo.setId(garbage.getId());
        garbagePo.setDistrictId(garbage.getDistrictId());

        District district = Constant.districtMap.get(garbagePo.getDistrictId());
        if (district == null) {
            District districtDo = districtMapper.selectById(garbage.getDistrictId());

            Constant.districtMap.put(garbage.getDistrictId(), districtDo);

            garbagePo.setDistrict(districtDo.getContent());
        } else {
            garbagePo.setDistrict(district.getContent());
        }

        garbagePo.setContent(garbage.getContent());

        garbagePoList.add(garbagePo);
    }
}
