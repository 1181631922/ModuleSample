package com.mljr.moon.imgcompress.util;

import android.support.annotation.Nullable;

/**
 * Author： fanyafeng
 * Date： 17/12/27 上午10:51
 * Email: fanyafeng@live.cn
 */
public final class Preconditions {

    private Preconditions(){}

    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }
}
