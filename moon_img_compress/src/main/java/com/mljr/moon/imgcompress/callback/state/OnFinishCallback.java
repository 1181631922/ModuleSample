package com.mljr.moon.imgcompress.callback.state;

import com.mljr.moon.imgcompress.model.MNImageModel;

/**
 * Author： fanyafeng
 * Date： 17/12/14 下午2:36
 * Email: fanyafeng@live.cn
 */
public interface OnFinishCallback {
    /**
     * 某一张图片压缩失败回调
     * {@link MNImageModel}
     * 可以拿到是哪一张图片压缩失败，可以进行相应的处理
     *
     * @param dealImageBean 压缩失败后的实体类
     */
    void onThreadFinish(MNImageModel dealImageBean);
}
