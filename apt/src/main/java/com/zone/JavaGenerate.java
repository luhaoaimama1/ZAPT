package com.zone;

import com.zone.apt.entity.ClassEntity;
import com.zone.apt.entity.FieldEntity;
import com.zone.apt.entity.MethodEntity;

import java.util.Map;

/**
 * Created by fuzhipeng on 2016/11/2.
 */

public class JavaGenerate {

    public static String brewJava(ClassEntity classEntity) {
        StringBuilder sb = new StringBuilder();
        sb.append("package " + classEntity.getClassPackage() + ";\n");
        sb.append("import com.zone.ZBinder;\n");
        sb.append("import android.view.View;\n");
        sb.append("import androidx.annotation.UiThread;\n");
        sb.append("public class " + classEntity.getAPTClassName() + " implements ZBinder{ \n");
        sb.append("private " + classEntity.getClassName() + " target;\n");

        sb.append("@UiThread\n");
        sb.append("public " + classEntity.getAPTClassName() + "(" + classEntity.getClassName() + " target){\n");
        sb.append(" this.target=target;\n");
        sb.append(" }\n");

        sb.append(" @Override public void bind() {\n");
        for (Map.Entry<String, FieldEntity> itemField : classEntity.getFields().entrySet()) {
            FieldEntity field = itemField.getValue();
            sb.append("target." + field.getName() +
                    " =  (" + field.getType() + ")target.findViewById(" +
                    ((ZField) field.getAnnotataionMap().get(ZField.class)).value() + ");\n");
        }
        for (Map.Entry<String, MethodEntity> itemMethod : classEntity.getMethods().entrySet()) {
            MethodEntity method = itemMethod.getValue();
            int[] ids = ((ZMethod) method.getAnnotataionMap().get(ZMethod.class)).value();
            for (int id : ids) {
                sb.append("target.findViewById(" + id + ").setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "                    //target.onclick(v);\n" +
                        "            }\n" +
                        "        });\n");
            }
        }
        sb.append("}");
        sb.append(" @Override public void unbind() {\n");
        for (Map.Entry<String, FieldEntity> itemField : classEntity.getFields().entrySet()) {
            FieldEntity field = itemField.getValue();
            sb.append("target." + field.getName() + " =  null;\n");
        }
        sb.append(" }\n");
        sb.append(" }\n");
        return sb.toString();
    }
}
