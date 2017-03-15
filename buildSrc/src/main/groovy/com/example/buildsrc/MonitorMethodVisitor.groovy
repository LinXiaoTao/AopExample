package com.example.buildsrc

import com.example.monitor.Monitor

//import com.example.monitor.Monitor
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Type
import org.objectweb.asm.commons.AdviceAdapter

/**
 * Created on 2017/3/14 下午7:49.
 * leo linxiaotao1993@vip.qq.com
 */

public class MonitorMethodVisitor extends AdviceAdapter {

    private boolean mIsVisit = false

    /**
     * Creates a new {@link AdviceAdapter}.
     *
     * @param api
     *            the ASM API version implemented by this visitor. Must be one
     *            of {@link Opcodes#ASM4} or {@link Opcodes#ASM5}.
     * @param mv
     *            the method visitor to which this adapter delegates calls.
     * @param access
     *            the method's access flags (see {@link Opcodes}).
     * @param name
     *            the method's name.
     * @param desc
     *            the method's descriptor (see {@link Type Type}).
     */
    public MonitorMethodVisitor(int api, MethodVisitor mv, int access, String name, String desc) {
        super(api, mv, access, name, desc)
    }

    //com.example.monitor
    @Override
    protected void onMethodEnter() {
        super.onMethodEnter()
        if (mIsVisit) {
            println 'onMethodEnter'
            visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false)
            visitMethodInsn(INVOKESTATIC, "com/example/monitor/TimeMonitor", "start", "(J)V", false)
        }
    }

    @Override
    protected void onMethodExit(int i) {
        super.onMethodExit(i)
        if (mIsVisit) {
            println 'onMethodExit'
            visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false)
            visitMethodInsn(INVOKESTATIC, "com/example/monitor/TimeMonitor", "calculation", "(J)V", false)
        }
    }

    @Override
    AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        mIsVisit = Type.getDescriptor(Monitor.class).equals(desc)
        return super.visitAnnotation(desc, visible)
    }
}
