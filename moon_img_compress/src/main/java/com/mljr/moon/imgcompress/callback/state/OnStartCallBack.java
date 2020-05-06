package com.mljr.moon.imgcompress.callback.state;

import com.mljr.moon.imgcompress.model.MNImageModel;

/**
 * Author： fanyafeng
 * Date： 17/12/14 下午2:34
 * Email: fanyafeng@live.cn
 */
public interface OnStartCallBack {
    /**
     * 获取正要开始压缩的图片
     * {@link MNImageModel}
     *
     * @param imageBean 获取相应的实体类
     */
    void onThreadProgressStart(MNImageModel imageBean);
}
