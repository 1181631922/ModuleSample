package com.ripple.permission.plugin.annotationvisitor;

import com.ripple.permission.plugin.model.PermissionAnnoModel;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Opcodes;


/**
 * Author: fanyafeng
 * Data: 2020/4/13 20:22
 * Email: fanyafeng@live.cn
 * Description:
 */
public class PermissionAnnoArrayVisitor extends AnnotationVisitor {

    private PermissionAnnoModel model;

    public PermissionAnnoArrayVisitor(AnnotationVisitor annotationVisitor, PermissionAnnoModel model) {
        super(Opcodes.ASM5, annotationVisitor);
        this.model = model;
    }

    public PermissionAnnoArrayVisitor(AnnotationVisitor annotationVisitor) {
        super(Opcodes.ASM5, annotationVisitor);
    }

    @Override
    public void visit(String name, Object value) {
        super.visit(name, value);
        if (model != null) {
            model.getPermissionList().add((String) value);
        }
    }
}
