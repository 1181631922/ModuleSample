import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id(ModuleBuildGradlePlugin.com_android_library)
    id(ModuleBuildGradlePlugin.kotlin_android)
    id(ModuleBuildGradlePlugin.kotlin_android_extensions)
}



android {
    compileSdkVersion(ModuleBuildGradle.apiLevel)
    buildToolsVersion(ModuleBuildGradle.version)

    lintOptions {
        isAbortOnError = false
    }


    defaultConfig {
        minSdkVersion(ModuleBuildGradle.minSdkVersion)
        targetSdkVersion(ModuleBuildGradle.targetSdkVersion)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
            setProguardFiles(
                listOf(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            )
        }
    }

}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(kotlin("stdlib", KotlinCompilerVersion.VERSION))
    implementation(AndroidXDependencies.androidx_appcompat_appcompat_with_version)
    implementation(AndroidXDependencies.androidx_core_core_ktx_with_version)
    implementation(project(":ripple_tool"))
}

apply(from = "./gradle-jcenter-push.gradle")
