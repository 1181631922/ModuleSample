package com.ripple.permission.plugin.methodvisitor;

import com.ripple.permission.plugin.annotationvisitor.PermissionAnnoMethodVisitor;
import com.ripple.permission.plugin.inject.InjectProcessorBuilder;
import com.ripple.permission.plugin.model.PermissionAnnoModel;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;

/**
 * Author: fanyafeng
 * Data: 2020/4/13 20:42
 * Email: fanyafeng@live.cn
 * Description:
 */
public class PermissionMethodVisitor extends MethodVisitor {
    private String methodName;
    private String methodDesc;
    private String className;
    private PermissionAnnoMethodVisitor annoMethodVisitor;
    private boolean firstLabel = true;
    private boolean canInject = false;

    private PermissionAnnoModel model;

    private InjectProcessorBuilder builder = new InjectProcessorBuilder();

    public PermissionMethodVisitor(MethodVisitor methodVisitor, String className, String methodName, String methodDesc) {
        super(Opcodes.ASM5, methodVisitor);
        builder.setAnnoClassName(className);
        builder.setAnnoMethodName(methodName);
        builder.setAnnoMethodDesc(methodDesc);
        builder.setVisitor(methodVisitor);

        this.methodName = methodName;
        this.methodDesc = methodDesc;
        this.className = className;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        if ("Lcom/ripple/permission/annotation/NeedPermission;".equals(descriptor)) {
            annoMethodVisitor = new PermissionAnnoMethodVisitor(super.visitAnnotation(descriptor, visible), className, descriptor);
            canInject = true;
            return annoMethodVisitor;
        } else {
            return super.visitAnnotation(descriptor, visible);
        }
    }

    @Override
    public void visitCode() {
        super.visitCode();
        //            LogUtil.d("进入到visitCode方法");
        //            PermissionAnnoModel model = annoMethodVisitor.getModel();
        //这里的desc指的是failMethod的desc
        //            addFunBody(mv, model, methodDesc);
        if (canInject) {
            model = annoMethodVisitor.getModel();
            builder.setAnnoPermissions(model.getPermissionList());
            builder.setAnnoFailMethodName(model.getFailMethodName());
        }
    }

    @Override
    public void visitLabel(Label label) {
        if (firstLabel && canInject) {
            if (model != null) {
                builder.build().inject(label);
                firstLabel = false;
            }
        }
        super.visitLabel(label);
    }

    @Override
    public void visitInsn(int opcode) {
        super.visitInsn(opcode);
    }


    private void addFunBody(MethodVisitor mv, PermissionAnnoModel model, String methodDesc) {

        String failMethodName = model.getFailMethodName();
        ArrayList<String> permissionList = model.getPermissionList();

        Label label = new Label();

        mv.visitLabel(label);
        mv.visitTypeInsn(Opcodes.NEW, "java/util/ArrayList");
        mv.visitInsn(Opcodes.DUP);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/util/ArrayList", "<init>", "()V", false);
        mv.visitVarInsn(Opcodes.ASTORE, 2);


        for (int i = 0; i < permissionList.size(); i++) {
            mv.visitVarInsn(Opcodes.ALOAD, 2);
            mv.visitLdcInsn(permissionList.get(i));
            mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", "add", "(Ljava/lang/Object;)Z", true);
            mv.visitInsn(Opcodes.POP);
        }


        mv.visitFieldInsn(Opcodes.GETSTATIC, "com/ripple/permission/RipplePermission", "INSTANCE", "Lcom/ripple/permission/RipplePermission;");
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitVarInsn(Opcodes.ALOAD, 2);
        mv.visitLdcInsn(methodName);
        mv.visitLdcInsn(methodDesc);
        mv.visitVarInsn(Opcodes.ALOAD, 1);
        mv.visitLdcInsn(failMethodName);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/ripple/permission/RipplePermission", "doCheckPermission", "(Ljava/lang/Object;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;)Z", false);


        mv.visitLocalVariable("list", "Ljava/util/List;", "Ljava/util/List<Ljava/lang/Object;>;", label, label, 1);
        mv.visitLocalVariable("permissionList", "Ljava/util/List;", "Ljava/util/List<Ljava/lang/String;>;", label, label, 2);

        Label endLabel = new Label();
        mv.visitJumpInsn(Opcodes.IFNE, endLabel);
        mv.visitInsn(Opcodes.RETURN);
        mv.visitLabel(endLabel);
        mv.visitFrame(Opcodes.F_APPEND, 0, new Object[]{"java/util/List"}, 0, null);
    }
}
