package com.ripple.gradle.helper.plugin

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.internal.reflect.Instantiator
import org.gradle.invocation.DefaultGradle

import javax.inject.Inject;

/**
 * Author: fanyafeng
 * Data: 2020/6/23 10:21
 * Email: fanyafeng@live.cn
 * Description:
 */
class RippleToolsExtension {

    MavenExtension mavenExtension

    /**
     * 当前项目依赖方式：
     * 0 maven依赖（默认）
     * 1 project依赖
     * 其他值都默认是maven依赖
     */
    int allMaven = -1

    /**
     * 当前项目依赖方式
     * 如果设置此值，则上面的allMaven会无效，
     * 0 maven依赖
     * 1 project依赖
     * 其他值等同于没有设置此值
     */
    int forceAllMaven = -1

    String projectIsAllMaven = null

    String projectForceAllMaven = null

    @Inject
    RippleToolsExtension(Project project) {
        Instantiator instantiator = ((DefaultGradle) project.getGradle()).getServices().get(Instantiator)
        mavenExtension = instantiator.newInstance(MavenExtension)
    }

    void maven(Action<? super MavenExtension> action) {
        action.execute(action)
    }


    @Override
    public String toString() {
        return "RippleToolsExtension{" +
                "mavenExtension=" + mavenExtension +
                ", allMaven=" + allMaven +
                ", forceAllMaven=" + forceAllMaven +
                ", projectIsAllMaven='" + projectIsAllMaven + '\'' +
                ", projectForceAllMaven='" + projectForceAllMaven + '\'' +
                '}';
    }
}
