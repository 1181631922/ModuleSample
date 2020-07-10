# 日志工具类：ripple_log
`android log`工具类，出发点是为了方便log使用，以及统一`log`想写个统一的工具类，想要解决的问题是既能不改变原有`LogUtil`的还能再加上个人定制的`log`，以便完成统一
还是老样子，采用接口的形式让用户能够自己注入个人的`log`工具类

## 零、接入使用
### 0.1 接入

```
//根目录gradle
maven {
            url "https://dl.bintray.com/fanyafeng/ripple"
        }
        
        
//module目录gradle
implementation 'com.ripple.component:log:0.0.2'
```
### 0.2 使用
因为内部有默认实现类，这里就省了初始化的流程了，如果想兼容现有的逻辑的话查看下方详解(以`kotlin`作为示例说明，`java`代码还是畅通方式的调用)

### 0.2.1 直接打印日志：

```
logD("打印日志")
```

下方为例子
```
//直接打印日志，因为msg常用，根据kotlin的建议，将其放在的第一个位置上了，下面的有详细说明
logD("打印日志")
logI("打印日志")
//等等
```
### 0.2.2 类扩展打印
简单的举个例子，具体使用看下方代码详解

```
5.logD()

var name="姓名"
name.logD("可以加入msg")
```
原有代码
```
    private fun line(e: Exception): Int {
        val trace = e.stackTrace
        if (trace.isEmpty()) {
            return -1
        }
        return trace[0].lineNumber
    }
```
在不影响逻辑的情况下添加日志打印

```
    private fun line(e: Exception): Int {
        val trace = e.stackTrace
        if (trace.isEmpty()) {
            return -1
        }
        return trace[0].lineNumber.logD()
    }
```
### 0.2.3 跳转到打印日志位置

```
5.logDWithClassJump()

var name="姓名"
name.logDWithClassJump("可以加入msg")

//还有个顶层函数 withClassJump()使用此函数直接跳转到此处
withClassJump()
```
还用上方的举例

```
    private fun line(e: Exception): Int {
        val trace = e.stackTrace
        if (trace.isEmpty()) {
            return -1
        }
        return trace[0].lineNumber.logDWithClassJump()
    }
//还有个顶层函数 withClassJump()使用此函数直接跳转到此处
```
这样打印出的日志为蓝色可以双击跳转到当前类的当前函数

## 一、目标
1. 在不影响原有`LogUtil`的前提下，添加此日志工具类
2. 能够控制日志打印的级别以及关闭的时机
3. 在`Kotlin`中能够添加类扩展完成打印日志的前提下不影响原有的代码逻辑例如：`val name=s -> val name=s.logD()`
4. 能够打印出日志所在位置的相应的类，方法以及行号
5. 能够定位日志位置，类似打印崩溃信息

## 二、抽象
目标已经确认，剩下的就是根据目标去抽离出相应的框架，需要结合系统`Log`的源码去剖析。
查看系统源码后不过百行，而且是对`println`的封装，此时抽离出系统的方法以及自己想要的方法为接口即可

## 三、编码
### 3.1 日志打印接口
代码中的注释很详细了，这里就不做细说了

```
/**
 * Author: fanyafeng
 * Data: 2020/7/8 19:22
 * Email: fanyafeng@live.cn
 * Description: log统一接口
 */
interface ILog {
    /**
     * [android.util.Log.v]
     */
    fun v(tag: String, msg: String)

    fun v(tag: String, msg: String, tr: Throwable)

    /**
     * [android.util.Log.d]
     */
    fun d(tag: String, msg: String)

    fun d(tag: String, msg: String, tr: Throwable)

    /**
     * [android.util.Log.i]
     */
    fun i(tag: String, msg: String)

    fun i(tag: String, msg: String, tr: Throwable)

    /**
     * [android.util.Log.w]
     */
    fun w(tag: String, msg: String)

    fun w(tag: String, tr: Throwable)

    fun w(tag: String, msg: String, tr: Throwable)

    /**
     * [android.util.Log.e]
     */
    fun e(tag: String, msg: String)

    fun e(tag: String, msg: String, tr: Throwable)

    fun getStackTraceString(tr: Throwable): String

    /**
     * 获取当前打印日志信息的方法名
     */
    fun getPrintLogMethodName(): String

    /**
     * 以下三个结合可以打印蓝色log双击直接定位到具体类
     * 具体使用如下：
     * java中：
     * 注：只能在java类中使用，并且跳转到相应位置，kotlin不适用
     * (getPrintLogClassName():getPrintLogLineNumber())
     *
     * java&kotlin:
     * (getPrintLogFileName():getPrintLogLineNumber())
     */

    /**
     * 获取当前打印日志信息的行号
     */
    fun getPrintLogLineNumber(): Int

    /**
     * 获取当前打印日志信息的文件名
     */
    fun getPrintLogFileName(): String

    /**
     * 获取当前打印日志信息的类名
     */
    fun getPrintLogClassName(): String


    //----------------------------------------------------------
}
```
### 3.2 统一的日志管理配置
这里是采用单例的模式进行注入，可以进行的配置有以下几点
1. 是否打印所有日志，优先级最高
2. 各个`Level`的打印配置，必须是`printAll`以及相应`Level`为true时才可以打印此`Level`的日志
3. 统一的日志`TAG`头，默认的为`RippleTag`
4. 统一的日志`MSG`头，默认的为`RippleMsg`
5. 手动划重点，这里就是核心日志`ILog`的实现类，这里为了方便使用有一个默认的实现类`LogImpl`
实现代码就不贴了

### 3.2 kotlin的扩展类
结合`kotlin`的语法糖，方便其在`kotlin`中使用，这里主要是分了三个方面进行扩展的

#### 3.2.1 Log的扩展类
添加顶层函数，能够在任意类中直接写方法名打出`log`，替代之前的`类名.方法名`的形式，实现类还是统一的入口`RippleLog`

```
/**
 * Author: fanyafeng
 * Data: 2020/7/8 19:45
 * Email: fanyafeng@live.cn
 * Description: Log扩展工具类，主要用于KT类调用
 */

/**
 * 以下为Log.v的工具类，等同调用：
 * Log.v()
 * 因为tag经常是固定的并且非空，而且一般意义不太大
 * 把tag放在第二个参数中，这样就能更好地利用msg这个参数
 */
@JvmOverloads
fun logV(msg: String, tag: String = RippleLog.getInstance().tag, tr: Throwable? = null) {
    if (tr == null) {
        RippleLog.getInstance().log?.v(tag, msg)
    } else {
        RippleLog.getInstance().log?.v(tag, msg, tr)
    }
}

/**
 * 以下为Log.d的工具类，等同调用：
 * Log.d()
 * 因为tag经常是固定的并且非空，而且一般意义不太大
 * 把tag放在第二个参数中，这样就能更好地利用msg这个参数
 */
@JvmOverloads
fun logD(msg: String, tag: String = RippleLog.getInstance().tag, tr: Throwable? = null) {
    if (tr == null) {
        RippleLog.getInstance().log?.d(tag, msg)
    } else {
        RippleLog.getInstance().log?.d(tag, msg, tr)
    }
}

@JvmOverloads
fun logI(msg: String, tag: String = RippleLog.getInstance().tag, tr: Throwable? = null) {
    if (tr == null) {
        RippleLog.getInstance().log?.i(tag, msg)
    } else {
        RippleLog.getInstance().log?.i(tag, msg, tr)
    }
}

@JvmOverloads
fun logW(msg: String? = null, tr: Throwable? = null, tag: String = RippleLog.getInstance().tag) {
    if (msg != null) {
        if (tr != null) {
            RippleLog.getInstance().log?.w(tag, msg, tr)
        } else {
            RippleLog.getInstance().log?.w(tag, msg)
        }
    } else {
        if (tr != null) {
            RippleLog.getInstance().log?.w(tag, tr)
        } else {
            logE("msg和Throwable不能同时为空")
        }
    }
}

@JvmOverloads
fun logE(msg: String, tag: String = RippleLog.getInstance().tag, tr: Throwable? = null) {
    if (tr == null) {
        RippleLog.getInstance().log?.e(tag, msg)
    } else {
        RippleLog.getInstance().log?.e(tag, msg, tr)
    }
}
```
#### 3.2.2 添加类的日志扩展
为了方便打印`log`并且不影响代码结构，添加类扩展方法进行日志的打印

```
/**
 * Author: fanyafeng
 * Data: 2020/7/9 14:11
 * Email: fanyafeng@live.cn
 * Description: 常用数据类型的log扩展类
 */
@JvmOverloads
fun <T> T.logV(tag: String = RippleLog.getInstance().tag): T {
    logV(this.toString(), tag)
    return this
}

@JvmOverloads
fun <T> T.logVWithClassJump(tag: String = RippleLog.getInstance().tag): T {
    /**
     * 线程安全
     */
    val stringBuffer = getPrintLogHeaderWithMsg()
    stringBuffer.append(this.toString())
    logV(
        stringBuffer.toString(),
        tag
    )
    return this
}

@JvmOverloads
fun <T> T.logD(tag: String = RippleLog.getInstance().tag): T {
    logD(this.toString(), tag)
    return this
}

@JvmOverloads
fun <T> T.logDWithClassJump(tag: String = RippleLog.getInstance().tag): T {
    /**
     * 线程安全
     */
    val stringBuffer = getPrintLogHeaderWithMsg()
    stringBuffer.append(this.toString())
    logD(
        stringBuffer.toString(),
        tag
    )
    return this
}

@JvmOverloads
fun <T> T.logI(tag: String = RippleLog.getInstance().tag): T {
    logI(this.toString(), tag)
    return this
}

@JvmOverloads
fun <T> T.logIWithClassJump(tag: String = RippleLog.getInstance().tag): T {
    /**
     * 线程安全
     */
    val stringBuffer = getPrintLogHeaderWithMsg()
    stringBuffer.append(this.toString())
    logI(
        stringBuffer.toString(),
        tag
    )
    return this
}

@JvmOverloads
fun <T> T.logW(tag: String = RippleLog.getInstance().tag): T {
    logW(this.toString(), tag = tag)
    return this
}

@JvmOverloads
fun <T> T.logWWithClassJump(tag: String = RippleLog.getInstance().tag): T {
    /**
     * 线程安全
     */
    val stringBuffer = getPrintLogHeaderWithMsg()
    stringBuffer.append(this.toString())
    logW(
        stringBuffer.toString(),
        tag = tag
    )
    return this
}

@JvmOverloads
fun <T> T.logE(tag: String = RippleLog.getInstance().tag): T {
    logE(this.toString(), tag)
    return this
}

@JvmOverloads
fun <T> T.logEWithClassJump(tag: String = RippleLog.getInstance().tag): T {
    /**
     * 线程安全
     */
    val stringBuffer = getPrintLogHeaderWithMsg()
    stringBuffer.append(this.toString())
    logE(
        stringBuffer.toString(),
        tag
    )
    return this
}
```
#### 3.2.3 打印日志信息的顶层函数
将常用的打印日志信息的工具方法添加到顶层函数中，方便`Kotlin`类的调用

```
/**
 * Author: fanyafeng
 * Data: 2020/7/9 13:54
 * Email: fanyafeng@live.cn
 * Description: 日志打印关联信息辅助类
 */

/**
 * 打印堆栈信息
 */
fun getStackTraceString(tr: Throwable): String? {
    return RippleLog.getInstance().log?.getStackTraceString(tr)
}

/**
 * 打印当前log所在的方法名字
 */
fun getPrintLogMethodName(): String? {
    return RippleLog.getInstance().log?.getPrintLogMethodName()
}

/**
 * 打印当前log的行数
 */
fun getPrintLogLineNumber(): Int? {
    return RippleLog.getInstance().log?.getPrintLogLineNumber()
}

/**
 * 打印日志信息的文件名
 */
fun getPrintLogFileName(): String? {
    return RippleLog.getInstance().log?.getPrintLogFileName()
}

/**
 * 打印日志信息的类名
 */
fun getPrintLogClassName(): String? {
    return RippleLog.getInstance().log?.getPrintLogClassName()
}

/**
 * 获取打印log的头部
 */
fun getPrintLogHeader(): StringBuffer {
    val stringBuffer = StringBuffer()
    stringBuffer.append("(")
    stringBuffer.append(getPrintLogFileName())
    stringBuffer.append(":")
    stringBuffer.append(getPrintLogLineNumber())
    stringBuffer.append(") ")
    return stringBuffer
}

/**
 * 获取打印log的头部
 * 带有msg
 */
fun getPrintLogHeaderWithMsg(): StringBuffer {
    val stringBuffer = getPrintLogHeader()
    stringBuffer.append(RippleLog.getInstance().msg)
    stringBuffer.append(":")
    return stringBuffer
}

/**
 * 带有类跳转的日志信息
 * 返回值为(类名:行号)
 */
@JvmOverloads
fun withClassJump(msg: String? = null, tag: String = RippleLog.getInstance().tag): String {
    val fileNameAndLineNumber = getPrintLogHeader()
    if (msg == null) {
        logD(tag = tag, msg = fileNameAndLineNumber.toString())
    } else {
        logD(tag = tag, msg = getPrintLogHeaderWithMsg().append(msg).toString())
    }
    return fileNameAndLineNumber.toString()
}
```
