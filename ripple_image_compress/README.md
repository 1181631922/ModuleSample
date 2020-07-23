# 图片压缩库
为了简化任务处理功能，方便开发者更好地专注业务，减少对某些模板类操作的重复编写以及避免在此过程中有可能产生的bug而进行多任务处理的封装
多任务处理库是一种标准和规范，当然也会对一些成熟的功能进行实现。
因为图片压缩是经常用到的，这里基于多任务处理库的前提下，单独把图片压缩拿了出来，其实市面上已经有很成熟的第三方库了（鲁班压缩），但是感觉定制化，可配置方面欠灵活，所以这个图片压缩库。

## 一、图片压缩库
为了有更好的阅读效果，文中已经尽可能少的使用代码，目的就是为了大家粗略看一眼就能接入使用，基本无成本。
### 1.1简介
针对当下场景，图片批量压缩使用场景会很多而且使用也很频繁，亟待解决，在进行网络调研以及研究系统源码之后对其进行了封装（这里的话最好结合图片选择库一起使用,使用时默认已经获取文件读写权限）。
### 1.2 快速使用
压缩完生成的新图片不会发广播，如果需要的话可以自行发送广播，库中有相应的工具方法，因为所有的路径开发者都可以拿到。如无特殊需要建议开发者使用完成之后进行删除。内置两套算法都没有判断压缩后图片的最大大小，但是不会超过`1M`,一般高清压缩适用于需要看文字的（比如证件照，合同等），普清压缩适用于只看图的情况，高清压缩后大约`200-300k`，普清压缩大约`500-600k`，默认大于`500k`才会进行压缩。
#### 1.2.1 引入maven库

```
//根目录gradle
maven {
            url "https://dl.bintray.com/fanyafeng/ripple"
        }
        
        
//module目录gradle
implementation 'com.ripple.component:img-compress:0.0.2'
```
#### 1.2.2 单张图片压缩
使用前需要对压缩引擎进行实例化，将需要压缩的对象使用`compressImage(item)`进行压缩，压缩完成后会有相应回调结果，根据自己需要去实例化不同的回调接口
```
        val engine = ImageCompressEngineImpl()
        engine.compressImage(ImageModel("图片路径"))

        engine.onItemStart = object : OnItemStart<ImageItem> {
            override fun onItemStart(startResult: ImageItem) {
                Log.d(TAG, "单张图片开始压缩操作")
            }
        }

        engine.onItemInterrupted = object : OnItemInterrupted<ImageItem> {
            override fun onItemInterrupted(interruptedResult: ImageItem) {
                Log.d(TAG, "某个图片压缩被打断")
            }
        }

        engine.onItemSuccess = object : OnItemSuccess<ImageItem> {
            override fun onItemSuccess(successResult: ImageItem) {
                Log.d(TAG, "某个图片压缩成功")
            }
        }

        engine.onFailed = object : OnFailed<List<ImageItem>> {
            override fun onFailed(failedResult: List<ImageItem>?) {
                Log.d(TAG, "所有压缩失败回调结果")
            }
        }


        engine.onSuccess = object : OnSuccess<List<ImageItem>> {
            override fun onSuccess(successResult: List<ImageItem>?) {
                Log.d(TAG, "所有压缩成功回调结果")
            }
        }
        engine.onFinish = object : OnFinish<List<ImageItem>> {
            override fun onFinish(
                finishResult: List<ImageItem>?,
                unFinishResult: List<ImageItem>?
            ) {
                Log.d(TAG, "所有任务执行完毕")
            }
        }
```
上文为`java`方式的实现，是不是感觉有些繁琐，那么可以看一下`kotlin`的实现：
至于回调还是需要哪个写哪个即可

```
        //多图
        compressImageList(imageList) {
            onItemStart { 
                
            }
            
            onItemInterrupted { 
                
            }
            
            onItemSuccess { 
                
            }
            
            onFailed { 
                
            }
            
            onSuccess {

            }

            onFinish { successResult, failedResult ->

            }
        }
        //单图
        compressImage(image){
            //同上
        }
```
是不是方便很多，这里推荐大家使用`kotlin`进行调用。
#### 1.2.3 多张图片压缩
使用同上，只是调用的话需要使用`compressImageList(imageList)`方法

```
...//同上
engine.compressImageList(imageList)
...//同上
```

### 1.3 高级使用
高级使用需要结合图片压缩设计架构图的设计进行理解调用，本文讲的是核心部分，如果还有不解可以查看源码，都有详细注释。
#### 1.3.1 CompressOption(图片压缩规格，结合源码定义)
图片压缩主要的压缩方向是像素点的`ARGB`，像素密度，质量这三个方面进行压缩，如果想了解详细信息请查看`BitmapFactory`源码，但是只有这些还不够，还需要对压缩图片大小阈值，压缩完图片后的最大大小进行配置，如果自定义使用的话需要实现`CompressOption接口`
1. `fun inSampleSize(sourceWidth: Int, sourceHeight: Int): Int`像素密度,单个像素点进行宽高压缩，压缩结果为平方
2. `fun qualityLevel(): Int`质量压缩，采用系统的质量压缩，一般为`80%`
3. `fun inPreferredConfig(): Bitmap.Config`像素点`ARGB`，常用的为`RGB_565`
4. `fun largestThreshold(): Long`压缩图片的最大阈值，如果图片超过此大小才进行压缩，否则不进行压缩
5. `fun largestSize(): Long`压缩后图片的最大大小，如果压缩后还大于此大小，可以采用循环压缩
6. `fun resizeImageBitmap(width: Int, height: Int): Pair<Int, Int>` 缩小图片尺寸已达到压缩效果，这个不建议使用，这个类似马赛克效果，会丢失图像

PS：为了方便使用内置了两套算法`HD`和`LD`，通过静态方法直接调用就可以了，还有实例代码类`SimpleCompressOption`
#### 1.3.2 CompressConfig(图片压缩配置)
图片压缩需要压缩规格还有压缩后的路径，缺一不可
1. `fun getTargetPath(): String`图片压缩后的路径
2. `fun getCompressOption(): CompressOption`设置图片压缩配置

PS：同样为了简化使用这里采用构造者模式进行了定义`SimpleCompressConfig`
#### 1.3.3 ImageItem(图片item接口)
压缩图片的item需要源路径和配置，为了扩展添加了`tag`
1. `fun getSourcePath(): String`图片的源路径
2. `fun getCompressConfig(): CompressConfig`压缩配置
3. `fun getTag(): Any?`标签设置，可空

PS：为了方便使用，这里内置了一个内部实现`ImageModel`构造方法只用传入`sourcePath`即可
#### 1.3.4 ImageCompressService(图片压缩服务)
这里只定义了一个接口，为了方便扩展
`fun compress(imageItem: ImageItem): File`
PS：为了方便使用，内置了实现`ImageCompressServiceImpl`
#### 1.3.5 ImageCompressTask(图片压缩任务)
图片压缩任务会有计数器，任务开始回调，任务被打断回调，任务成功回调，以及图片压缩服务和图片`item`，还有就是`run`
1. `fun getCountDownLatch(): CountDownLatch`
2. `fun getOnItemStart(): OnItemStart<ImageItem>`
3. `fun getOnItemInterrupted(): OnItemInterrupted<ImageItem>`
4. `fun getOnItemSuccess(): OnItemSuccess<ImageItem>`
5. `fun getImageCompressService(): ImageCompressService`
6. `fun getImageItem(): ImageItem`
7. `fun run()`

PS：为了方便使用，内置了实现`ImageCompressTaskImpl`

#### 1.3.6 ImageCompressResultTask(压缩任务列表结果)
任务列表任务会有计数器，失败结果回调，成功结果回调，所有任务完成回调以及`run`
1. `fun getCountDownLatch(): CountDownLatch`
2. `fun getOnFailed(): OnFailed<T>`
3. `fun getOnSuccess(): OnSuccess<T>`
4. `fun getOnFinish(): OnFinish<T>`
5. `fun run()`

PS：为了方便使用，内置了实现`ImageCompressResultTaskImpl`

#### 1.3.7 TaskEngine(任务引擎)
这里采用`java`的接口定义，系统对任务处理做了一层封装
`fun getExecutorService(): ExecutorService`

#### 1.3.8 ImageCompressEngine(图片任务引擎)
图片压缩需要任务引擎以及图片压缩服务
1. `fun getTaskEngine(): TaskEngine`
2. `fun getImageCompressService(): ImageCompressService`

PS：这里有默认实现，也就是最终的图片压缩的入口实现`ImageCompressEngineImpl`

### 1.4 总结
为了有更好的扩展性，都是基于接口编程，对修改关闭对扩展开放。这个图片压缩架构设计的底层实现也是基于接口实现。
举个例子：如果你感觉这些压缩规则都满足不了你，你自己完全可以实现`CompressOption`接口进行自定义并将其配置到`ImageItem`中即可





