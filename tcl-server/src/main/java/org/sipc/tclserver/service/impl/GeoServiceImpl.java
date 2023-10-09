package org.sipc.tclserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.sipc.tclserver.common.Constant;
import org.sipc.tclserver.mapper.DistrictMapper;
import org.sipc.tclserver.mapper.MunicipalityMapper;
import org.sipc.tclserver.mapper.ProvinceMapper;
import org.sipc.tclserver.pojo.domain.District;
import org.sipc.tclserver.pojo.domain.Municipality;
import org.sipc.tclserver.pojo.domain.Province;
import org.sipc.tclserver.pojo.dto.CommonResult;
import org.sipc.tclserver.pojo.dto.result.GeoResult;
import org.sipc.tclserver.pojo.dto.result.po.GeoPo;
import org.sipc.tclserver.service.GeoService;
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
@DubboService
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class GeoServiceImpl implements GeoService {

    private final ProvinceMapper provinceMapper;

    private final MunicipalityMapper municipalityMapper;

    private final DistrictMapper districtMapper;

    @Override
    public CommonResult<GeoResult> geoAll() {

        //存放province列表
        List<GeoPo> provincePoList = new ArrayList<>();

        //遍历寻找province信息
        for (Province province : provinceMapper.selectList(new UpdateWrapper<Province>())) {
            GeoPo provincePo = new GeoPo();

            provincePo.setId(provincePo.getId());
            provincePo.setContent(provincePo.getContent());
            provincePo.setType(1);

            //存放municipality列表
            List<GeoPo> municipalityPoList = new ArrayList<>();

            List<Integer> municipalityIdList = new ArrayList<>();

            //遍历寻找municipality信息
            for (Municipality municipality : municipalityMapper.selectList(
                    new UpdateWrapper<Municipality>().eq("province_id", province.getId())
            )) {
                GeoPo municipalityPo = new GeoPo();

                municipalityPo.setId(municipality.getId());
                municipalityPo.setContent(municipality.getContent());
                municipalityPo.setType(2);

                //存放district列表
                List<GeoPo> districtPoList = new ArrayList<>();

                List<Integer> districtIdList = new ArrayList<>();

                //遍历寻找district信息
                for (District district : districtMapper.selectList(
                        new UpdateWrapper<District>().eq("municipality_id", municipality.getId())
                )) {
                    GeoPo districtPo = new GeoPo();

                    districtPo.setId(district.getId());
                    districtPo.setContent(district.getContent());
                    districtPo.setType(3);

                    //将当前districtPo添加进districtPoList
                    districtPoList.add(districtPo);

                    //将districtId添加到districtIdList
                    districtIdList.add(district.getId());

                }

                //将当前districtPoList设置当前municipalityPo的GeoPoList
                municipalityPo.setGeoPoList(districtPoList);

                //将当前municipalityPo添加进municipalityPo
                municipalityPoList.add(municipalityPo);

                //配置对应的市区map
                Constant.municipalityD.put(municipality.getId(), districtIdList);

                municipalityIdList.add(municipality.getId());
            }

            //将当前municipalityPoList设置当前provincePo的GeoPoList
            provincePo.setGeoPoList(municipalityPoList);

            //将当前provincePo添加进provincePoList
            provincePoList.add(provincePo);

            Constant.provinceM.put(province.getId(), municipalityIdList);

        }

        GeoResult result = new GeoResult();

        result.setGeoDates(provincePoList);

        return CommonResult.success(result);
    }
}
