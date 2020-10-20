// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.ripple.dependencies.plugin") apply false
}

buildscript {
    val kotlin_version by extra("1.3.71")
    val min_sdk_version by extra(17)
    val max_sdk_version by extra(30)

    ReposDependencies.addRepos.invoke(repositories)

    dependencies {
        classpath(kotlin("gradle-plugin", version = kotlin_version))
        classpath(ClasspathDependencies.com_android_tools_build_gradle)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files

//        classpath("com.ripple.permission.plugin:ripple_permission_plugin:1.0.0")
        classpath(ClasspathDependencies.org_sonarsource_scanner_gradle_sonarqube_gradle_plugin)
        classpath(ClasspathDependencies.com_jfrog_bintray_gradle_gradle_bintray_plugin)
        classpath(ClasspathDependencies.com_github_dcendents_android_maven_gradle_plugin)
        classpath(ClasspathDependencies.org_jfrog_buildinfo_build_info_extractor_gradle)
        classpath(ClasspathDependencies.org_jetbrains_dokka_dokka_android_gradle_plugin)
    }
}

subprojects {
    project.apply(plugin = "com.ripple.dependencies.plugin")
}

allprojects {
    AllReposDependencies.addRepos.invoke(repositories)
}

tasks.register<Delete>("clean").configure {
    delete(rootProject.buildDir)
}
