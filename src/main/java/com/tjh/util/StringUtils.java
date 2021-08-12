package com.tjh.util;

/**
 * @author tjh
 * #Description StringUtils
 * #Date: 2021/3/27 21:41
 */
public class StringUtils {

    public static boolean isNumber(String str){
        String reg = "^[0-9]+(.[0-9]+)?$";
        return str.matches(reg);
    }
}
