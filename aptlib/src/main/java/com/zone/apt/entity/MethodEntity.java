package com.zone.entity;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;

/**
 * Created by fuzhipeng on 2016/11/1.
 */

public class MethodEntity {
    private Map<Class<? extends Annotation>,Annotation> annotataionMap= new HashMap<>();
    private Set<Modifier> modifierSet;
    private  String retunType;
    private  String methodName;
    //不是map因为这个是有序的
    private List<String> argTypes=new ArrayList<>();
    private List<String> argName=new ArrayList<>();
    //所在的类
    private  String className;

    @Override
    public String toString() {
        StringBuilder annotataionMapString=new StringBuilder();
        for (Map.Entry<Class<? extends Annotation>,Annotation> item : annotataionMap.entrySet()) {
            annotataionMapString.append("\tAnnotationkey:"+ item.getKey().getCanonicalName()+
                    "\tAnnotationValue:"+ item.getValue()+"\n");
        }
        StringBuilder argStr =new StringBuilder();
        for (int i = 0; i < argTypes.size(); i++)
            argStr.append("\targ:__index:"+i+",type:"+argTypes.get(i)+",name:"+argName.get(i)+"\n")  ;
        StringBuilder result=new StringBuilder();
        result.append("\t{modifierSet:"+modifierSet+"\n"+
                "\tretunType:"+retunType+"\n"+
                "\tmethodName:"+methodName+"\n"+
                "\tclassName:"+className+"\n"+
                annotataionMapString.toString()+
                argStr.toString()+"}");
        return result.toString();
    }

    public MethodEntity(ExecutableElement element) {
        this.className = element.getEnclosingElement().toString();
        this.modifierSet = element.getModifiers();
        this.retunType = element.getReturnType().toString();
        this.methodName = element.getSimpleName().toString();
        List<? extends VariableElement> pars = element.getParameters();
        for (VariableElement par : pars){
            argTypes.add(par.asType().toString());
            argName.add(par.toString());
        }
    }

    public Set<Modifier> getModifierSet() {
        return modifierSet;
    }

    public void setModifierSet(Set<Modifier> modifierSet) {
        this.modifierSet = modifierSet;
    }

    public String getRetunType() {
        return retunType;
    }

    public void setRetunType(String retunType) {
        this.retunType = retunType;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public List<String> getArgTypes() {
        return argTypes;
    }

    public void setArgTypes(List<String> argTypes) {
        this.argTypes = argTypes;
    }

    public List<String> getArgName() {
        return argName;
    }

    public void setArgName(List<String> argName) {
        this.argName = argName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Map<Class<? extends Annotation>, Annotation> getAnnotataionMap() {
        return annotataionMap;
    }

    public void setAnnotataionMap(Map<Class<? extends Annotation>, Annotation> annotataionMap) {
        this.annotataionMap = annotataionMap;
    }
}
