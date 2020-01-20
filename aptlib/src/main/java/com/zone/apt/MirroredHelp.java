package com.zone.apt;

import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;

/**
 * MIT License
 * Copyright (c) [2018] [Zone]
 * 针对异常javax.lang.model.type.MirroredTypeException: Attempt to access Class object for TypeMirror j
 * TypeMirror，它表示我们未编译类
 * 我们可以直接强制转换为DeclaredType，然后读取TypeElement来获取合法的名字。
 * 采取办法： https://race604.com/annotation-processing/
 */
public abstract class MirroredHelp {

    /**
     * ele.toString()==clas.a.getCanonicalName()
     * 例如：zone.com.sdk.API.gank.api.GankImpl
     * ele.getSimpleName()
     * 例如：GankImpl
     * @return   element
     */
    public Element getExcepetElement() {
        DeclaredType value = null;
        Element ele = null;
        try {
            excepetMothod();
        } catch (MirroredTypeException mte) {
            value = (DeclaredType) mte.getTypeMirror();
            ele = value.asElement();
        }
        return ele;
    }

    public  abstract void excepetMothod();
}
