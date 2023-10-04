package org.sipc.tclserver.common;

import org.sipc.tclserver.pojo.domain.District;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tzih
 * @version v1.0
 * @since 2023.10.02
 */
public class Constant {

    public static Map<Integer, List<Integer>> provinceM;

    public static Map<Integer, List<Integer>> municipalityD;

    public static Map<Integer, District> districtMap;

}
