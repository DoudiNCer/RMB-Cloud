package org.sipc.tclserver.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sipc.controlserver.pojo.dto.tcl.param.UploadPhotoParam;
import org.sipc.controlserver.service.tcl.HardwareService;
import org.sipc.tclserver.util.OneNETUtil.OneNETUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class HardwareServiceImpl implements HardwareService {

    private final OneNETUtil oneNETUtil;
    @Override
    public void uploadPhoto(UploadPhotoParam photo) {
        try {
            oneNETUtil.uploadPhoto(photo);
        } catch (IOException ignored) {

        }
    }
}
