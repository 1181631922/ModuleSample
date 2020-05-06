## 前言
利用业余时间完成一整套自用框架，一方面是想尽量不依赖第三方库，另一方面也算是对自己技术的检测吧
PS：英文名：Ripple
### 1.先大体规划一下自己的方向
1. 本身可以处理，不用引用任何第三方库的，会从一下几个方面入手
    * 通用UI，本身会定义通用UI，但是不会很多，就是比较最起码是在这个项目中会经常用到的
    * Android本身可以处理的一些业务可以拿出来
2. 需要依赖第三方库才能进行处理
    * 最常用的就是网络，下载
    * 常用的支付，分享等    
3. 自己会定义一些底层的sdk规则，然后自己再去进行实现.


| 库名称            | 库描述                             | module名字                  |
|----------------|---------------------------------|---------------------------|
| Base库          | 底层base库，定义功能（app的基本功能）不定义实现     | com.ripple.base.kit       |
| BaseSdk        | 实现base的功能                       | com.ripple.base.sdk       |
| UI库            | 通用UI库                           | com.ripple.ui             |
| TOOL库            | 通用工具库包括android和java                           | com.ripple.tool             |
| Http库         | 网络库                             | com.ripple.http          |
| Dialog库        | 封装系统dialog                      | com.ripple.dialog         |
| Refresh库       | 刷新库                             | com.ripple.refresh        |
| Router库        | 路由库，暂时先定义                       | com.ripple.router         |
| RouterHelper库  | 路由辅助类库                          | com.ripple.router.helper  |
| MediaPicker库   | 媒体选择库                           | com.ripple.media.picker   |
| BridgeWebView库 | webview库                        | com.ripple.web.bridge     |
| Camera库        | 自定义相机库，暂定需要5.0之上，底层采用Camera2去实现 | com.ripple.camera         |
| Cache库         | 缓存库                             | com.ripple.cache          |
| DB库            | 数据库                             | com.ripple.db             |
| Permission库    | 动态权限库                           | com.ripple.permission     |
| Permission库（gradle）    | 动态权限gradle库                           | com.ripple.permission.gradle     |
| Compress库      | 图片压缩库                           | com.ripple.photo.compress |
| Task库      | 多任务处理库                           | com.ripple.task |
| Share库         | 分享库                             | com.ripple.share          |
| FileDownload库         | 文件下载库                             | com.ripple.file.down          |


### 2.一些比较杂的问题
1. 项目除去运行时注解的框架都会采用Kotlin编写，这样写起来也会比较快，使用起来也比较便捷
2. 每个库写完之后会链接到上方列表
3. 个人能力问题，可能写不了很快很好
4. UI库会出一些TV版，方便别人也方便自己吧
5. 文章会同步到博客上
6. 中间会有一些其他知识的分享

暂时只有这么多了，剩下的就是希望不要断更



