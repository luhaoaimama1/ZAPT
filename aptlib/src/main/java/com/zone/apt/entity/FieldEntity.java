//package com.zone.apt.entity;
//
//import java.lang.annotation.Annotation;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import javax.lang.model.element.Modifier;
//import javax.lang.model.element.VariableElement;
//import javax.lang.model.util.Elements;
//import javax.lang.model.util.Types;
//
///**
// * Created by fuzhipeng on 2016/11/1.
// */
//
//public class FieldEntity {
//    private Map<Class<? extends Annotation>,Annotation> annotataionMap= new HashMap<>();
//    private Set<Modifier> modifierSet;
//    private  String type;
//    private  String name;
//    //所在的类
//    private  String className;
//
//    @Override
//    public String toString() {
//        StringBuilder annotataionMapString=new StringBuilder();
//        for (Map.Entry<Class<? extends Annotation>,Annotation> item : annotataionMap.entrySet()) {
//            annotataionMapString.append("Annotationkey:"+ item.getKey().getCanonicalName()+
//                    "\t Annotation:"+ item.getValue());
//        }
//        StringBuilder result=new StringBuilder();
//        result.append("\t{modifierSet:"+modifierSet+"\n"+
//                "\ttype:"+type+"\n"+
//                "\tname:"+name+"\n"+
//                "\tclassName:"+className+"\n"+
//                "\t"+ annotataionMapString.toString()+"\n"+"}");
//        return result.toString();
//    }
//
//    public FieldEntity(VariableElement element) {
//        this.modifierSet = element.getModifiers();
//        this.type = element.asType().toString();
//        this.name = element.toString();
//        this.className = element.getEnclosingElement().toString();
//    }
//
//    public Set<Modifier> getModifierSet() {
//        return modifierSet;
//    }
//
//    public void setModifierSet(Set<Modifier> modifierSet) {
//        this.modifierSet = modifierSet;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getClassName() {
//        return className;
//    }
//
//    public void setClassName(String className) {
//        this.className = className;
//    }
//
//    public Map<Class<? extends Annotation>, Annotation> getAnnotataionMap() {
//        return annotataionMap;
//    }
//
//    public void setAnnotataionMap(Map<Class<? extends Annotation>, Annotation> annotataionMap) {
//        this.annotataionMap = annotataionMap;
//    }
//}
