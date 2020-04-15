package com.ripple.permission.plugin

import com.android.build.gradle.AppExtension
import com.ripple.permission.plugin.dsl.RipplePermissionPluginExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class PluginEntry implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def android = project.extensions.getByType(AppExtension)

        def extension = project.extensions.create('rippleIgnorePermission', RipplePermissionPluginExtension)

        PermissionPlugin transform = new PermissionPlugin()
        android.registerTransform(transform)
    }
}