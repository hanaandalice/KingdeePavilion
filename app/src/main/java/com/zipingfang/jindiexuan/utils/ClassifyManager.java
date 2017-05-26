package com.zipingfang.jindiexuan.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/26.
 */

public class ClassifyManager {

    private static List<String> driverList;
    static {
        driverList =new ArrayList<>();
        driverList.add("汽车司机");
        driverList.add("电动车司机");
    }

    public static List<String> getDriverList() {
        return driverList;
    }
}
