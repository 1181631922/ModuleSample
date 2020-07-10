## 前言
利用业余时间完成一整套自用框架，一方面是想尽量不依赖第三方库，另一方面也算是对自己技术的检测吧
PS：英文名：Ripple
## 一、规划
### 1.先大体规划一下自己的方向
1. 本身可以处理，不用引用任何第三方库的，会从一下几个方面入手
    * 通用UI，本身会定义通用UI，但是不会很多，就是比较最起码是在这个项目中会经常用到的
    * Android本身可以处理的一些业务可以拿出来
2. 需要依赖第三方库才能进行处理
    * 最常用的就是网络，下载
    * 常用的支付，分享等
3. 自己会定义一些底层的sdk规则，然后自己再去进行实现.


| 库名称                   | 库描述                                           | module名字                    |开源库托管地址|
|-------------------------|-------------------------------------------------|-------------------------------|-------------------|
| Base库                           | 底层base库，定义功能（app的基本功能）不定义实现               | com.ripple.base.kit           |暂无|
| BaseSdk                          | 实现base的功能                                          | com.ripple.base.sdk           |暂无|
| [UI库](#3)                       | [常用UI库](#3)                                          | com.ripple.ui                  |https://github.com/fanyafeng/ripple_ui|
| [TOOL库](#4)                     | [通用工具库包括android和java](4)                          | com.ripple.tool                |https://github.com/fanyafeng/ripple_tool|
| Http库                           | 网络库                                                  | com.ripple.http               |暂无|
| [Dialog库](6)                    | [封装系统dialog](6)                                     | com.ripple.dialog              |https://github.com/fanyafeng/ripple_dialog|
| Refresh库                        | 刷新库                                                  | com.ripple.refresh             |暂无|
| Router库                         | 路由库，暂时先定义                                        | com.ripple.router              |暂无|
| RouterHelper库                   | 路由辅助类库                                             | com.ripple.router.helper       |暂无|
| [MediaPicker库](10)              | [媒体选择库](10)                                        | com.ripple.media.picker        |https://github.com/fanyafeng/ripple_media_picker|
| BridgeWebView库                  | webview库                                              | com.ripple.web.bridge          |暂无|
| Camera库                         | 自定义相机库，暂定需要5.0之上，底层采用Camera2去实现          | com.ripple.camera              |暂无|
| Cache库                          | 缓存库                                                  | com.ripple.cache               |暂无|
| DB库                             | 数据库                                                 | com.ripple.db                  |暂无|
| [Permission库](15)               | [动态权限库](15)                                        | com.ripple.permission          |https://github.com/fanyafeng/ripple_permission|
| [Permission库（gradle）](16)      | [动态权限gradle库](16)                                  | com.ripple.permission.gradle   |https://github.com/fanyafeng/ripple_permission_plugin|
| Compress库                       | 图片压缩库                                               | com.ripple.photo.compress       |https://github.com/fanyafeng/ripple_image_compress|
| [Task库](#18)                    | [批量任务处理库](#18)                                    | com.ripple.task                |https://github.com/fanyafeng/ripple_task|
| Share库                          | 分享库                                                  | com.ripple.share               |暂无|
| FileDownload库                   | 文件下载库                                               | com.ripple.file.down            |暂无|
| [Log库](21)                      | [日志打印库](21)                                         |com.ripple.log                   |https://github.com/fanyafeng/ripple_log|


4. 以下为上面提到的库的详细说明以及使用


5. 

### 2.一些比较杂的问题
1. 项目除去运行时注解的框架都会采用Kotlin编写，这样写起来也会比较快，使用起来也比较便捷
2. 每个库写完之后会链接到上方列表
3. 个人能力问题，可能写不了很快很好
4. UI库会出一些TV版，方便别人也方便自己吧
5. 文章会同步到博客上
6. 中间会有一些其他知识的分享

暂时只有这么多了，剩下的就是希望不要断更

## 二、库详解

### <a id="3">三 常用ui控件库</a>

[README](https://github.com/1181631922/ModuleSample/tree/master/ripple_ui)


**博客地址**
1. [类似微信朋友圈图片九宫格](https://blog.csdn.net/qq_23195583/article/details/106816151)
2. [标签列表选择view：ChooseFlowView](https://blog.csdn.net/qq_23195583/article/details/107092027)

**3.1 设计以及使用**
1. [类似微信朋友圈图片九宫格](https://github.com/1181631922/ModuleSample/blob/master/ripple_ui/doc/ninegrid.md)
2. [标签列表选择view：ChooseFlowView](https://github.com/1181631922/ModuleSample/blob/master/ripple_ui/doc/chooseflowview.md)

### <a id="4">三 常用tool控件库</a>

### <a id="6">三 常用tool控件库</a>
[Android Dialog，Toast封装](https://github.com/fanyafeng/ripple_dialog)

**博客地址**
1.[Android Dialog，Toast封装](https://blog.csdn.net/qq_23195583/article/details/106924853)

###  <a id="18">十八 批量任务处理器Task库</a>
[README](https://github.com/1181631922/ModuleSample/tree/master/ripple_task)

**博客地址：**
1. [多任务处理器初版](https://blog.csdn.net/qq_23195583/article/details/106569808)
2. [多任务处理器更新](https://blog.csdn.net/qq_23195583/article/details/106794355)

**18.1 设计原理：**
1. [最初设计原理](https://github.com/1181631922/ModuleSample/blob/master/ripple_task/doc/DesignPrinciple.md)
2. [优化设计](https://github.com/1181631922/ModuleSample/blob/master/ripple_task/doc/DesignPrinciple2.md)

**18.2 使用：**
1. [多任务处理器库使用](https://github.com/1181631922/ModuleSample/tree/master/ripple_task)


### <a id="21">二十一 Log库</a>

**博客地址：**
1.[android日志工具类：ripple_log](https://blog.csdn.net/qq_23195583/article/details/107232736)






