# 自己动手编写http框架（一）
之前都是自己想好思路然后编写框架，也可能是之前的比较简单吧，不用那么费时间，然后现在要写最常用的`http`使用框架，相信大家基本都有相同的经历，如果不是在一个公司从头开始的，那么基本公司都有相对完善的`http`框架，很少有从0到1的过程，这也是为啥这个框架到现在才去写的原因，因为之前大部分是时间处于伸手党，用的多，写得少，或者是从半路开始写的，这里想根据自己的思路从头开始写，一步一步来，正好也开始写博客来记录一下过程。
## 一、http请求
第一步就是先去使用，然后再去抽离
本来想找个`mock`数据的，但是好多不能用了，就用`springboot`简单搭了一个，没有用数据库，这样测试起来也比较方便
现在基本都使用的是`okhttp`了，这里基于`okhttp`，这里强调一下，其实这样从框架层面来说是不对的，应该参考一下图片框架，底层使用的是`HTTPURLconnection`，然后支持用户去注入网络请求框架，下面开始分析一下常用的网络的请求方式，以及需要设置哪些属性等。
做这个不要着急，一步一步来
PS:我依赖的`okhttp`是`4.7.2`，已经全部是用`Kotlin`写的了，正好同时自己也去加深一下对`Kotlin`的理解，`http`库里面依赖了之前写的一些工具库。
### 1.1 http get请求
`http`的请求应该子线程中，但是请求本身是同步，这里做了两个测试，一个是同步，一个是异步
带有`header`的暂时先不去考虑，以下暂时罗列一下`get`请求的几种方式：
1. 最常见的就是`path`不加任何参数
2. 另外就是后面以`params`添加参数`path?xxx=xxx&yyy=yyy`
3. 还有就是路径添加参数`path/{xxx}/{yyy}"`
4. 还有就是以`json`的方式进行传参

以上列举的常用的四种情况
#### 1.1.1 第一种情况：
咱们常用的第一种不带参数的异步请求情况：
```
private fun httpGetASync1() {
    val client = OkHttpClient()
    val urlBuilder = url.toHttpUrlOrNull()?.newBuilder()
    val urlResult = urlBuilder?.build()

    val request = Request.Builder()
        .url(urlResult!!)
        .get()

    request.build().headers.logD()
    urlResult.logD()

    client.newCall(request.build()).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.toString().logD()
        }

        override fun onResponse(call: Call, response: Response) {
            val result = response.body?.string()
            result.logD()
            call.isCanceled().logD()
        }

    })
}
```
这里使用的是`OKhttp`自带的异步请求，暂时先基于这一种情况进行封装，现在类型是确认的，但是咱们的目标肯定是从外部传入的，先来定义下调用方式：

```
RippleHttp.getInstance()
            .get(URL, object : OnHttpResult.OnHttpSimpleResult<T> {
                override fun onItemSuccess(successResult: T) {
                    successResult.toLogD()
                }
            })
```
这应该是咱们的目标(一步一步来，暂时不考虑`param`以及`header`等等)，通过外部传入`url`以及泛型`T`来将请求成功的数据进行解析，到现在咱们开始考虑下一步了，`url`传的话肯定是很容易的，下一步比较麻烦的是需要解析方法上的泛型`T`，这里暂时先不细讲，后面会细讲，目标明确了，剩下的就是去按照自己想要的调用方式去编写框架了。
##### 1.1.1.1 首先定义回调
回调的话这里还是需要慎重一下的，这里定义了五种：`成功，完成，开始，进行中，失败`，下面简述一下为什么会有这五种
1. `OnItemSuccess<T>`成功回调，这个是肯定的，咱们的目的就是为了获取数据传给使用者，采用泛型是因为需要依据用户的需求进行解析
2. `OnItemFinish<Boolean>`结束回调，不论失败还是成功，都会走到此回调，因为使用者有时候需要在结束时做相应操作，不论此请求成功与否。
3. `OnItemStart<Unit>`开始回调，标志请求的一种状态
4. `OnItemDoing<Long>`任务进行中回调，这里是为了兼容下载文件时的请求，`0L-100L`为其正常范围，失败的话会返回`-1L`
5. `OnItemFailed<BaseException>`失败回调，会返回失败的原因`msg`以及相应的`code`

##### 1.1.1.2 再有就是解析泛型
这里是个大块，暂时放着，后面会细讲，因为这里涉及到了`java`以及`kotlin`还是有区别的

至此所有准备工作都完成，下面开始封装：

```
    /**
     * 以接口的方式抽离
     */
    fun <S> parseJsonString(result: String?, typeOf: Type): S {
        return Gson().fromJson(result, typeOf)
    }

    /**
     * 异步get请求
     */
    fun <T> get(url: String, callback: OnHttpResult<T>) {
        callback.onItemStart(Unit)
        /**
         * 构造urlBuilder
         */
        val urlBuilder = url.toHttpUrlOrNull()?.newBuilder()

        /**
         * 构造url
         * 使用了大量的构造者模式
         */
        val urlResult = urlBuilder?.build()

        /**
         * 构造request
         */
        val request = Request.Builder()
            .url(urlResult!!)
            .get()
        val requestResult = request.build()
        /**
         * 发起异步请求
         */
        callback.onItemDoing(OnItemDoing.CODE_ITEM_DOING_START)
        RippleHttpClient.getInstance().newCall(requestResult).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                /**
                 * 任务进行中回调
                 * 任务失败，返回-1L
                 */
                callback.onItemDoing(OnItemDoing.CODE_ITEM_DOING_FAILED)

                /**
                 * 走到结束回调但是任务没完成
                 * 返回false
                 */
                callback.onItemFinish(false)
                /**
                 * call为回传的结果
                 */
                callback.onItemFailed(BaseException(e))
            }

            override fun onResponse(call: Call, response: Response) {
                /**
                 * 走到结束回调并且任务完成
                 * 返回true
                 */
                callback.onItemFinish(true)

                /**
                 * 任务进行中回调
                 * 任务成功，返回100L
                 */
                callback.onItemDoing(OnItemDoing.CODE_ITEM_DOING_FINISH)

                val result = response.body?.string()

                val clazz = parseItemParamType(callback)
                clazz.toLogD("callback 打印泛型名字：")

                val backResult = parseJsonString<T>(result, clazz)
                /**
                 * 任务完成回调
                 * 返回成功model
                 */
                callback.onItemSuccess(backResult)
            }

        })
    }
```
完成后调用：
PS：这里我自己通过`SpringBoot`搭建了一个服务器，`Mock`的好多平台不能用了，还好自己会后端，嘿嘿

```
    /**
     * http get callback请求封装
     */
    private fun httpGetASyncPackCallback1() {
        RippleHttp.getInstance().get(GET_USER, object : OnHttpResult.OnHttpSimpleResult<User> {
            override fun onItemStart(startResult: Unit) {
                super.onItemStart(startResult)
                logD("HTTP任务开始", "Http请求封装：")
            }

            override fun onItemDoing(doingResult: Long) {
                super.onItemDoing(doingResult)
                doingResult.toLogD("Http请求封装：")
            }

            override fun onItemSuccess(successResult: User) {
                successResult.toLogD("Http请求封装：")
            }

            override fun onItemFailed(failedResult: BaseException) {
                super.onItemFailed(failedResult)
                failedResult.toLogD("Http请求封装：")
            }

            override fun onItemFinish(finishResult: Boolean) {
                super.onItemFinish(finishResult)
                finishResult.toLogD("Http请求封装：")
            }
        })
    }
```
第一部分暂时先到这里，感觉这篇文章会很长

