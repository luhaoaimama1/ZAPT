package com.zone.apt;

import com.zone.apt.entity.ClassEntity;
import com.zone.apt.entity.FieldEntity;
import com.zone.apt.entity.MethodEntity;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * Created by fuzhipeng on 2016/11/2.
 */

public class ElementResolver {
    private ProcessingEnvironment env;
    private Filer filer;
    private Elements elementUtils;
    private Types typeUtils;
    private Messager messager;
    private Map<String, ClassEntity> classEntityMap;
    public static final String GENERATE_LABEL = "_";


    public ElementResolver(ProcessingEnvironment env) {
        this.env = env;
        filer = env.getFiler();
        elementUtils = env.getElementUtils();
        typeUtils = env.getTypeUtils();
        messager = env.getMessager();
        classEntityMap = new HashMap<>();
    }

    public String getSettingKey(String className,String annotationName){
        return className;
//        return className + " " + annotationName;
    }
    //refot动态代理。。。
    //并且类带$$ 不解析；
    public void resolve(RoundEnvironment env, Class<? extends Annotation> support) {
        for (Element element : env.getElementsAnnotatedWith(support)) {

            if (element.getKind() == ElementKind.CLASS) {
                //类名是否 带$$  带则不解析；
                ClassEntity classEntity = new ClassEntity(elementUtils, typeUtils,
                        (TypeElement) element);
                if (checkClassNeedResolve(classEntity.getClassName())) {
                    String key = getSettingKey(classEntity.getClassName(), support.getCanonicalName());
                    if (classEntityMap.get(key) == null)
                        classEntityMap.put(key,new ClassEntity(elementUtils, typeUtils, (TypeElement) element));
                    ClassEntity classEntityTemp = classEntityMap.get(key);
                    classEntityTemp.getAnnotataionMap().put(support, element.getAnnotation(support));
                } else break;
            }

            if (element.getKind() == ElementKind.FIELD) {
                //类名是否 带$$  带则不解析；
                FieldEntity fieldEntity = new FieldEntity((VariableElement) element);
                if (checkClassNeedResolve(fieldEntity.getClassName())) {
                    String key = getSettingKey(fieldEntity.getClassName(), support.getCanonicalName());
                    if (classEntityMap.get(key) == null)
                        classEntityMap.put(key,
                                new ClassEntity(elementUtils, typeUtils,
                                        (TypeElement) element.getEnclosingElement()));
                    ClassEntity classEntityTemp = classEntityMap.get(key);
                    FieldEntity fieldCache = classEntityTemp.getFields().get(fieldEntity.getName());
                    if (fieldCache == null)
                        fieldCache = fieldEntity;
                    fieldCache.getAnnotataionMap().put(support,element.getAnnotation(support));
                    classEntityTemp.getFields().put(fieldCache.getName(), fieldCache);
                } else break;
            }
            if (element.getKind() == ElementKind.METHOD) {
                //类名是否 带$$  带则不解析；
                MethodEntity methodEntity = new MethodEntity((ExecutableElement) element);
                if (checkClassNeedResolve(methodEntity.getClassName())) {
                    String key = getSettingKey(methodEntity.getClassName(), support.getCanonicalName());
                    if (classEntityMap.get(key) == null)
                        classEntityMap.put(key,new ClassEntity(elementUtils, typeUtils,
                                (TypeElement) element.getEnclosingElement()));
                    ClassEntity classEntityTemp = classEntityMap.get(key);
                    MethodEntity methodCache = classEntityTemp.getMethods().get(methodEntity.getMethodName());
                    if (methodCache == null)
                        methodCache = methodEntity;
                    methodCache.getAnnotataionMap().put(support,element.getAnnotation(support));
                    classEntityTemp.getMethods().put(methodCache.getMethodName(), methodCache);
                } else break;
            }
        }
    }

    public int count = 1;
    public void  printLog(){
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, ClassEntity> classEntity : this.getClassEntityMap().entrySet()) {
            sb.append("-------------类：" + classEntity.getKey() + "----------------\n");
            sb.append(classEntity.getValue() + "\n");
            sb.append("\n");
        }
        LogConfig.writeLog(messager,sb.toString(), count);
        count++;
    }

    private boolean checkClassNeedResolve(String className) {
        return !className.contains(GENERATE_LABEL);
    }

    public ProcessingEnvironment getEnv() {
        return env;
    }

    public void setEnv(ProcessingEnvironment env) {
        this.env = env;
    }

    public Filer getFiler() {
        return filer;
    }

    public void setFiler(Filer filer) {
        this.filer = filer;
    }

    public Elements getElementUtils() {
        return elementUtils;
    }

    public void setElementUtils(Elements elementUtils) {
        this.elementUtils = elementUtils;
    }

    public Types getTypeUtils() {
        return typeUtils;
    }

    public void setTypeUtils(Types typeUtils) {
        this.typeUtils = typeUtils;
    }

    public Messager getMessager() {
        return messager;
    }

    public void setMessager(Messager messager) {
        this.messager = messager;
    }

    public Map<String, ClassEntity> getClassEntityMap() {
        return classEntityMap;
    }

    public void setClassEntityMap(Map<String, ClassEntity> classEntityMap) {
        this.classEntityMap = classEntityMap;
    }
}
