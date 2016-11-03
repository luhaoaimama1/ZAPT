package com.example;
import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.SourceVersion;
/**
 * Created by fuzhipeng on 2016/11/3.
 */
public abstract class AbstractProcessorAPT extends AbstractProcessor {
    protected ElementResolver mElementResolver;
    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);
        mElementResolver = new ElementResolver(env);
    }
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        for (Class support : getSupportedAnnotationClasses())
            types.add(support.getCanonicalName());
        return types;
    }
    public abstract Class<? extends Annotation>[] getSupportedAnnotationClasses();


    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

}
