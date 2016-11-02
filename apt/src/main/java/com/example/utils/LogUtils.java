package com.example.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by fuzhipeng on 2016/11/1.
 */

public class LogUtils {
    //这个是mac地址 如果地址不一样你的需要改这个
    public static String fileAddress="/Users/fuzhipeng/AndroidStudioProjects" +
            "/AnnotationStudy/apt/process.txt";
    public static void writeLog(String str, int count) {
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
