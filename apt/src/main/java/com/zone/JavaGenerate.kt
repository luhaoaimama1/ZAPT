package com.zone

import com.zone.apt.entity.ClassEntity

/**
 * Created by fuzhipeng on 2016/11/2.
 */

object JavaGenerate {

    fun brewJava(classEntity: ClassEntity): String {
        val sb = StringBuilder()
        sb.append("package ${classEntity.classPackage};\n")
        sb.append("import com.zone.ZBinder;\n")
        sb.append("import android.view.View;\n")
        sb.append("import androidx.annotation.UiThread;\n")
        sb.append("public class ${classEntity.aptClassName} implements ZBinder{ \n")
        sb.append("private  ${classEntity.className}  target;\n")

        sb.append("@UiThread\n")
        sb.append("public ${classEntity.aptClassName} (${classEntity.className}  target){\n")
        sb.append("this.target=target;\n")
        sb.append("}\n")

        sb.append("@Override public void bind() {\n")
        for ((_, field) in classEntity.fields) {
            val viewId = (field.annotataionMap[ZField::class.java] as ZField).value
            sb.append("target.${field.name} = (${field.type})target.findViewById($viewId);\n")
        }
        for ((_, method) in classEntity.methods) {
            val ids = (method.annotataionMap[ZMethod::class.java] as ZMethod).value
            for (viewId in ids) {
                sb.append("target.findViewById($viewId).setOnClickListener(new View.OnClickListener() {\n")
                sb.append("   @Override\n")
                sb.append(" public void onClick(View v) {\n")
                sb.append("             target.onClick($viewId);\n")
                sb.append("     }\n")
                sb.append("  });\n")
            }
        }
        sb.append("}")
        sb.append(" @Override public void unbind() {\n")
        for ((_, field) in classEntity.fields) {
            sb.append("target.${field.name} =  null;\n")
        }
        sb.append(" }\n")
        sb.append(" }\n")
        return sb.toString()
    }
}
