package com.example.buildsrc

import org.gradle.api.Plugin
import org.gradle.api.Project


public class AspectPlugin implements Plugin<Project>{


    @Override
    void apply(Project project) {
        println '==================================================================================='
        println "自定义插件"
        println '==================================================================================='
    }
}