package com.mljr.moon.imgcompress.compressrule;

import android.graphics.Bitmap;

import java.io.Serializable;
/**
 * Author： fanyafeng
 * Date： 17/12/25 上午10:07
 * Email: fanyafeng@live.cn
 * <p>
 * 定义压缩规则接口
 */
public interface ICompressRule extends Serializable{

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
