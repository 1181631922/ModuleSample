package com.ripple.permission.plugin;

import com.ripple.permission.plugin.methodvisitor.PermissionMethodVisitor;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.List;

/**
 * Author: fanyafeng
 * Data: 2020/4/13 16:29
 * Email: fanyafeng@live.cn
 * Description:
 */
public class AgencyHandleClassVisitor extends ClassVisitor implements Opcodes {
    private String className;

    public AgencyHandleClassVisitor(ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        System.out.println("AgencyHandleClassVisitor : visit -----> started ：" + name);
        this.className = name;
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        System.out.println("AgencyHandleClassVisitor : visitMethod : " + name);
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        return new PermissionMethodVisitor(mv, className,name, desc);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        return super.visitAnnotation(desc, visible);
    }

    @Override
    public void visitEnd() {
        //System.out.println("AgencyHandleClassVisitor : visit -----> end");
        super.visitEnd();
    }
}
