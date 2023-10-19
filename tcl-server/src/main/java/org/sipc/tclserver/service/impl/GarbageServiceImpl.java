package org.sipc.tclserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.dubbo.config.annotation.DubboReference;
import org.sipc.controlserver.pojo.dto.resultEnum.ResultEnum;
import org.sipc.controlserver.pojo.dto.tcl.result.*;
import org.sipc.controlserver.pojo.dto.tcl.result.po.*;
import org.sipc.controlserver.service.user.UserCService;
import org.sipc.tclserver.common.Constant;
import org.sipc.tclserver.mapper.*;
import org.sipc.tclserver.pojo.domain.*;
import org.sipc.tclserver.util.CheckinQRCodeUtil.CheckinQRCodeUtil;
import org.sipc.tclserver.util.TimeTransUtil;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.sipc.controlserver.exection.DateBaseException;
import org.sipc.controlserver.pojo.dto.CommonResult;
import org.sipc.controlserver.pojo.dto.tcl.param.EditTrashParam;
import org.sipc.controlserver.pojo.dto.tcl.param.GarbageAllParam;
import org.sipc.controlserver.pojo.dto.tcl.param.VerifyParam;
import org.sipc.controlserver.service.tcl.GarbageService;
import org.sipc.tclserver.mapper.*;

import org.sipc.tclserver.pojo.domain.po.IdNameTypeNumPo;
import org.sipc.tclserver.pojo.domain.po.TypeNumPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author tzih
 * @version v1.0
 * @since 2023.10.02
 */
//@Service
@DubboService
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class GarbageServiceImpl implements GarbageService {

    private final ProvinceMapper provinceMapper;

    private final MunicipalityMapper municipalityMapper;

    private final DistrictMapper districtMapper;

    private final GarbageMapper garbageMapper;

    private final GarbageRecordMapper garbageRecordMapper;

    private final CheckinQRCodeUtil checkinQRCodeUtil;

    @DubboReference
    private UserCService userCService;

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

        garbagePo.setCreateTime(TimeTransUtil.tranStringDay(garbage.getGmtCreate()));
        garbagePo.setLocation(garbage.getLocation());
        garbagePo.setLatitude(garbage.getLatitude().doubleValue());
        garbagePo.setLongitude(garbage.getLongitude().doubleValue());
        garbagePo.setStatus(garbage.getStatus());

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

        garbage.setContent(garbageAllParam.getName());
        garbage.setProvinceId(province.getId());
        garbage.setMunicipalityId(municipality.getId());
        garbage.setDistrictId(district.getId());
        garbage.setLocation(garbageAllParam.getLocation());
        garbage.setLatitude(BigDecimal.valueOf(garbageAllParam.getLatitude()));
        garbage.setLongitude(BigDecimal.valueOf(garbageAllParam.getLongitude()));
        garbage.setGmtCreate(LocalDateTime.now());
        garbage.setGmtModified(LocalDateTime.now());

        garbageMapper.insert(garbage);

        return CommonResult.success("新建成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<String> edit(EditTrashParam editTrashParam) throws DateBaseException {

        Garbage garbage = new Garbage();

        garbage.setId(editTrashParam.getId());
        garbage.setContent(editTrashParam.getName());
        garbage.setLocation(editTrashParam.getLocation());
        garbage.setLatitude(BigDecimal.valueOf(editTrashParam.getLatitude()));
        garbage.setLongitude(BigDecimal.valueOf(editTrashParam.getLongitude()));

        int updateNum = garbageMapper.updateById(garbage);
        if (updateNum != 1) {
            throw new DateBaseException("数据库修改数异常");
        }

        return CommonResult.success("请求成功");
    }

    @Override
    public CommonResult<String> delete(Integer garbageId) throws DateBaseException {

        int deletedNum = garbageMapper.deleteById(garbageId);

        if (deletedNum != 1) {
            throw new DateBaseException("数据库删除数异常");
        }

        return CommonResult.success("请求成功");
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
        for (TypeNumPo typeNumPo : typeNumPos) {
            switch (typeNumPo.getType()) {
                case 1, 2:
                    statusPo.setNormal(statusPo.getNormal() + typeNumPo.getNum());
                    break;
                case 3:
                    statusPo.setFault(statusPo.getNormal() + typeNumPo.getNum());
                    break;
            }
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
                    setTData(garbageSortPoAll, garbageSortPoT, garbageUsePoT, idNameTypeNumPo);

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
                    setTData(garbageSortPoAll, garbageSortPoT, garbageUsePoT, idNameTypeNumPo);
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

    private void setTData(GarbageSortPo garbageSortPoAll, GarbageSortPo garbageSortPoT, GarbageUsePo garbageUsePoT, IdNameTypeNumPo idNameTypeNumPo) {
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

    @Override
    public CommonResult<String> getCheckinQRCode(Integer garbageId) {

        String checkinQRCode = checkinQRCodeUtil.getCheckinQRCode(garbageId);

        if (checkinQRCode == null) {
            return CommonResult.fail("请求异常，请稍候再试");
        }

        return CommonResult.success(checkinQRCode, 1);
    }

    @Override
    public CommonResult<VerifyResult> verifyQRCode(VerifyParam verifyParam) {

        Integer garbageId = checkinQRCodeUtil.verifyQRCode(verifyParam.getQrCode());

        if (garbageId == null) {
            return CommonResult.fail("您请求的信息不存在");
        }

        VerifyResult verifyResult = new VerifyResult();

        verifyResult.setGarbageId(garbageId);

        return CommonResult.success(verifyResult);
    }

    @Override
    public CommonResult<String> test() throws Exception {
        throw new Exception("这是一个错误测试");
    }

    @Override
    public CommonResult<InfoResult> info(Integer garbageId) {

        Garbage garbage = garbageMapper.selectById(garbageId);

        if (garbage == null) {
            return CommonResult.fail("garbageId错误，该垃圾桶不存在");
        }

        List<RecordPo> recordPoList = new LinkedList<>();

        for (GarbageRecord garbageRecord : garbageRecordMapper.selectList(
                new UpdateWrapper<GarbageRecord>().eq("garbage_id", garbageId)
        )) {
            RecordPo recordPo = new RecordPo();

            recordPo.setId(garbageRecord.getId());
            recordPo.setUrl(garbageRecord.getUrl());
            recordPo.setType(garbageRecord.getType());
            recordPo.setTime(TimeTransUtil.tranStringDay(garbageRecord.getTime()));

            recordPoList.add(recordPo);
        }

        InfoResult result = new InfoResult();

        result.setName(garbage.getContent());
        result.setRecordPoList(recordPoList);

        return CommonResult.success(result);
    }

    @Override
    public CommonResult<UploadResult> identify(Integer garbageId, Integer userId, String url, String strType) {

//        // 检查文件是否为空
//        if (file.isEmpty()) {
////            return ResponseEntity.badRequest().body("Please select a file to upload.");
//            return CommonResult.fail("传参错误，图片不存在");
//        }
//
        Garbage garbage = garbageMapper.selectById(garbageId);
        if (garbage == null) {
            return CommonResult.fail("垃圾桶不存在");
        }

//        UploadResult result = new UploadResult();
//        try {
//            result = work(file);
//        } catch (Exception e) {
//            System.out.println("出异常了");
//        }
        // 在实际应用中，您可以将文件保存到服务器或进行其他操作
        // 这里只是简单地返回一个成功响应
//        return ResponseEntity.ok("File uploaded successfully: " + file.getOriginalFilename());
        if (url != null) {

            GarbageRecord garbageRecord = new GarbageRecord();

            garbageRecord.setDistrictId(garbage.getDistrictId());
            garbageRecord.setGarbageId(garbageId);
            garbageRecord.setUserId(userId);
            if (strType != null) {
                switch (strType) {
                    case "可回收垃圾":
                        garbageRecord.setType(1);
                        break;
                    case "其他垃圾":
                        garbageRecord.setType(2);
                        break;
                    case "有害垃圾":
                        garbageRecord.setType(3);
                        break;
                    case "厨余垃圾":
                        garbageRecord.setType(4);
                        break;
                    default:
                        garbageRecord.setType(0);
                        break;
                }
            } else {
                garbageRecord.setType(0);
            }

            garbageRecord.setContent("");
            garbageRecord.setUrl(url);
            garbageRecord.setTime(LocalDateTime.now());
            garbageRecord.setGmtCreate(LocalDateTime.now());
            garbageRecord.setGmtModified(LocalDateTime.now());

            int insertNum = garbageRecordMapper.insert(garbageRecord);
            if (insertNum != 1) {
                return CommonResult.fail("请求失败");
            }

            CommonResult<String> stringCommonResult = userCService.addPoints(userId, 1);

            if (!stringCommonResult.getCode().equals(ResultEnum.SUCCESS.getCode())) {
                return CommonResult.fail("请求失败");
            }

//            UploadResult result = new UploadResult();

            return CommonResult.success(null);
        } else {
            return CommonResult.fail("请求错误，请稍后再试");
        }

    }

    private UploadResult work(MultipartFile multipartFile) throws Exception {

        if (multipartFile == null) {
            return null;
        }

        // 创建一个RestTemplate实例
        RestTemplate restTemplate = new RestTemplate();

//        // 设置文件路径
//        String filePath = "/path/to/your/file.txt";

        // 创建HTTP头部
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // 创建HTTP请求实体
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

//        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename())); // 创建临时文件
//        multipartFile.transferTo(file); // 将MultipartFile的内容写入文件

        // 将MultipartFile的内容读取为字节数组
//        byte[] fileBytes = multipartFile.getBytes();

        File file = multipartFileToFile(multipartFile);
        if (file != null) {
            body.add("file", new FileSystemResource(file));
        }

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // 设置目标服务器的URL
        String serverUrl = "http://127.0.0.1:5000/upload"; // 服务器的地址

        // 发送文件上传请求
        ResponseEntity<UploadResult> response = restTemplate.postForEntity(serverUrl, requestEntity, UploadResult.class);

        System.out.println(Objects.requireNonNull(response.getBody()).getUrl() + "\n" + Arrays.toString(response.getBody().getList().toArray()));
        // 处理响应
        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("File uploaded successfully.");
        } else {
            System.err.println("File upload failed with status code: " + response.getStatusCodeValue());
        }

        return response.getBody();
    }


    private File multipartFileToFile(MultipartFile file) throws Exception {

        File toFile = null;
        if (file.isEmpty() || file.getSize() <= 0) {
            return null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
//            inputStreamToFile(ins, toFile);
            copyInputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    private void copyInputStreamToFile(InputStream inputStream, File file)
            throws IOException {

        try (FileOutputStream outputStream = new FileOutputStream(file)) {

            int read;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }

            // commons-io
            //IOUtils.copy(inputStream, outputStream);

        }

    }

    @Override
    public Boolean isGarbage(Integer garbageId) {

        Garbage garbage = garbageMapper.selectById(garbageId);

        return garbage != null;
    }
}
