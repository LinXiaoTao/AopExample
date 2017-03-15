package com.example.buildsrc

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
/**
 * Created on 2017/3/14 下午4:16.
 * leo linxiaotao1993@vip.qq.com
 */

public class AspectTransform extends Transform {


    AspectTransform() {
    }

    @Override
    public String getName() {
        return "Aspect";
    }

    //输入文件类型
    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        //class
        return TransformManager.CONTENT_CLASS;
    }

    //转换作用范围
    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        //整个项目
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    public boolean isIncremental() {
        return false;
    }

    @Override
    void transform(TransformInvocation transformInvocation)
            throws TransformException, InterruptedException, IOException {
        transformInvocation.inputs.each {
            input ->
                //输出目录复制
                input.directoryInputs.each {
                    directory ->
                        //获取输出路径文件夹
                        def dest = transformInvocation.outputProvider.getContentLocation(directory.name,
                                directory.contentTypes, directory.scopes, Format.DIRECTORY)
//                        println "输出文件夹:" + dest.absolutePath + " 是否存在:" + dest.exists()

                        //删除输出文件夹内容，如果不为空
                        if (dest.exists())
                            FileUtils.deleteDirectoryContents(dest)

                        timeTransform(directory.file)

                        //将输入目录复制到输出目录
                        FileUtils.copyDirectory(directory.file, dest)
//                        println "输出文件夹:" + dest.absolutePath + " 是否存在:" + dest.exists()
                }

                //jar复制一般为第三方jar
                input.jarInputs.each {
                    jar ->
                        def jarName = jar.name
                        if (jarName.endsWith('.jar'))
                            jarName = jarName.substring(0,jarName.length() - 4)
//                        println "jar输入目录:" + jar.file.absolutePath

                        //jar输出目录
                        def dest = transformInvocation.outputProvider.getContentLocation(jarName, jar.contentTypes
                                , jar.scopes, Format.JAR)
//                        println "jar输出目录:" + dest.absolutePath

                        //删除输出jar，如果存在
                        FileUtils.deleteIfExists(dest)

//                        if (dest.exists())
//                            println dest.absolutePath + "还存在"

                        FileUtils.createFile(dest, "")
                        FileUtils.copyFile(jar.file, dest)
                }

        }
        super.transform(transformInvocation)
    }

    private void timeTransform(File input) {
        if (input.directory) {
            input.listFiles().each {
                dir ->
                    timeTransform(dir)
            }
        } else {
            transformFile(input)
        }

    }

    private void transformFile(File file) {
        if (file.absolutePath.contains("R.class") || file.absolutePath.contains('BuildConfig.class')
                || file.absolutePath.contains("R\$"))
            return
        println "输入文件路径:" + file.absolutePath
        ClassReader classReader = new ClassReader(new FileInputStream(file))
        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
        MonitorClassVisitor monitorClassVisitor = new MonitorClassVisitor(Opcodes.ASM5, classWriter)
        classReader.accept(monitorClassVisitor, ClassReader.EXPAND_FRAMES)
        FileOutputStream fileOutputStream = new FileOutputStream(file)
        fileOutputStream.write(classWriter.toByteArray())
    }

}
