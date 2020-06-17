package com.mljr.moon.imgcompress.util;

import android.util.Log;

import com.mljr.moon.imgcompress.MNCompressConfig;

/**
 * Author： fanyafeng
 * Date： 18/1/2 下午3:59
 * Email: fanyafeng@live.cn
 */
public final class LogUtil {
    private static boolean isDebug = MNCompressConfig.getCompressConfig().isDebug();
    private static final String TAG = "---| TAG输出:";

    private LogUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void i(Object msg) {
        if (isDebug) {
            Log.i(TAG, msg + " |---");
        }
    }

    public static void i(String msg) {
        if (isDebug) {
            Log.i(TAG, msg + " |---");
        }
    }

    public static void d(String msg) {
        if (isDebug) {
            Log.d(TAG, msg + " |---");
        }
    }

    public static void e(String msg) {
        if (isDebug) {
            Log.e(TAG, msg + " |---");
        }
    }

    public static void v(String msg) {
        if (isDebug) {
            Log.v(TAG, msg + " |---");
        }
    }

    // 下面四个是默认tag的函数,Int类型
    public static void i(int msg) {
        if (isDebug) {
            Log.i(TAG, msg + " |---");
        }
    }

    public static void d(int msg) {
        if (isDebug) {
            Log.d(TAG, msg + " |---");
        }
    }

    public static void e(int msg) {
        if (isDebug) {
            Log.e(TAG, msg + " |---");
        }
    }

    public static void v(int msg) {
        if (isDebug) {
            Log.v(TAG, msg + " |---");
        }
    }

    public static void i(Object tag, Object msg) {
        if (isDebug) {
            Log.i("---| " + tag + ":", msg + " |---");
        }
    }

    public static void i(String tag, String msg) {
        if (isDebug) {
            Log.i("---| " + tag + ":", msg + " |---");
        }
    }

    public static void d(String tag, String msg) {
        if (isDebug) {
            Log.i("---| " + tag + ":", msg + " |---");
        }
    }

    public static void d(String tag, int msg) {
        if (isDebug) {
            Log.i("---| " + tag + ":", msg + " |---");
        }
    }


    public static void e(String tag, String msg) {
        if (isDebug) {
            Log.i("---| " + tag + ":", msg + " |---");
        }
    }

    public static void v(String tag, String msg) {
        if (isDebug) {
            Log.i("---| " + tag + ":", msg + " |---");
        }
    }

    // 下面是传入自定义tag的函数,int类型
    public static void i(int tag, int msg) {
        if (isDebug) {
            Log.i("---| " + tag + ":", msg + " |---");
        }
    }

    public static void d(int tag, int msg) {
        if (isDebug) {
            Log.i("---| " + tag + ":", msg + " |---");
        }
    }

    public static void e(int tag, int msg) {
        if (isDebug) {
            Log.i("---| " + tag + ":", msg + " |---");
        }
    }

    public static void v(int tag, int msg) {
        if (isDebug) {
            Log.i("---| " + tag + ":", msg + " |---");
        }
    }


}
