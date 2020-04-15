package com.ripple.permission.plugin.inject;

import com.ripple.permission.plugin.descreader.DescReader;
import com.ripple.permission.plugin.log.LogUtil;
import com.ripple.permission.plugin.log.Utils;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: fanyafeng
 * Data: 2020/4/15 09:31
 * Email: fanyafeng@live.cn
 * Description:
 */
public class InjectProcessor {

    public static String ANNO_CLASS_DESC = "Lcom/ripple/permission/annotation/NeedPermission;";
    public static String ANNO_METHOD_FIELD = "method";
    public static String ANNO_METHOD_PER = "permissions";

    private final String className = "com/ripple/permission/RipplePermission";
    private final String desc = "Lcom/ripple/permission/RipplePermission;";
    private final String fieldName = "INSTANCE";
    private final String methodName = "doCheckPermission";
    private final String methodDesc = "(Ljava/lang/Object;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;)Z";


    private final MethodVisitor methodVisitor;

    private final String annoClassName;

    private final String annoMethodName;

    private final String annoMethodDesc;

    private final List<String> annoPermissionList;

    private final String annoFailMethodName;

    public InjectProcessor(MethodVisitor methodVisitor,
                           String annoMethodName,
                           String annoMethodDesc,
                           List<String> annoPermissionList,
                           String annoFailMethodName,
                           String annoClassName) {
        this.methodVisitor = methodVisitor;
        this.annoClassName = annoClassName;
        this.annoMethodName = annoMethodName;
        this.annoMethodDesc = annoMethodDesc;
        this.annoPermissionList = annoPermissionList;
        this.annoFailMethodName = annoFailMethodName;
    }

    public void inject(Label label) {
        LogUtil.d("inject");
        checkReturnType();
        printInfo();
        methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, className, fieldName, desc);

        methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
        loadPermissionsInStack();
        methodVisitor.visitLdcInsn(annoMethodName);
        methodVisitor.visitLdcInsn(annoMethodDesc);
        loadParamInStack();
        loadFailMethodNameInStack();

        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, className, methodName, methodDesc, false);
        methodVisitor.visitJumpInsn(Opcodes.IFNE, label);
        methodVisitor.visitInsn(Opcodes.RETURN);
        LogUtil.d("inject尾部");
    }

    private void printInfo() {
        LogUtil.d("printInfo");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("mAnnoClassName = " + annoClassName);
        stringBuilder.append("\nmAnnoMethodName = " + annoMethodName);
        stringBuilder.append("\nmAnnoMethodDesc = " + annoMethodDesc);
        stringBuilder.append("\nmAnnoPermissions = " + annoPermissionList.toString());
        stringBuilder.append("\nmAnnoFailMethodName = " + annoFailMethodName);
        Utils.print(stringBuilder.toString());
    }

    private void loadPermissionsInStack() {
        LogUtil.d("loadPermissionsInStack");
        int count = annoPermissionList.size();
        String type = Type.getInternalName(ArrayList.class);
        methodVisitor.visitTypeInsn(Opcodes.NEW, type);
        methodVisitor.visitInsn(Opcodes.DUP);
        methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, type, "<init>", "()V", false);

        for (int i = 0; i < count; i++) {
            methodVisitor.visitInsn(Opcodes.DUP);
            methodVisitor.visitLdcInsn(annoPermissionList.get(i));
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                    type,
                    "add",
                    "(Ljava/lang/Object;)Z",
                    false);
            methodVisitor.visitInsn(Opcodes.POP);
        }
    }

    /**
     * 把参数列表封装成列表压入栈
     *
     * @param
     */
    private void loadParamInStack() {
        LogUtil.d("loadParamInStack");
        int count = 0;
        String params = annoMethodDesc.substring(1).split("\\)")[0];
        DescReader descReader = new DescReader(params);
        String type = Type.getInternalName(ArrayList.class);
        methodVisitor.visitTypeInsn(Opcodes.NEW, type);
        methodVisitor.visitInsn(Opcodes.DUP);
        methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, type, "<init>", "()V", false);

        while (descReader.hasNext()) {
            count++;
            methodVisitor.visitInsn(Opcodes.DUP);
            char paramType = descReader.readNextLoadType();
            visitVarInsnBoxing(paramType, count, methodVisitor);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                    type,
                    "add",
                    "(Ljava/lang/Object;)Z",
                    false);
            methodVisitor.visitInsn(Opcodes.POP);
        }
    }

    private void loadFailMethodNameInStack() {
        LogUtil.d("loadFailMethodNameInStack");
        if (annoFailMethodName == null || annoFailMethodName.isEmpty()) {
            methodVisitor.visitInsn(Opcodes.ACONST_NULL);
        } else {
            methodVisitor.visitLdcInsn(annoFailMethodName);
        }
    }

    private void checkReturnType() {
        LogUtil.d("checkReturnType");
        String returnType = annoMethodDesc.substring(annoMethodDesc.indexOf(")") + 1);
        if (!"V".equals(returnType)) {
            new RuntimeException("方法的返回类型必须为空，" + annoClassName + "." + annoMethodName + "的返回类型是" + returnType).printStackTrace();
            System.exit(1);
        }
    }

    //将基础数据类型替换为包装类型入栈
    private static void visitVarInsnBoxing(char type, int index, MethodVisitor visitor) {
        LogUtil.d("visitVarInsnBoxing");
        switch (type) {
            case 'Z':
                String booleanType = Type.getInternalName(Boolean.class);
                visitor.visitVarInsn(Opcodes.ILOAD, index);
                visitor.visitMethodInsn(Opcodes.INVOKESTATIC, booleanType, "valueOf", "(Z)L" + booleanType + ";", false);
                break;
            case 'C':
                String typeName = Type.getInternalName(Character.class);
                visitor.visitVarInsn(Opcodes.ILOAD, index);
                visitor.visitMethodInsn(Opcodes.INVOKESTATIC, typeName, "valueOf", "(C)L" + typeName + ";", false);
                break;
            case 'B':
                String typeName1 = Type.getInternalName(Byte.class);
                visitor.visitVarInsn(Opcodes.ILOAD, index);
                visitor.visitMethodInsn(Opcodes.INVOKESTATIC, typeName1, "valueOf", "(B)L" + typeName1 + ";", false);
                break;
            case 'S':
                String typeName2 = Type.getInternalName(Short.class);
                visitor.visitVarInsn(Opcodes.ILOAD, index);
                visitor.visitMethodInsn(Opcodes.INVOKESTATIC, typeName2, "valueOf", "(S)L" + typeName2 + ";", false);
                break;
            case 'I':
                String typeName3 = Type.getInternalName(Integer.class);
                visitor.visitVarInsn(Opcodes.ILOAD, index);
                visitor.visitMethodInsn(Opcodes.INVOKESTATIC, typeName3, "valueOf", "(I)L" + typeName3 + ";", false);
                break;
            case 'F':
                String typeName4 = Type.getInternalName(Float.class);
                visitor.visitVarInsn(Opcodes.FLOAD, index);
                visitor.visitMethodInsn(Opcodes.INVOKESTATIC, typeName4, "valueOf", "(F)L" + typeName4 + ";", false);
                break;
            case 'J':
                String typeName5 = Type.getInternalName(Long.class);
                visitor.visitVarInsn(Opcodes.LLOAD, index);
                visitor.visitMethodInsn(Opcodes.INVOKESTATIC, typeName5, "valueOf", "(J)L" + typeName5 + ";", false);
                break;
            case 'D':
                String typeName6 = Type.getInternalName(Double.class);
                visitor.visitVarInsn(Opcodes.DLOAD, index);
                visitor.visitMethodInsn(Opcodes.INVOKESTATIC, typeName6, "valueOf", "(D)L" + typeName6 + ";", false);
                break;
            default:
                visitor.visitVarInsn(Opcodes.ALOAD, index);
        }

    }
}
