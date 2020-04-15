package com.ripple.permission.plugin.inject;

import com.ripple.permission.plugin.log.Utils;

import org.objectweb.asm.MethodVisitor;

import java.util.List;

/**
 * Author: fanyafeng
 * Data: 2020/4/15 11:37
 * Email: fanyafeng@live.cn
 * Description:
 */
public final class InjectProcessorBuilder {
    private MethodVisitor mVisitor;

    /**
     * 注解方法所在的class
     */
    private String mAnnoClassName;

    /**
     * 被注解的方法名
     */
    private String mAnnoMethodName;

    /**
     * 被注解的方法描述
     */
    private String mAnnoMethodDesc;

    /**
     * 注解指定的需要申请的权限信息
     */
    private List<String> mAnnoPermissions;

    /**
     * 注解指定的失败回调方法名
     */
    private String mAnnoFailMethodName;

    public void setVisitor(MethodVisitor mVisitor) {
        this.mVisitor = mVisitor;
    }

    public void setAnnoMethodName(String mAnnoMethodName) {
        this.mAnnoMethodName = mAnnoMethodName;
    }

    public void setAnnoMethodDesc(String mAnnoMethodDesc) {
        this.mAnnoMethodDesc = mAnnoMethodDesc;
    }

    public void setAnnoPermissions(List<String> mAnnoPermissions) {
        this.mAnnoPermissions = mAnnoPermissions;
    }

    public void setAnnoFailMethodName(String mAnnoFailMethodName) {
        this.mAnnoFailMethodName = mAnnoFailMethodName;
    }

    public void setAnnoClassName(String mAnnoClassName) {
        this.mAnnoClassName = mAnnoClassName;
    }

    public InjectProcessor build() {

        if (mAnnoClassName == null) {
            Utils.print("mAnnoClassName null");
            return null;
        }

        if (mVisitor == null) {
            Utils.print("MethodVisitor null");
            return null;
        }

        if (mAnnoMethodName == null) {
            Utils.print("mAnnoMethodName null");
            return null;
        }

        if (mAnnoMethodDesc == null) {
            Utils.print("mAnnoMethodDesc null");
            return null;
        }

        if (mAnnoPermissions == null || mAnnoPermissions.isEmpty()) {
            Utils.print("mAnnoPermissions null");
            return null;
        }

        if (mAnnoFailMethodName == null) {
            mAnnoFailMethodName = "";
        }
        return new InjectProcessor(mVisitor, mAnnoMethodName, mAnnoMethodDesc, mAnnoPermissions, mAnnoFailMethodName, mAnnoClassName);
    }
}
