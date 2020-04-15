package com.ripple.permission.plugin.log;

/**
 * Author: fanyafeng
 * Data: 2020/4/15 09:38
 * Email: fanyafeng@live.cn
 * Description:
 */
public class Utils {
    public static boolean isDebug = true;

    public static void print(String text){
        if (isDebug){
            System.out.println(text);
        }
    }
}
