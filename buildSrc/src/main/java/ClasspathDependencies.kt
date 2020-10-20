/**
 * Author: fanyafeng
 * Date: 2020/10/19 19:28
 * Email: fanyafeng@live.cn
 * Description: 根目录build.gradle classpath依赖
 */
object ClasspathDependencies {
    /*
    com.android.tools.build:gradle:4.0.2
     */
    const val com_android_tools_build_gradle = "com.android.tools.build:gradle:4.0.2"

    /*
    sonar扫描
    org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:2.8
     */
    const val org_sonarsource_scanner_gradle_sonarqube_gradle_plugin =
        "org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:2.8"

    /*
    自己打aar包用
    com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.4
     */
    const val com_jfrog_bintray_gradle_gradle_bintray_plugin =
        "com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.4"

    /*
    功能同上
    com.github.dcendents:android-maven-gradle-plugin:2.1
     */
    const val com_github_dcendents_android_maven_gradle_plugin =
        "com.github.dcendents:android-maven-gradle-plugin:2.1"

    /*
    功能同上
    org.jfrog.buildinfo:build-info-extractor-gradle:4.0.0
     */
    const val org_jfrog_buildinfo_build_info_extractor_gradle =
        "org.jfrog.buildinfo:build-info-extractor-gradle:4.0.0"

    /*
    功能同上
    org.jetbrains.dokka:dokka-android-gradle-plugin:0.9.18
     */
    const val org_jetbrains_dokka_dokka_android_gradle_plugin =
        "org.jetbrains.dokka:dokka-android-gradle-plugin:0.9.18"


}