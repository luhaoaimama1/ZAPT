package com.example.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by fuzhipeng on 2016/11/1.
 */

public class LogUtils {
    /**
     * 获取类名
     *
     * @param type
     * @param packageName
     * @return
     */
    public static String getClassName(TypeElement type, String packageName) {
        int packageLen = packageName.length() + 1;
        return type.getQualifiedName().toString().substring(packageLen).replace('.', '$');
    }

    /**
     * 获取某个类型的包名
     *
     * @param type
     * @return
     */
    public static String getPackageName(Elements elementUtils,Element type) {
        return elementUtils.getPackageOf(type).getQualifiedName().toString();
    }

    public static void writeLog(String str) {
        try {
            FileWriter fw = new FileWriter(new File("/Users/fuzhipeng/AndroidStudioProjects" +
                    "/AnnotationStudy/process.txt"), true);
            fw.write(str + "\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void writeLog2(String str, int count) {
        String fileAddress="/Users/fuzhipeng/AndroidStudioProjects/AnnotationStudy/apt/process.txt";
//        IOUtils.write(new File(fileAddress),str,"utf-8");
        try {
            FileWriter fw = new FileWriter(new File(fileAddress),count!=1);
//            FileWriter fw = new FileWriter(new File(fileAddress),true);
            fw.write("---------------count:"+count+"------------------\n");
            fw.write(str + "\n");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
