/**
 * Author: fanyafeng
 * Date: 2020/10/19 10:49
 * Email: fanyafeng@live.cn
 * Description: 所依赖的androidx的第三方库
 */
object AndroidXDependencies {
    /**
     * androidx 第三方库
     */
    private const val COLON = ":"
    const val androidx_appcompat = "androidx.appcompat"
    const val androidx_core = "androidx.core"
    const val androidx_constraintlayout = "androidx.constraintlayout"
    const val androidx_recyclerview = "androidx.recyclerview"
    const val androidx_legacy = "androidx.legacy"

    //-----------------------------androidx.appcompat:appcompat:1.2.0----------------------------------------
    const val androidx_appcompat_appcompat = androidx_appcompat + COLON + "appcompat"
    const val androidx_appcompat_appcompat_with_version =
        androidx_appcompat_appcompat + COLON + AndroidXVersion.ANDROIDX_APPCOMPAT_APPCOMPAT_VERSION

    //-----------------------------androidx.core:core-ktx:1.3.2----------------------------------------
    const val androidx_core_core_ktx = androidx_core + COLON + "core-ktx"
    const val androidx_core_core_ktx_with_version =
        androidx_core_core_ktx + COLON + AndroidXVersion.ANDROIDX_CORE_CORE_KTX_VERSION

    //-----------------------------androidx.constraintlayout:constraintlayout:2.0.2----------------------------------------
    const val androidx_constraintlayout_constraintlayout =
        androidx_constraintlayout + COLON + "constraintlayout"
    const val androidx_constraintlayout_constraintlayout_with_version =
        androidx_constraintlayout_constraintlayout + COLON + AndroidXVersion.ANDROIDX_APPCOMPAT_APPCOMPAT_VERSION


    //-----------------------------androidx.recyclerview:recyclerview:1.2.0-alpha06----------------------------------------
    const val androidx_recyclerview_recyclerview = androidx_recyclerview + COLON + "recyclerview"
    const val androidx_recyclerview_recyclerview_with_version =
        androidx_recyclerview_recyclerview + COLON + AndroidXVersion.ANDROIDX_RECYCLERVIEW_RECYCLERVIEW_VERSION

    //-----------------------------androidx.legacy:legacy-support-v4:1.0.0----------------------------------------
    const val androidx_legacy_legacy_support_v4 = androidx_legacy + COLON + "legacy-support-v4"
    const val androidx_legacy_legacy_support_v4_with_version =
        androidx_legacy_legacy_support_v4 + COLON + AndroidXVersion.ANDROIDX_LEGACY_LEGACY_SUPPORT_V4_VERSION

    //-----------------------------androidx.legacy:legacy-support-v13:1.0.0----------------------------------------
    const val androidx_legacy_legacy_support_v13 = androidx_legacy + COLON + "legacy-support-v13"
    const val androidx_legacy_legacy_support_v13_with_version =
        androidx_legacy_legacy_support_v13 + COLON + AndroidXVersion.ANDROIDX_LEGACY_LEGACY_SUPPORT_V13_VERSION
}