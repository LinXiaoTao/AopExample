package com.example.buildsrc

import com.android.build.gradle.AppExtension
import com.example.buildsrc.monitor.Config
import org.gradle.api.Plugin
import org.gradle.api.Project

public class AspectPlugin implements Plugin<Project> {


    @Override
    void apply(Project project) {
        println '==================================================================================='
        println "自定义插件"
//        def currentName = ManagementFactory.getRuntimeMXBean().getName()
//        def pid = currentName.split("@")[0]
//        println "当前进程pid: " + pid
        project.extensions.create("config", Config)
        project.task('readConfig').doLast {
            def config = project['config']
            println "是否启用监视:" + config.enable
        }

        def android = project.extensions.getByType(AppExtension)
        android.registerTransform(new AspectTransform())
        println '==================================================================================='
    }
}