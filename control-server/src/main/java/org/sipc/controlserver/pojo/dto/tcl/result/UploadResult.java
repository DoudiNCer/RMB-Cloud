package org.sipc.controlserver.pojo.dto.tcl.result;

import lombok.Data;

import java.util.List;

/**
 * @author tzih
 * @version v1.0
 * @since 2023.10.11
 */
@Data
public class UploadResult {

    private String url;

    private List<String> list;

}
