package com.ripple.permission.plugin;

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.ripple.permission.plugin.asm.ClassVisitorHelper
import com.ripple.permission.plugin.dsl.RipplePermissionPluginExtension
import groovy.io.FileType
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils

public class PermissionPlugin extends Transform {

    @Override
    String getName() {
        return "PermissionPlugin"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        transformInvocation.inputs.each {
            it.directoryInputs.each { DirectoryInput directoryInput ->
                File dest = transformInvocation.outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
                File dir = directoryInput.file
                if (dir) {
                    HashMap<String, File> modifyMap = new HashMap<>()
                    dir.traverse(type: FileType.FILES, nameFilter: ~/.*\.class/) {
                        File classFile ->
                            File file = modifyClassFile(dir, classFile, transformInvocation.getContext().temporaryDir)
                            if (file != null) {
                                modifyMap.put(classFile.absolutePath.replace(dir.absolutePath, ""), file)
                            }

                    }
                    FileUtils.copyDirectory(directoryInput.file, dest)

                    modifyMap.entrySet().each { Map.Entry<String, File> en ->
                        File target = new File(dest.absolutePath + en.getKey())
//                            Logger.info(target.getAbsolutePath())
                        if (target.exists()) {
                            target.delete()
                        }
                        FileUtils.copyFile(en.getValue(), target)
                        en.getValue().delete()
                    }


                }


            }

            it.jarInputs.each { JarInput jarInput ->
                String jarName = jarInput.name
                File outputFile = transformInvocation.getOutputProvider().getContentLocation(jarName,
                        jarInput.contentTypes, jarInput.scopes, Format.JAR)

                FileUtils.copyFile(jarInput.file, outputFile)
            }


        }
    }

    /**
     *
     * @param dir 包所在的目录
     * @param classFile class文件的全路径
     * @param tempDir 临时目录
     * @return 返回值代表修改后的文件，空值表示修改失败或者没有修改！
     */
    private File modifyClassFile(File dir, File classFile, File tempDir) {
        File modified = null
        FileOutputStream outputStream = null
        String className = path2ClassName(classFile.absolutePath, dir.absolutePath)
        try {
            byte[] sourceClassBytes = IOUtils.toByteArray(new FileInputStream(classFile))
            byte[] modifiedBytes = ClassVisitorHelper.modifyClass(className, sourceClassBytes)
            if (modifiedBytes) {
                modified = new File(tempDir, className.replace('.', '') + '.class')
                if (modified.exists())
                    modified.delete()
                modified.createNewFile()
                outputStream = new FileOutputStream(modified)
                outputStream.write(modifiedBytes)
            }
        } catch (Exception e) {
            e.printStackTrace()
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush()
                    outputStream.close()
                }
            } catch (Exception e1) {
                e1.printStackTrace()
            }
        }

        return modified

    }

    /**
     *
     * @param path class文件的全路径
     * @param dir class包的目录
     * @return
     */
    private String path2ClassName(String path, String dir) {
        return path
                .replace(dir + File.separator, "")
                .replace(File.separator, ".")
                .replace(".class", "")

    }


}
