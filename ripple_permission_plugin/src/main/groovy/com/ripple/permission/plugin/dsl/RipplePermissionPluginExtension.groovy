package com.ripple.permission.plugin.dsl;

/**
 * Author: fanyafeng
 * Data: 2020/4/14 16:04
 * Email: fanyafeng@live.cn
 * Description: build.gradle扩展类，用于控制是否进行插装
 * 如果这些还不够，可以通过定义忽略路径
 */
public class RipplePermissionPluginExtension {
    boolean isIgnoreAll = false
    boolean isIgnoreThirdJar = true

    /**
     * 如果相应目录或者第三方jar包已经忽略，下方不起作用
     * 上方配置优先执行
     */

    //包含相应路径的都忽略，类似模糊查询
    List<String> ignoreContainPathList = new ArrayList<>();

    //精确屏蔽相应class目录文件
    List<String> ignorePathList = new ArrayList<>();
}
