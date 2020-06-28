package com.ripple.gradle.helper.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project;

/**
 * Author: fanyafeng
 * Data: 2020/6/23 10:07
 * Email: fanyafeng@live.cn
 * Description:
 */
class RippleToolsPlugin implements Plugin<Project> {

    //当前的project
    private Project project;

    @Override
    void apply(Project target) {
        project = target

    }
}
