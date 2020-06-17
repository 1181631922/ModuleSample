package com.mljr.moon.imgcompress.callback.state;

import com.mljr.moon.imgcompress.model.MNImageModel;

import java.util.List;

/**
 * Author： fanyafeng
 * Date： 17/12/14 上午11:13
 * Email: fanyafeng@live.cn
 */
public interface OnAllSuccessCallback {
    /**
     * 所有图片压缩成功回调
     * {@link MNImageModel}
     *
     * @param deaImageList 压缩成功后实体类列表结果
     */
    void onAllSuccess(List<MNImageModel> deaImageList);
}
