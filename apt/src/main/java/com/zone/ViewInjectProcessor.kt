package com.zone

import com.zone.apt.JavaFileUtils
import com.zone.apt.LogConfig
import com.google.auto.service.AutoService
import com.zone.apt.AbstractProcessorAPT
import com.zone.apt.ElementResolver

import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

@AutoService(Processor::class)
class ViewInjectProcessor : AbstractProcessorAPT() {
    companion object {
        internal val SUFFIX = "AutoBundle" + ElementResolver.GENERATE_LABEL
        internal val HELPER_PACKAGE_PATH = "com.zone.argument"
        internal val BUNDLE_HELPER_NAME = "BundleHelper"


        init {
            LogConfig.setFileAddress("/Users/fuzhipeng/AndroidStudioProjects/ZAPT/apt/src/main/java/com/zone/process.txt")
        }
    }

    override fun getSupportedAnnotationClasses(): Array<Class<out Annotation>> =
            arrayOf(AutoBundle::class.java)

    override fun process(annotations: Set<TypeElement>, env: RoundEnvironment): Boolean {

        mElementResolver.messager.printMessage(Diagnostic.Kind.WARNING, "写入成功 ：")
        //解析成实体类
        for (support in supportedAnnotationClasses) {
            mElementResolver.resolve(env, support)
        }

        //文件写入
        for ((_, value) in mElementResolver.classEntityMap) {
            try {
                //初始化帮助类
                JavaFileUtils.write("$HELPER_PACKAGE_PATH.$BUNDLE_HELPER_NAME", mElementResolver, value) { JavaGenerate.brewHelperJava() }

                //
                value.aptClassName = value.classSimpleName.toString() + SUFFIX
                val write = JavaFileUtils.write(mElementResolver, value) { JavaGenerate.brewJava(value) }
            } catch (e: Exception) {
                mElementResolver.messager.printMessage(Diagnostic.Kind.ERROR, e.message)
            }
        }
        //log打印；
        mElementResolver.printLog()
        return true
    }
}