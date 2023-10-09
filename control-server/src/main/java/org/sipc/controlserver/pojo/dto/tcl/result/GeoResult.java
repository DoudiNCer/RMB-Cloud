package org.sipc.controlserver.pojo.dto.tcl.result;

import lombok.Data;
import org.sipc.controlserver.pojo.dto.tcl.result.po.GeoPo;

import java.util.List;

/**
 * @author tzih
 * @version v1.0
 * @since 2023.10.02
 */
@Data
public class GeoResult {

   List<GeoPo> geoDates;

   public GeoResult() {

   }

}
