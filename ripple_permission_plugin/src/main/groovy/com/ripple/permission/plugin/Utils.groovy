package com.ripple.permission.plugin


class Utils {
    public static boolean isDebug = true

    static void print(String text) {
        if (isDebug)
            println(text)
    }

    /**
     *
     * @param name 例如:com/meili/sdk/permission/MNPermission
     *             或者 com.meili.sdk.permission.MNPermission
     * @return exp:Lcom/meili/sdk/permission/MNPermission;
     */
    static String className2Desc(String name) {
        String desc = "L" +
                name.replace(".", "/") +
                ";"
        return desc
    }


}