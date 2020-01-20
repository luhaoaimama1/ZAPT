package com.zone.apt;

import com.zone.apt.entity.ClassEntity;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.tools.JavaFileObject;

/**
 * Created by fuzhipeng on 2016/11/3.
 */

public class JavaFileUtils {

    //因为生成的类不可以在重复写入 所以需要判断他是否有了 有就不写了
    private static List<String> generateCache = new ArrayList<>();

    public static void write(ElementResolver mElementCheckHelper, ClassEntity classEntity, Callback callback) throws IOException {
        String fullPath = classEntity.getClassPackage() + "." + classEntity.getAPTClassName();
        if (classEntity.getElement() != null && !generateCache.contains(fullPath)) {
            JavaFileObject jfo = mElementCheckHelper.getFiler()
                    //第一个参数是类名 需要带上包名 不然生成类 位置不对
                    .createSourceFile(fullPath,
                            classEntity.getElement()); //发现第二个参暂时有没有都一样
            Writer writer = jfo.openWriter();
            writer.write(callback.getContent());
            writer.flush();
            writer.close();
            generateCache.add(fullPath);
        }
    }

    public interface Callback {
        public String getContent();
    }
}
