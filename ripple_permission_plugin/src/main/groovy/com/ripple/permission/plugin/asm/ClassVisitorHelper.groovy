package com.ripple.permission.plugin.asm

import com.ripple.permission.plugin.AgencyHandleClassVisitor
import com.ripple.permission.plugin.dsl.RipplePermissionPluginExtension
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter;

/**
 * Author: fanyafeng
 * Data: 2020/4/15 16:05
 * Email: fanyafeng@live.cn
 * Description:
 */
class ClassVisitorHelper {
    static byte[] modifyClass(String className, byte[] srcByteCode) {
        byte[] classByteCode = null
        try {
            classByteCode = modifyClas(srcByteCode)
        } catch (Exception e) {
            e.printStackTrace()
        }

        if (classByteCode == null)
            classByteCode = srcByteCode

        return classByteCode
    }

    private static byte[] modifyClas(byte[] srcByteCode) throws IOException {
        ClassReader cr = new ClassReader(srcByteCode)
        ClassWriter classWriter = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS)
        ClassVisitor adapter = new AgencyHandleClassVisitor(classWriter)
        cr.accept(adapter, ClassReader.EXPAND_FRAMES)
        return classWriter.toByteArray()
    }
}
