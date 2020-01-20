package com.zone

import com.zone.apt.JavaFileUtils
import com.zone.apt.LogConfig
import com.zone.apt.entity.ClassEntity
import com.google.auto.service.AutoService
import com.zone.apt.AbstractProcessorAPT
import com.zone.apt.ElementResolver

import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedAnnotationTypes
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

@AutoService(Processor::class)
class ViewInjectProcessor : AbstractProcessorAPT() {
    companion object {
        internal val SUFFIX = ElementResolver.GENERATE_LABEL + "Injector"

        init {
            LogConfig.setFileAddress("/Users/nutstore/AndroidStudioProjects/AnnotationStudy/apt/src/main/java/com/zone/process.txt")
        }
    }

    override fun getSupportedAnnotationClasses(): Array<Class<out Annotation>> =
            arrayOf(ZField::class.java, ZMethod::class.java, ZClass::class.java)

    override fun process(annotations: Set<TypeElement>, env: RoundEnvironment): Boolean {

        mElementResolver.messager.printMessage(Diagnostic.Kind.WARNING, "写入成功 ：")
        //解析成实体类
        for (support in supportedAnnotationClasses) {
            mElementResolver.resolve(env, support)
        }

        //文件写入
        for ((_, value) in mElementResolver.classEntityMap) {
            try {
                value.aptClassName = value.classSimpleName.toString() + SUFFIX
                JavaFileUtils.write(mElementResolver, value) { JavaGenerate.brewJava(value) }
            } catch (e: Exception) {
                mElementResolver.messager.printMessage(Diagnostic.Kind.ERROR, e.message)
            }

        }
        //log打印；
        mElementResolver.printLog()
        return true
    }
}