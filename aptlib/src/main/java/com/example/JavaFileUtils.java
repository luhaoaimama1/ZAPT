package com.example;

import com.example.entity.ClassEntity;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

/**
 * Created by fuzhipeng on 2016/11/3.
 */

public class JavaFileUtils {

    //因为生成的类不可以在重复写入 所以需要判断他是否有了 有就不写了
    private static List<String> generateCache=new ArrayList<>();

    public static void write (ElementResolver mElementCheckHelper, ClassEntity classEntity,
                              String suffix, String content) throws IOException {

        String generateValue = classEntity.getClassName() + suffix;
        TypeElement classType = classEntity.getElement();
        if (classType!=null&&!generateCache.contains(generateValue)) {
            JavaFileObject jfo = mElementCheckHelper.getFiler()
                    .createSourceFile(generateValue,//第一个参数是类名；
                    classEntity.getElement()); //发现第二个参暂时有没有都一样
            Writer writer = jfo.openWriter();
            writer.write(content);
            writer.flush();
            writer.close();
            generateCache.add(generateValue);
        }
    }
}
