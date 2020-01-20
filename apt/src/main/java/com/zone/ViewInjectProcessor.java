package com.zone;
import com.zone.apt.JavaFileUtils;
import com.zone.apt.LogConfig;
import com.zone.apt.entity.ClassEntity;
import com.google.auto.service.AutoService;
import com.zone.apt.AbstractProcessorAPT;
import com.zone.apt.ElementResolver;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class ViewInjectProcessor extends AbstractProcessorAPT {
    public static final String SUFFIX = ElementResolver.GENERATE_LABEL +"Injector";
//    public static final String GENERATE_LABEL =  ElementResolver.GENERATE_LABEL+"ViewBinder";
    private Class<? extends Annotation>[] supports = new Class[]{
            ZField.class,
            ZMethod.class,
            ZClass.class
    };

    static {
        LogConfig.setFileAddress("/Users/nutstore/AndroidStudioProjects/AnnotationStudy/apt/src/main/java/com/zone/process.txt");
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment env) {

        mElementResolver.getMessager()
                .printMessage(Diagnostic.Kind.WARNING, "  写 入  成 功  2：" );
        //解析成实体类
        for (Class<? extends Annotation> support : supports){
            mElementResolver.resolve(env, support);
        }

        //文件写入
        for (Map.Entry<String, ClassEntity> item : mElementResolver.getClassEntityMap().entrySet()) {
            try {
                final ClassEntity value = item.getValue();
                value.setAPTClassName(value.getClassSimpleName() + SUFFIX);
                JavaFileUtils.write(mElementResolver, value, new JavaFileUtils.Callback() {
                    @Override
                    public String getContent() {
                        return JavaGenerate.brewJava(value);
                    }
                });
            } catch (Exception e) {
                mElementResolver.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
            }
        }
        //log打印；
        mElementResolver.printLog();
        return true;
    }


    @Override
    public Class<? extends Annotation>[] getSupportedAnnotationClasses() {
        return supports;
    }
}