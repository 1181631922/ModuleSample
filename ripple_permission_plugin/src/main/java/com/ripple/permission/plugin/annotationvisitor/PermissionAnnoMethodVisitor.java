package com.ripple.permission.plugin.annotationvisitor;

import com.ripple.permission.plugin.inject.InjectProcessor;
import com.ripple.permission.plugin.log.LogUtil;
import com.ripple.permission.plugin.model.PermissionAnnoModel;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Author: fanyafeng
 * Data: 2020/4/13 20:22
 * Email: fanyafeng@live.cn
 * Description:
 */
public class PermissionAnnoMethodVisitor extends AnnotationVisitor {

    //方法名字
    private String className;
    //方法描述
    private String desc;

    //注解方法model
    private PermissionAnnoModel model = new PermissionAnnoModel();

    public PermissionAnnoModel getModel() {
        return model;
    }

    public PermissionAnnoMethodVisitor(int api) {
        super(api);
    }

    public PermissionAnnoMethodVisitor(AnnotationVisitor annotationVisitor, String className, String desc) {
        super(Opcodes.ASM5, annotationVisitor);
        this.className = className;
        this.desc = desc;
        LogUtil.d("PermissionAnnoMethod 初始化方法");
    }

    @Override
    public void visit(String name, Object value) {
        super.visit(name, value);
        //添加失败回调方法名字
        if (InjectProcessor.ANNO_METHOD_FIELD.equals(name)) {
            model.setFailMethodName((String) value);
        }
    }

    @Override
    public AnnotationVisitor visitArray(String name) {
        if (InjectProcessor.ANNO_METHOD_PER.equals(name)) {
            return new PermissionAnnoArrayVisitor(super.visitArray(name), model);
        } else {
            return super.visitArray(name);
        }
    }
}
