package com.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

/**
 * Created by fuzhipeng on 2016/11/1.
 */

public class LogConfig {
    //这个是mac地址 如果地址不一样你的需要改这个
    private static String fileAddress="/Users/fuzhipeng/AndroidStudioProjects" +
            "/AnnotationStudy/apt/process.txt";
    protected static void writeLog(Messager messager, String str, int count) {
        if("".equals(fileAddress)||fileAddress==null)
            messager.printMessage(Diagnostic.Kind.ERROR,"请配置log地址");
        try {
            FileWriter fw = new FileWriter(new File(fileAddress),count!=1);
//            FileWriter fw = new FileWriter(new File(fileAddress),true);
            fw.write("---------------process count:"+count+"------------------\n");
            fw.write(str + "\n");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getFileAddress() {
        return fileAddress;
    }

    public static void setFileAddress(String fileAddress) {
        LogConfig.fileAddress = fileAddress;
    }
}
