package com.ripple.task.config.impl;

import com.ripple.task.config.ProcessModel;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Author: fanyafeng
 * Data: 2020/6/4 17:04
 * Email: fanyafeng@live.cn
 * Description:
 */
public class ProcessModelJavaImpl implements ProcessModel {


    @NotNull
    @Override
    public String getSourcePath() {
        return "null";
    }

    @Nullable
    @Override
    public String getTargetPath() {
        return "null";
    }

    @Override
    public void setTargetPath(@NotNull String target) {

    }

    @NotNull
    @Override
    public String parse(@NotNull String sourcePath, @Nullable String targetPath) {
        return "null";
    }
}

