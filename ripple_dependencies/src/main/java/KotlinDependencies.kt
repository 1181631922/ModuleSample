/**
 * Author: fanyafeng
 * Date: 2020/10/19 11:04
 * Email: fanyafeng@live.cn
 * Description: kotlin 第三方依赖库
 */
object KotlinDependencies {
    private const val COLON = ":"
    const val org_jetbrains_kotlin = "org.jetbrains.kotlin"
    const val org_jetbrains_kotlinx = "org.jetbrains.kotlinx"

    //-----------------------------org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.72----------------------------------------
    const val org_jetbrains_kotlin_kotlin_stdlib_jdk7 =
        org_jetbrains_kotlin + COLON + "kotlin-stdlib-jdk7"
    const val org_jetbrains_kotlin_kotlin_stdlib_jdk7_with_version =
        org_jetbrains_kotlin_kotlin_stdlib_jdk7 + COLON + KotlinVersion.KOTLIN_VERSION

    //kotlin反射库
    //-----------------------------org.jetbrains.kotlin:kotlin-reflect:1.3.72----------------------------------------
    const val org_jetbrains_kotlin_kotlin_reflect = org_jetbrains_kotlin + COLON + "kotlin-reflect"
    const val org_jetbrains_kotlin_kotlin_reflect_with_version =
        org_jetbrains_kotlin_kotlin_reflect + COLON + KotlinVersion.KOTLIN_VERSION

    //kotlin协程核心库
    //-----------------------------org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9----------------------------------------
    const val org_jetbrains_kotlinx_kotlinx_coroutines_core =
        org_jetbrains_kotlinx + COLON + "kotlinx-coroutines-core"
    const val org_jetbrains_kotlinx_kotlinx_coroutines_core_with_version =
        org_jetbrains_kotlinx_kotlinx_coroutines_core + COLON + KotlinVersion.ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_CORE_VERSION

    //-----------------------------org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.4----------------------------------------
    const val org_jetbrains_kotlinx_kotlinx_coroutines_android =
        org_jetbrains_kotlinx + COLON + "kotlinx-coroutines-android"
    const val org_jetbrains_kotlinx_kotlinx_coroutines_android_with_version =
        org_jetbrains_kotlinx_kotlinx_coroutines_android + COLON + KotlinVersion.ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_ANDROID_VERSION
}