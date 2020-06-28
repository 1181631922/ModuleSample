package com.ripple.gradle.helper.plugin.util

import org.gradle.api.Project;

/**
 * Author: fanyafeng
 * Data: 2020/6/22 20:00
 * Email: fanyafeng@live.cn
 * Description:
 */
class AndroidUtils {
    /**
     * 以下两个字段用来区分module类型
     * 如果是 applicationId 则这个module为app
     * 如果是 libraryId 则这个module为第三方依赖lib
     */
    def public static applicationId = 'com.android.application'
    def public static libraryId = 'com.android.library'

    /**
     * 是否是一个application
     *
     * @param project
     * @return
     */
    static boolean isApplication(Project project) {
        project.pluginManager.hasPlugin(applicationId)
    }

    /**
     * 是否是一个lib
     *
     * @param project
     * @return
     */
    static boolean isLibrary(Project project){
        project.pluginManager.hasPlugin(libraryId)
    }
}
