package org.sipc.tclserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.RequiredArgsConstructor;
import org.sipc.tclserver.common.Constant;
import org.sipc.tclserver.mapper.*;
import org.sipc.tclserver.pojo.domain.District;
import org.sipc.tclserver.pojo.domain.Garbage;
import org.sipc.tclserver.pojo.domain.Municipality;
import org.sipc.tclserver.pojo.domain.Province;
import org.sipc.tclserver.pojo.domain.po.IdNameTypeNumPo;
import org.sipc.tclserver.pojo.domain.po.TypeNumPo;
import org.sipc.tclserver.pojo.dto.CommonResult;
import org.sipc.tclserver.pojo.dto.param.GarbageAllParam;
import org.sipc.tclserver.pojo.dto.result.DataResult;
import org.sipc.tclserver.pojo.dto.result.GarbageAllResult;
import org.sipc.tclserver.pojo.dto.result.po.GarbagePo;
import org.sipc.tclserver.pojo.dto.result.po.GarbageSortPo;
import org.sipc.tclserver.pojo.dto.result.po.GarbageUsePo;
import org.sipc.tclserver.pojo.dto.result.po.StatusPo;
import org.sipc.tclserver.service.GarbageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    private final GarbageRecordMapper garbageRecordMapper;

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
            case 2:
                for (Garbage garbage : garbageMapper.selectList(new UpdateWrapper<Garbage>().eq("municipality_id", id))) {
                    setGarbagePo(garbagePoList, garbage);
                }
                break;
            case 3:
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

    @Override
    public CommonResult<String> add(GarbageAllParam garbageAllParam) {

        District district = districtMapper.selectById(garbageAllParam.getDistrictId());

        if (district == null) {
            return CommonResult.fail("不存在的districtId");
        }

        Municipality municipality = municipalityMapper.selectById(district.getMunicipalityId());

        if (municipality == null) {
            return CommonResult.fail("不存在的districtId");
        }

        Province province = provinceMapper.selectById(municipality.getProvinceId());

        if (province == null) {
            return CommonResult.fail("不存在的districtId");
        }

        Garbage garbage = new Garbage();

        garbage.setContent(garbageAllParam.getContent());
        garbage.setProvinceId(province.getId());
        garbage.setMunicipalityId(municipality.getId());
        garbage.setDistrictId(district.getId());
        garbage.setGmtCreate(LocalDateTime.now());
        garbage.setGmtModified(LocalDateTime.now());

        garbageMapper.insert(garbage);

        return CommonResult.success("新建成功");
    }

    @Override
    public CommonResult<DataResult> data(Integer districtId) {

//        List<TypeNumPo> typeNumPos = garbageRecordMapper.selectUseNumByDistrictId(districtId);

        GarbageSortPo garbageSortPoAll = new GarbageSortPo(0, "", 0, 0, 0, 0);

//        for (TypeNumPo typeNumPo : typeNumPos) {
//            switch (typeNumPo.getType()) {
//                case 1:
//                    garbageSortPo.setRecyclable(typeNumPo.getNum());
//                    break;
//                case 2:
//                    garbageSortPo.setNotRecyclable(typeNumPo.getNum());
//                    break;
//                case 3:
//                    garbageSortPo.setHarmful(typeNumPo.getNum());
//                    break;
//                case 4:
//                    garbageSortPo.setFoodWaste(typeNumPo.getNum());
//                    break;
//            }
//        }
        StatusPo statusPo = new StatusPo();
        List<TypeNumPo> typeNumPos = garbageMapper.selectStatusNumByDistrictId(districtId);
        if (typeNumPos.size() == 2) {
            statusPo.setNormal(typeNumPos.get(0).getNum());
            statusPo.setFault(typeNumPos.get(1).getNum());
        }

        LocalDateTime nowTime = LocalDateTime.now();

        List<IdNameTypeNumPo> idNameTypeNumPos = garbageRecordMapper.selectDetailsByDistrictId(districtId,
                nowTime.withHour(0), nowTime.withDayOfMonth(nowTime.getDayOfMonth() + 1).withHour(0));

        List<GarbageSortPo> detailList = new ArrayList<>();

        List<GarbageUsePo> garbageUseList = new ArrayList<>();

        if (!idNameTypeNumPos.isEmpty()) {
            int tId = idNameTypeNumPos.get(0).getId();

            boolean flag = true;

            GarbageSortPo garbageSortPoT = new GarbageSortPo();

            GarbageUsePo garbageUsePoT = new GarbageUsePo();

            for (IdNameTypeNumPo idNameTypeNumPo : idNameTypeNumPos) {

                if (tId == idNameTypeNumPo.getId()) {

                    /*
                       设置每个垃圾桶的各种垃圾的数量
                     */
                    //设置垃圾桶id和垃圾桶名称
                    if (flag) {
                        garbageSortPoT.setGarbageId(idNameTypeNumPo.getId());
                        garbageSortPoT.setName(idNameTypeNumPo.getName());
                        detailList.add(garbageSortPoT);

                        garbageUsePoT.setGarbageId(idNameTypeNumPo.getId());
                        garbageUsePoT.setName(idNameTypeNumPo.getName());
                        garbageUseList.add(garbageUsePoT);

                        flag = false;
                    }
                    //
                    switch (idNameTypeNumPo.getType()) {
                        case 1:
                            //设置垃圾桶每种垃圾的数量
                            garbageSortPoT.setRecyclable(idNameTypeNumPo.getNum());
                            //设置所有垃圾桶每种垃圾的数量
                            garbageSortPoAll.setRecyclable(garbageSortPoAll.getRecyclable() + idNameTypeNumPo.getNum());
                            break;
                        case 2:
                            //设置垃圾桶每种垃圾的数量
                            garbageSortPoT.setNotRecyclable(idNameTypeNumPo.getNum());
                            //设置所有垃圾桶每种垃圾的数量
                            garbageSortPoAll.setNotRecyclable(garbageSortPoAll.getNotRecyclable() + idNameTypeNumPo.getNum());
                            break;
                        case 3:
                            //设置垃圾桶每种垃圾的数量
                            garbageSortPoT.setHarmful(idNameTypeNumPo.getNum());
                            //设置所有垃圾桶每种垃圾的数量;
                            garbageSortPoAll.setHarmful(garbageSortPoT.getHarmful() + idNameTypeNumPo.getNum());
                            break;
                        case 4:
                            //设置垃圾桶每种垃圾的数量
                            garbageSortPoT.setFoodWaste(idNameTypeNumPo.getNum());
                            //设置所有垃圾桶每种垃圾的数量
                            garbageSortPoAll.setFoodWaste(garbageSortPoAll.getFoodWaste() + idNameTypeNumPo.getNum());
                            break;
                    }
                    garbageUsePoT.setUseNum(garbageUsePoT.getUseNum() + idNameTypeNumPo.getNum());

                } else {

                    garbageSortPoT = new GarbageSortPo();
                    garbageUsePoT = new GarbageUsePo();
                    
                    /*
                       设置每个垃圾桶的各种垃圾的数量
                     */
                    //设置垃圾桶id和垃圾桶名称

                    garbageSortPoT.setGarbageId(idNameTypeNumPo.getId());
                    garbageSortPoT.setName(idNameTypeNumPo.getName());
                    detailList.add(garbageSortPoT);

                    garbageUsePoT.setGarbageId(idNameTypeNumPo.getId());
                    garbageUsePoT.setName(idNameTypeNumPo.getName());
                    garbageUseList.add(garbageUsePoT);

                    flag = false;
                    
                    //
                    switch (idNameTypeNumPo.getType()) {
                        case 1:
                            //设置垃圾桶每种垃圾的数量
                            garbageSortPoT.setRecyclable(idNameTypeNumPo.getNum());
                            //设置所有垃圾桶每种垃圾的数量
                            garbageSortPoAll.setRecyclable(garbageSortPoAll.getRecyclable() + idNameTypeNumPo.getNum());
                            break;
                        case 2:
                            //设置垃圾桶每种垃圾的数量
                            garbageSortPoT.setNotRecyclable(idNameTypeNumPo.getNum());
                            //设置所有垃圾桶每种垃圾的数量
                            garbageSortPoAll.setNotRecyclable(garbageSortPoAll.getNotRecyclable() + idNameTypeNumPo.getNum());
                            break;
                        case 3:
                            //设置垃圾桶每种垃圾的数量
                            garbageSortPoT.setHarmful(idNameTypeNumPo.getNum());
                            //设置所有垃圾桶每种垃圾的数量;
                            garbageSortPoAll.setHarmful(garbageSortPoT.getHarmful() + idNameTypeNumPo.getNum());
                            break;
                        case 4:
                            //设置垃圾桶每种垃圾的数量
                            garbageSortPoT.setFoodWaste(idNameTypeNumPo.getNum());
                            //设置所有垃圾桶每种垃圾的数量
                            garbageSortPoAll.setFoodWaste(garbageSortPoAll.getFoodWaste() + idNameTypeNumPo.getNum());
                            break;
                    }
                    garbageUsePoT.setUseNum(garbageUsePoT.getUseNum() + idNameTypeNumPo.getNum());
                }
            }
        }

        DataResult result = new DataResult();

        result.setDistrictId(districtId);
        result.setDetailList(detailList);
        result.setUseNum(garbageSortPoAll);
        result.setStatus(statusPo);
        result.setGarbageUseList(garbageUseList);

        return CommonResult.success(result);
    }
}
