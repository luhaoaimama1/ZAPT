package com.example;

import com.example.entity.ClassEntity;
import com.example.entity.helper.ElementCheckHelper;
import com.example.utils.LogUtils;
import com.google.auto.service.AutoService;

import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
public class ViewInjectProcessor extends AbstractProcessor {
    public static final String SUFFIX = "$$Injector";
//    public static final String SUFFIX = "$$ViewBinder";
    public int count = 1;
    private Class<? extends Annotation>[] supports = new Class[]{
            ZField.class,
            ZMethod.class,
            ZClass.class
    };
    private ElementCheckHelper mElementCheckHelper;
    private List<String> generateCache=new ArrayList<>();

    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);
        mElementCheckHelper = new ElementCheckHelper(env);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment env) {
        StringBuilder sb = new StringBuilder();

        for (Class<? extends Annotation> support : supports)
            mElementCheckHelper.resolve(env, support);
        for (Map.Entry<String, ClassEntity> classEntity : mElementCheckHelper.getClassEntityMap().entrySet()) {
            sb.append("-------------类：" + classEntity.getKey() + "----------------\n");
            sb.append(classEntity.getValue() + "\n");
            sb.append("\n");
            try {
                TypeElement classType = classEntity.getValue().getElement();
                //代表最后生成的类名
                String generateValue = classEntity.getValue().getClassName()
                        + SUFFIX;

                if (classType!=null&&!generateCache.contains(generateValue)) {
                    JavaFileObject jfo = mElementCheckHelper.getFiler()
                            //第一个参数是类名；
                            .createSourceFile(generateValue,
//                                   );
                                    //发现第二个参暂时有没有都一样
                             classEntity.getValue().getElement());
                    Writer writer = jfo.openWriter();
                    writer.write(JavaGenerate.brewJava(classEntity.getValue()));
                    writer.flush();
                    writer.close();
                    generateCache.add(generateValue);
                }
            } catch (Exception e) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
            }
        }
        LogUtils.writeLog(sb.toString(), count);
        count++;
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        for (Class support : supports)
            types.add(support.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


}