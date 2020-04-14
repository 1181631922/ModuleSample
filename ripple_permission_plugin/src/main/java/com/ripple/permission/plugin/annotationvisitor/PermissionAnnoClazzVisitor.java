package com.ripple.permission.plugin.annotationvisitor;


import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Author: fanyafeng
 * Data: 2020/4/13 20:22
 * Email: fanyafeng@live.cn
 * Description:
 */
public class PermissionAnnoClazzVisitor extends AnnotationVisitor {

    public PermissionAnnoClazzVisitor(AnnotationVisitor annotationVisitor) {
        super(Opcodes.ASM5, annotationVisitor);
    }

    @Override
    public void visit(String name, Object value) {
        super.visit(name, value);
    }

    @Override
    public void visitEnum(String name, String descriptor, String value) {
        super.visitEnum(name, descriptor, value);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String name, String descriptor) {
        return super.visitAnnotation(name, descriptor);
    }

    @Override
    public AnnotationVisitor visitArray(String name) {
        return new PermissionAnnoArrayVisitor(super.visitArray(name));
    }
}
