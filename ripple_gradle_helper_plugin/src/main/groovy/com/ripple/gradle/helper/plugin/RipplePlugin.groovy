package com.ripple.gradle.helper.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project;

/**
 * Author: fanyafeng
 * Data: 2020/6/22 10:01
 * Email: fanyafeng@live.cn
 * Description: ripple gradle插件
 *
 * 以下为android打包流程，也解释了为什么开发者会有时机在开发完代码后还可以去有时机操作代码
 *
 * ---------------------                                                                                                               ------------------
 * |                   |                                                                                                               |                |
 * |    Application    |                                                                                                               |     .aidl      |
 * |    Resources      |                                                                                                               |     Files      |
 * |                   |                                                                                                               |                |
 * ---------------------                                                                                                               ------------------
 *           |                                                                                                                                  |
 *           |                                                                                                                                  |
 *           |                                                                                                                                  |
 * ----------------------          -------------       -----------------------------------      ---------------------------            ------------------
 * |                    |          |           |       |                                 |      |                         |            |                |
 * |         AAPT       |          |           |       |                                 |      |                         |            |                |
 * |     Android Asset  |------>   |  R.java   |       |     Application Source Code     |      |      Java Interfaces    | <--------- |      AIDL      |
 * |     Packaging Tool |          |           |       |                                 |      |                         |            |                |
 * |                    |          |           |       |                                 |      |                         |            |                |
 * ----------------------          -------------       -----------------------------------      ---------------------------            ------------------
 *                  |                     |                               |                                   |
 *                  |                     |                               |                                   |
 *                  |                     |                               |                                   |
 *                  |                     |                               |                                   |
 *                  |                     |             | ----------------|------------|                      |
 *                  |                     |             |                              |                      |
 *                  |                     |             |                              |                      |
 *                  |                     |-------------|        java Complier         | ---------------------|
 *                  |                                   |                              |
 *                  |                                   |                              |
 *                  |                                   |------------------------------|
 *                  |                                                   |
 *                  |                                                   |
 *                  |                                                   |
 *                  |                                                   |
 *                  |                                   |------------------------------|
 *                  |                                   |                              |
 *                  |                                   |                              |
 *                  |                                   |           .class Files       |
 *                  |                                   |                              |
 *                  |                                   |                              |
 *                  |                                   |------------------------------|
 *                  |                                                    |
 *                  |                                                    |
 *                  |                                                    |
 *                  |                                                    |              这里就是开发者的插桩时机
 *                  |                                                    |              允许开发者进行字节码的操作
 *                  |                                                    |              开发者遍历所有class文件找到target class
 *                  |                                                    |              进行相应的操作
 *                  |                                                    |
 *                  |                                                    |
 *                  |                                                    |
 *                  |                                                    |
 *                  |                                    |-----------------------------|
 *                  |                                    |                             |
 *                  |                                    |               dex           |
 *                  |                                    |                             |
 *                  |                                    |-----------------------------|
 *                  |                                                    |
 *                  |                                                    |
 *           | -----------------------|                  |----------------------------|                                                |------------------------------|
 *           |                        |                  |                            |                                                |                              |
 *           | Compiled  Resources    | ---------------> |                            |                                                |                              |
 *           |                        |                  |        apkbuilder          |  <-------------------------------------------  |          Other Resources     |
 *           |                        |                  |                            |                                                |                              |
 *           |------------------------|                  |----------------------------|                                                |                              |
 *                                                                       |                                                              |------------------------------|
 *                                                                       |
 *                                                                       |
 *                                                                       |
 *                                                                       |
 *                                                       |-----------------------------|
 *                                                       |                             |
 *                                                       |                             |
 *                                                       |        Android Package      |
 *                                                       |              (.apk)         |
 *                                                       |                             |
 *                                                       |-----------------------------|
 *                                                                       |
 *                                                                       |
 *                                                                       |
 *                                                                       |
 *                                                       |-----------------------------|
 *                                                       |                             |
 *                                                       |                             |                                                 |-----------------------|
 *                                                       |                             |                                                 |                       |
 *                                                       |            Jarsigner        |  <----------------------------------------------|       Debug or        |
 *                                                       |                             |                                                 |    Release Keystore   |
 *                                                       |                             |                                                 |                       |
 *                                                       |                             |                                                 |-----------------------|
 *                                                       |-----------------------------|
 *                                                                       |
 *                                                                       |
 *                                                                       |
 *                                                                       |
 *                                                       |-----------------------------|
 *                                                       |                             |
 *                                                       |                             |
 *                                                       |          Signed .apk        |
 *                                                       |                             |
 *                                                       |                             |
 *                                                       |-----------------------------|
 *                                                                       |
 *                                                                       |
 *                                                                       |
 *                                                                       |
 *                                                       |-----------------------------|
 *                                                       |                             |
 *                                                       |                             |
 *                                                       |                             |
 *                                                       |            zipalign         |
 *                                                       |         (release  mode)     |
 *                                                       |                             |
 *                                                       |                             |
 *                                                       |                             |
 *                                                       |-----------------------------|
 *                                                                       |
 *                                                                       |
 *                                                                       |
 *                                                                       |
 *                                                       |-----------------------------|
 *                                                       |                             |
 *                                                       |                             |
 *                                                       |         Signed and          |
 *                                                       |         Aligned .apk        |
 *                                                       |                             |
 *                                                       |                             |
 *                                                       |-----------------------------|
 *
 *
 *  1.工程的资源文件（res文件夹下的所有文件），通过AAPT打包成R.java类（资源索引表），以及.arsc资源文件
 *  2.如果有aidl，通过aidl工具，打包成java接口类
 *  3.R.java和aidl.java通过java编辑成想要的.class文件
 *  4.源码class文件和第三方jar或者library通过dx工具打包成dex文件。dx工具的主要作用是将java字节码转换成Dalvik字节码，
 *    在此过程中会压缩常量池，消除一些荣誉信息等。
 *  5.apkbuilder工具就会将所有没有变异的资源，.arsc资源，.dex文件打包到一个完成的apk文件中
 *  6.签名，5中完成apk通过配置的签名工具，jarsigner工具对其签名。得到一个签名后的apk，signed.apk
 *  7.zipAlign工具对6中的signed.apk进行对其处理，所谓对其，主要过程是将apk包中所有的资源文件距离文件起始编译为4字节整数倍，
 *  这样通过内存映射访问apk文件时的速度会更快。对齐的作用主要是为了减少运行时内存的使用。
 *  PS：是不是好奇为什么是4字节对齐
 *  答案：https://blog.csdn.net/wordwarwordwar/article/details/79864996
 *
 *
 */
class RipplePlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        def eextension = project.extensions.getByName('android')
    }
}
