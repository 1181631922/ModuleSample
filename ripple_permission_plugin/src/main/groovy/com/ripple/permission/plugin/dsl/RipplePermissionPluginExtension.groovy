package com.ripple.permission.plugin.dsl;

/**
 * Author: fanyafeng
 * Data: 2020/4/14 16:04
 * Email: fanyafeng@live.cn
 * Description: build.gradle扩展类，用于控制是否进行插装
 * 如果这些还不够，可以通过定义忽略路径
 */
public class RipplePermissionPluginExtension {
    //包含相应路径的都忽略，类似模糊查询
    List<String> ignoreContainPathList = new ArrayList<>();

    //精确屏蔽相应class目录文件
    List<String> ignorePathList = new ArrayList<>();
}
