package com.mljr.moon.imgcompress;

import android.content.Context;

import com.mljr.moon.imgcompress.config.CompressConfig;
import com.mljr.moon.imgcompress.util.LogUtil;
import com.mljr.moon.imgcompress.util.Preconditions;

import java.io.Serializable;

/**
 * Author： fanyafeng
 * Date： 17/12/27 上午10:46
 * Email: fanyafeng@live.cn
 */
public class MNCompressConfig implements Serializable {

    private final static String TAG = MNCompressConfig.class.getSimpleName();

    private static CompressConfig mCompressConfig = null;

    /**
     * 简单初始化，不需要自定义
     * 需要在使用前调用
     */
    public static void initialize(Context context) {
        initialize(CompressConfig.createSimple(context));
    }

    /**
     * 自定义初始化
     * @param compressConfig
     */
    public static void initialize(CompressConfig compressConfig) {
        if (mCompressConfig == null) {
            mCompressConfig = compressConfig;
        } else {
            LogUtil.e(TAG, "MLCompressConfig have been initialize,please checkout you config");
        }
    }

    public static CompressConfig getCompressConfig() {
        Preconditions.checkNotNull(mCompressConfig, "MLCompressConfig have not been initialize");
        return mCompressConfig;
    }

}
