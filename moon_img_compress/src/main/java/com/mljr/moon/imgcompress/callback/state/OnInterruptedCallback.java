package com.mljr.moon.imgcompress.callback.state;

import com.mljr.moon.imgcompress.model.MNImageModel;

/**
 * Author： fanyafeng
 * Date： 17/12/14 下午2:37
 * Email: fanyafeng@live.cn
 */
public interface OnInterruptedCallback {
    /**
     * 某一张图片压缩被打断
     * {@link MNImageModel}
     * 可以拿到是哪一张图片压缩被打断，可以进行相应的处理
     *
     * @param imageBean 压缩失败后的实体类
     */
    void onThreadInterrupted(MNImageModel imageBean);
}
