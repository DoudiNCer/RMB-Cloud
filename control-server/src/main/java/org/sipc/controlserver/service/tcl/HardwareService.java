package org.sipc.controlserver.service.tcl;

import org.sipc.controlserver.pojo.dto.tcl.param.UploadPhotoParam;

public interface HardwareService {

    void uploadPhoto(UploadPhotoParam param);
}
