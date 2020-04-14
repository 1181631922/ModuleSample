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
    private List<String> ignoreContainPathList;
    private List<String> ignorePathList;

    public AgencyHandleClassVisitor(ClassVisitor cv, List<String> ignoreContainPathList, List<String> ignorePathList) {
        super(Opcodes.ASM5, cv);
        this.ignoreContainPathList = ignoreContainPathList;
        this.ignorePathList = ignorePathList;
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

        if (ignoreContainPathList != null && ignoreContainPathList.size() > 0) {
            for (String path : ignoreContainPathList) {
                if (className != null && className.contains(path)) {
                    return super.visitMethod(access, name, desc, signature, exceptions);
                }
            }
        }

        if (ignorePathList != null && ignorePathList.size() > 0) {
            for (String path : ignorePathList) {
                if (className != null && className.equals(path)) {
                    return super.visitMethod(access, name, desc, signature, exceptions);
                }
            }
        }


        return new PermissionMethodVisitor(mv, className, desc);
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
