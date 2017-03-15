package com.example.buildsrc

import com.example.monitor.Monitor
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type

/**
 * Created on 2017/3/14 下午7:39.
 * leo linxiaotao1993@vip.qq.com
 */

public class MonitorClassVisitor extends ClassVisitor {

    private boolean mIsVisit = false

    public MonitorClassVisitor(int api) {
        super(api)
    }

    public MonitorClassVisitor(int var1, ClassVisitor var2) {
        super(var1, var2)
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        mIsVisit = Type.getDescriptor(Monitor.class).equals(desc)
        return super.visitAnnotation(desc, visible)
    }

    @Override
    MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions)
//        println "methodVisitor " + (methodVisitor == null)
        if (mIsVisit) {
            return new MonitorMethodVisitor(Opcodes.ASM5, methodVisitor, access, name, desc)
        }
        return methodVisitor
    }
}
