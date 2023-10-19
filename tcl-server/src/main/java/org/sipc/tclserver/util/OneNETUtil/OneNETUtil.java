package org.sipc.tclserver.util.OneNETUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sipc.controlserver.pojo.dto.tcl.param.UploadPhotoParam;
import org.sipc.tclserver.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class OneNETUtil {
    public static final String HW_TIME = "HW-TIME";
    public static final String HW_PIC = "HW-PIC";
    private final RedisUtil redisUtil;

    public void uploadPhoto(UploadPhotoParam photo) throws IOException {
        Date now = new Date();
        Date last = redisUtil.getString(HW_TIME, Date.class);
        if (last != null && (now.getTime() - last.getTime()) < 2000L){
            return;
        }
        redisUtil.setString(HW_PIC, photo.getPhoto());
        redisUtil.setString(HW_TIME, now);
    }

    public byte[] getPhoto(){
        return redisUtil.getString(HW_PIC, byte[].class);
    }
}
