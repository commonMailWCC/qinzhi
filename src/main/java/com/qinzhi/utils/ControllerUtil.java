package com.qinzhi.utils;


import com.qinzhi.bean.Constants;

public final class ControllerUtil {
    private ControllerUtil() {

    }

    public static String returnString(boolean bool) {
        String result = "";
        if (bool) {
            result = Constants.SUCCESS;
        } else {
            result = Constants.FAIL;
        }
        return result;
    }
}
