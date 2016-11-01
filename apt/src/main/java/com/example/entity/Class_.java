package com.example.entity;

import com.example.utils.GsonUtils;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by fuzhipeng on 2016/11/1.
 */

public class Class_ {
    private final String classPackage;
    private final String className;
    private final String targetClass;
    private final boolean isInterface;
    private final Set<Method_> methods;
    private final Set<Field_> fields;

    public Class_(String classPackage, String className, String targetClass, boolean isInterface) {
        this.classPackage = classPackage;
        this.className = className;
        this.targetClass = targetClass;
        this.isInterface = isInterface;
        this.methods = new LinkedHashSet<>();
        this.fields = new LinkedHashSet<>();
    }

    @Override
    public String toString() {
        return GsonUtils.toJson(this);
    }
}
