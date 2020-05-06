package com.mljr.moon.imgcompress;

import com.mljr.moon.imgcompress.compressrule.ICompressRule;

import java.io.File;

/**
 * Author： fanyafeng
 * Date： 17/12/14 下午3:19
 * Email: fanyafeng@live.cn
 */
public class MNSyncCompressImage {

    /**
     * 图片同步压缩方法
     * 默认为高清压缩
     *
     * @param file 需要压缩的图片文件
     * @return
     */
    public static File compress(File file) {
        return MNCompressConfig.getCompressConfig().getEngineFactory().compress(file);
    }

    /**
     * 自定义压缩方法
     *
     * @param file          需要压缩的图片文件
     * @param iCompressRule 压缩规则
     * @return
     */
    public static File compress(File file, ICompressRule iCompressRule) {
        return MNCompressConfig.getCompressConfig().getEngineFactory().compress(file, iCompressRule);
    }

}
