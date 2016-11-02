package com.example;

/**
 * Created by fuzhipeng on 2016/11/2.
 */
//测试用
public interface ZBinder<T> {
    void bind(T target);
    void unbind(T target);
}
