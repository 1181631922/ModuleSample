# 图片压缩

## 简介

> sdk提供图片压缩的功能，主要有三种压缩方式（框架默认的压缩为高清压缩，6.0以上默认有访问存储卡的权限）
* 1.高清压缩：图片压缩后一般400-500k，不会超过1M，适用：有字的情况，如证件照，
* 2.普清压缩：图片压缩后一般100-200k，适用：纯图片
* 3.自定义压缩：质量，bitmap.config，压缩路径，自定义压缩规则，压缩引擎
* 注：内置两套压缩引擎压缩后图片格式为jpg，如有特殊需要可自定义

### 一、导入sdk
#### 配置依赖库：
在module中的build.gradle添加依赖库
```
compile 'com.meili.mobile.component:img-compress:版本号'
```

### 二、开始使用(异步批量压缩)
#### 1.初始化控件
##### 1.sdk中内置简单初始化（使用前必须初始化）
```
MNCompressConfig.initialize(Context);
```
简单初始化使用的配置如下：<br>
（1）图片压缩后的路径：本地/内部存储/Android/data/包名/files/图片列表<br>
（2）是否打开调试模式：否（不会打印日志）<br>
（3）图片默认压缩引擎：MNCompressOption.PHOTO_HD（高清压缩，如果项目中还有其他需要可在相应方法中传入自定义压缩引擎）<br>
##### 2.自定义
举个栗子：
```
MNCompressConfig.initialize(new CompressConfig.Builder(Context)
    .setDebug(true)
    .setImagePath("moon_img_compress")
    .setEngineFactory(MNCompressEngine.ML_COMPRESS_ENGINE)
    .build());
```
（1）图片压缩后的路径：本地/内部储存/moon_img_compress/图片列表<br>
（2）是否打开调试模式：是（打印日志，可用于调试）<br>
（3）图片默认压缩引擎：MLCompressOption.PHOTO_HD（高清压缩，如果项目中还有其他需要可在相应方法中传入自定义压缩引擎）<br>

#### 2.初始化控件后使用
##### 1.控件中的model：MNImageModel,压缩引擎Engine，压缩规则ICompressRule
###### （1）控件中的model：MNImageModel
*  sourcePath 压缩源文件路径<br>
*  targetPath 压缩后文件路径<br>
*  position   可根据此position进行排序，用来确认位置，默认升序<br>
*  tag        可以自定义TAG

###### （2）压缩规则ICompressRule：
压缩规则ICompressRule是一个接口，自定义压缩规则需要实现这个接口

```
public interface ICompressRule {

    /**
     * 需要定义像素密度压缩规则
     * 自定义压缩规则的话需要根据传入的的图片宽高进行相应的操作
     *
     * @param width
     * @param height
     * @return
     */
    int inSampleSize(int width, int height);

    /**
     * 压缩后图片质量
     *
     * @return
     */
    int compressQuality();

    /**
     * 压缩后bitmap格式
     *
     * @return
     */
    Bitmap.Config inPreferredConfig();

    /**
     * 配置图片压缩大小阀值
     * 超过后去压缩
     * 否则不压缩
     *
     * @return
     */
    int maxSize();

}
```

###### （3）压缩引擎Engine：
EngineFactory为一个抽象类，需要实现两个抽象方法

```
    /**
     * @param file          需要压缩的图片
     * @param iCompressRule 压缩图片规则
     * @return
     */
    public abstract File compress(File file, ICompressRule iCompressRule);

    /**
     * 上面的方法必须定义，然后可以自己写一个或者用sdk提供的两个模板来传参实现此方法
     * {@link com.meili.component.imgcompress.compressrule.MLCompressOption#PHOTO_HD}
     * {@link com.meili.component.imgcompress.compressrule.MLCompressOption#PHOTO_LD}
     * eg:compress(file,PHOTO_HD)
     * compress(file,PHOTO_LD)
     *
     * @param file 需要压缩的图片
     * @return
     */
    public abstract File compress(File file);
```


##### 2.使用控件
（1）所有压缩都完成的回调，会得到一个压缩完成列表，里面会有相应的每一个item的信息，这个是最常用的

```
.setOnAllSuccessCallback(new OnAllSuccessCallback() {
    @Override
        public void onAllSuccess(List<MLImageModel> mlImageBeanList) {

    });
```

（2）某一张图片压缩被打断，传回相应的item，可进行相应的操作

```
.setOnInterruptedCallback(new OnInterruptedCallback() {
    @Override
    public void onThreadInterrupted(MLImageModel imageBean) {

        }
    })
```

（3）某一张图片压缩完成，传回相应item

```
.setOnFinishCallback(new OnFinishCallback() {
    @Override
    public void onThreadFinish(MLImageModel dealImageBean) {

        }
    })
```

（4）所有图片压缩失败回调

```
.setOnAllFailedCallback(new OnAllFailedCallback() {
    @Override
    public void onAllFailed() {

        }
    })
```

（5）某一张图片开始压缩回调，传回相应的item

```
.setOnStartCallBack(new OnStartCallBack() {
    @Override
    public void onThreadProgressStart(MLImageModel imageBean) {

        }
    })
```

##### 3.提交压缩
（1）实例化MNCompressImage()

```
//sdk中内置线程池，默认单线程压缩
MNCompressImage mnCompressImage = new MNCompressImage();

//可自定义线程数量
MNCompressImage mnCompressImage = new MNCompressImage(3);
```

（2）提交压缩请求(这里需要注意一下)
常用的是以下两种方法：

```
//默认使用初始化sdk的config
mnCompressImage.submit(mnImageModelList);//可以是列表
mnCompressImage.submit(mnImageModel);//也可以是单个的model
```

（3）如果某些图片需要进行自定义压缩引擎压缩规则而不是用config中的可以调用下面几种方法：

```
    /**
     * 提交单张图片压缩
     * 使用内置压缩引擎
     * 自定义压缩规则需要实现{@link ICompressRule}接口
     *
     * @param imageBean
     * @param iCompressRule
     */
    mnCompressImage.submit(MNImageModel, ICompressRule) {
    }

    /**
     * 提交多张图片压缩
     * 自定义压缩引擎
     * 自定义压缩规则需要实现{@link ICompressRule}接口
     * 如果自定义压缩引擎不需要这些规则，可以不用配置
     *
     * @param imageBean
     * @param compressEngineFactory
     * @param iCompressRule
     */
    mnCompressImage.submit(MNImageModel, EngineFactory, ICompressRule)

    /**
      * 类比{@link #submit(MNImageModel)}
      * 使用配置中的压缩引擎
      *
      * @param imageBeanList
      * @param iCompressRule
      */
     mnCompressImage.submit(List<MNImageModel>, ICompressRule)

    /**
     * 类比{@link #submit(MNImageModel, EngineFactory, ICompressRule)}
     * 自定义压缩引擎
     * 自定义压缩规则
     *
     * @param mnImageBeanList
     * @param compressEngineFactory
     * @param compressRule
     */
    mnCompressImage.submit(List<MNImageModel>, EngineFactory, ICompressRule)
```

（4）设置需要的监听回调

```
mnCompressImage
    .setOnAllSuccessCallback(new OnAllSuccessCallback() {
    @Override
    public void onAllSuccess(List<MNImageModel> mnImageBeanList) {
        //得到压缩后的列表，进行所需操作
        }
    });
```

### 三、同步压缩
压缩完成返回文件(使用内置压缩引擎)
```
    //默认高清压缩
    MNSyncPhotoCompressTask.compress(file);
    //自定义option，同异步压缩配置
    MNSyncPhotoCompressTask.compress(file,iCompressRule);
```