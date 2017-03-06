package com.example.buildsrc

import com.sun.tools.attach.VirtualMachine
import org.gradle.api.Plugin
import org.gradle.api.Project

import java.lang.management.ManagementFactory

public class AspectPlugin implements Plugin<Project>{


    @Override
    void apply(Project project) {
        println '==================================================================================='
        println "自定义插件"
        def currentName = ManagementFactory.getRuntimeMXBean().getName()
        def pid = currentName.split("@")[0]
        println "当前进程pid: " + pid
        VirtualMachine virtualMachine = VirtualMachine.attach(pid)
        virtualMachine.loadAgent("/Users/linxiaotao/Documents/work/Personal/AopExample/buildSrc/agent.jar")
        virtualMachine.detach()
        println '==================================================================================='
    }
}