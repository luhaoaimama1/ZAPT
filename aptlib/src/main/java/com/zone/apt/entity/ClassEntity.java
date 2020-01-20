package com.zone.apt.entity;

import java.lang.annotation.Annotation;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * Created by fuzhipeng on 2016/11/1.
 */

public class ClassEntity {
    private String APTClassName;
    private Name classSimpleName;
    private Map<Class<? extends Annotation>, Annotation> annotataionMap = new HashMap<>();
    private String classPackage;
    private Set<Modifier> modifierSet;
    private String className;
    //判断是否Null如果不是则有父类
    private String superclass;
    //没有接口 则长度为0；
    private List<String> interfaces = new ArrayList<>();
    private Map<String, FieldEntity> fields = new HashMap<>();
    private Map<String, MethodEntity> methods = new HashMap<>();
    private WeakReference<TypeElement> elementWeakCache;

    @Override
    public String toString() {
        StringBuilder fieldString = new StringBuilder();
        StringBuilder methodString = new StringBuilder();
        StringBuilder interfacesString = new StringBuilder();
        StringBuilder annotataionMapString = new StringBuilder();
        for (Map.Entry<String, FieldEntity> item : fields.entrySet()) {
            fieldString.append("FieldKey:" + item.getKey() +
                    "\tFieldValue:\n" + item.getValue().toString() + "\n");
        }
        for (Map.Entry<String, MethodEntity> item : methods.entrySet()) {
            methodString.append("Methodkey:" + item.getKey() +
                    "\tMethodValue:\n" + item.getValue().toString() + "\n");
        }
        for (Map.Entry<Class<? extends Annotation>, Annotation> item : annotataionMap.entrySet()) {
            annotataionMapString.append("AnnotationKey:" + item.getKey().getCanonicalName() +
                    "\tAnnotationValue:" + item.getValue().toString() + "\n");
        }
        for (int i = 0; i < interfaces.size(); i++) {
            interfacesString.append("interfaces__index:" + i + ":" + interfaces.get(i));
        }
        StringBuilder result = new StringBuilder();
        result.append("{\n" +
                "classPackage:" + classPackage + "\n" +
                "modifierSet:" + modifierSet + "\n" +
                "className:" + className + "\n" +
                "classSimpleName:" + classSimpleName + "\n" +
                "superclass:" + superclass + "\n" +
                fieldString.toString() + "\n" +
                methodString.toString() + "\n" +
                annotataionMapString.toString() + "\n" +
                interfacesString.toString() + "\n" +
                "}");
        return result.toString();
    }

    public ClassEntity(Elements elementUtils, Types typeUtils, TypeElement element) {
        elementWeakCache = new WeakReference<TypeElement>(element);
        this.classPackage = elementUtils.getPackageOf(element).getQualifiedName().toString();
        this.modifierSet = element.getModifiers();
        this.className = element.toString();
        this.classSimpleName = element.getSimpleName();
        if ("java.lang.Object".equals(element.getSuperclass().toString()))
            this.superclass = null;
        else
            this.superclass = element.getSuperclass().toString();
        List<? extends TypeMirror> interfaces = element.getInterfaces();

        for (TypeMirror anInterface : interfaces)
            this.interfaces.add(typeUtils.asElement(anInterface).toString());
    }

    public String getClassPackage() {
        return classPackage;
    }

    public void setClassPackage(String classPackage) {
        this.classPackage = classPackage;
    }

    public Set<Modifier> getModifierSet() {
        return modifierSet;
    }

    public void setModifierSet(Set<Modifier> modifierSet) {
        this.modifierSet = modifierSet;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSuperclass() {
        return superclass;
    }

    public void setSuperclass(String superclass) {
        this.superclass = superclass;
    }

    public List<String> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(List<String> interfaces) {
        this.interfaces = interfaces;
    }

    public Map<String, FieldEntity> getFields() {
        return fields;
    }

    public void setFields(Map<String, FieldEntity> fields) {
        this.fields = fields;
    }

    public Map<String, MethodEntity> getMethods() {
        return methods;
    }

    public void setMethods(Map<String, MethodEntity> methods) {
        this.methods = methods;
    }

    public Map<Class<? extends Annotation>, Annotation> getAnnotataionMap() {
        return annotataionMap;
    }

    public void setAnnotataionMap(Map<Class<? extends Annotation>, Annotation> annotataionMap) {
        this.annotataionMap = annotataionMap;
    }

    public TypeElement getElement() {
        return elementWeakCache.get();
    }

    public Name getClassSimpleName() {
        return classSimpleName;
    }

    public String getAPTClassName() {
        return APTClassName;
    }

    public void setAPTClassName(String APTClassName) {
        this.APTClassName = APTClassName;
    }
}
