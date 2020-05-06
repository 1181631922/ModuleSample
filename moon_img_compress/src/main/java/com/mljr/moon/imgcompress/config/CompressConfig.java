package com.mljr.moon.imgcompress.config;

import android.content.Context;

import com.mljr.moon.imgcompress.engine.EngineFactory;
import com.mljr.moon.imgcompress.engine.MNCompressEngine;
import com.mljr.moon.imgcompress.util.Preconditions;


/**
 * Author： fanyafeng
 * Date： 17/12/27 上午11:41
 * Email: fanyafeng@live.cn
 */
public class CompressConfig {
    private final Context context;
    /**
     * 图片压缩路径
     */
    private final String imagePath;
    /**
     * 模式
     */
    private final boolean isDebug;
    /**
     * 压缩引擎
     */
    private final EngineFactory engineFactory;

    private CompressConfig(Builder builder) {
        this.context = Preconditions.checkNotNull(builder.context);
        this.imagePath = builder.imagePath;
        this.isDebug = builder.isDebug;
        this.engineFactory = builder.engineFactory;
    }

    public static class Builder {
        private final Context context;
        private String imagePath = null;
        private boolean isDebug = false;
        private EngineFactory engineFactory = MNCompressEngine.ML_COMPRESS_ENGINE;

        public Builder(Context context) {
            this.context = Preconditions.checkNotNull(context);
        }

        public Builder setImagePath(String imagePath) {
            this.imagePath = imagePath;
            return this;
        }

        public Builder setDebug(boolean debug) {
            isDebug = debug;
            return this;
        }

        public Builder setEngineFactory(EngineFactory engineFactory) {
            this.engineFactory = engineFactory;
            return this;
        }

        public CompressConfig build() {
            return new CompressConfig(this);
        }
    }

    public String getImagePath() {
        return imagePath;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public EngineFactory getEngineFactory() {
        return engineFactory;
    }

    public Context getContext() {
        return context;
    }

    /**
     * 如果用户不配置的话，默认使用此配置
     *
     * @return
     */
    public static CompressConfig createSimple(Context context) {
        return new Builder(context).build();
    }
}
