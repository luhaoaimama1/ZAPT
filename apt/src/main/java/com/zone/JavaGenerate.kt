package com.zone

import com.zone.apt.entity.ClassEntity
import com.zone.apt.entity.FieldEntity

/**
 * Created by fuzhipeng on 2016/11/2.
 */

object JavaGenerate {

    fun brewJava(classEntity: ClassEntity): String {
        val sb = StringBuilder()
        sb.appendln("package ${classEntity.classPackage};")
        sb.appendln("import android.os.Bundle;")
        sb.appendln("import android.util.ArrayMap;")
        sb.appendln("import java.util.ArrayList;")
        sb.appendln("import java.util.HashMap;")
        val blockWriter = BlockWriter(sb)
        codeBlock("public class ${classEntity.aptClassName}", blockWriter) {
            appendln("private  ${classEntity.className}  target;")
            appendln("private  HashMap<String, ArrayList<String>> tagMap = new HashMap<>();")
            //构造器
            codeBlock("private ${classEntity.aptClassName} (${classEntity.className}  target)", blockWriter) {
                appendln("this.target=target;")

                for ((_, field) in classEntity.fields) {
                    val tags = (field.annotataionMap[AutoBundle::class.java] as AutoBundle).value
                    val fieldList = "${field.name}List"
                    appendln("ArrayList<String> $fieldList = new ArrayList<>();")
                    for (itemTag in tags) {
                        appendln("$fieldList.add(\"${itemTag}\");")

                    }
                    appendln("tagMap.put(\"${field.name}\", $fieldList);")
                }
            }

            //静态编码
            codeBlock("public static EncodeBuilder handleEncode(${classEntity.className} target, Bundle bundle)", blockWriter) {
                appendln("${classEntity.aptClassName} obj = new  ${classEntity.aptClassName}(target);")
                appendln("return obj.new EncodeBuilder(bundle);")
            }

            //静态解码
            codeBlock("public static DecodeBuilder decode(${classEntity.className} target, Bundle bundle)", blockWriter) {
                appendln("${classEntity.aptClassName} obj = new ${classEntity.aptClassName}(target);")
                appendln("EncodeBuilder encodeBuilder = obj.new EncodeBuilder(bundle);")
                appendln("return obj.new DecodeBuilder(bundle);")
            }

            //静态编码 自动注入
            codeBlock("public static EncodeBuilder autoEncode(${classEntity.className} target, Bundle bundle)", blockWriter) {
                appendln(" ${classEntity.aptClassName} obj = new  ${classEntity.aptClassName}(target);")
                appendln("EncodeBuilder encodeBuilder = obj.new EncodeBuilder(bundle);")
                for ((_, field) in classEntity.fields) {
                    appendln("encodeBuilder.${field.name}(target.${field.name});//根据当前值自动设置")
                }
                appendln("return encodeBuilder;")
            }

            //class EncodeBuilder
            codeBlock("public class EncodeBuilder", blockWriter) {
                appendln("Bundle bundle;")
                codeBlock("public EncodeBuilder(Bundle bundle)", blockWriter) {
                    appendln("this.bundle = bundle;")
                }

                appendln("private String tag;")
                codeBlock("EncodeBuilder tag(String tag)", blockWriter) {
                    appendln("this.tag = tag;")
                    appendln("return this;")
                }

                codeBlock("public void save()", blockWriter) {
                    val bundleHelper = "${ViewInjectProcessor.HELPER_PACKAGE_PATH}.${ViewInjectProcessor.BUNDLE_HELPER_NAME}"
                    appendln("if (bundle == null) return;")
                    appendln("ArrayMap<Object, Object> map = new ArrayMap<>();")

                    //解析全部
                    codeBlock("if (tag == null)", blockWriter) {
                        for ((_, field) in classEntity.fields) {
                            encode(field, bundleHelper, blockWriter)
                        }
                    }

                    //过滤出tag的字段解析
                    codeBlock("else", blockWriter) {
                        for ((_, field) in classEntity.fields) {
                            val fieldTags = field.name + "Tags"
                            appendln("ArrayList<String> $fieldTags = tagMap.get(\"${field.name}\");")
                            codeBlock("if ($fieldTags != null && ($fieldTags.contains(\"*\") || $fieldTags.contains(tag)))", blockWriter) {
                                encode(field, bundleHelper, blockWriter)
                            }
                        }
                    }
                    appendln("$bundleHelper.putValue(bundle, map);")
                }

                //生成构造方法
                for ((_, field) in classEntity.fields) {
                    val viewId = (field.annotataionMap[AutoBundle::class.java] as AutoBundle).value
                    val name = field.name
                    val type = field.type
                    //过滤出tag的字段解析
                    appendln("private $type $name;")
                    codeBlock("EncodeBuilder $name($type $name)", blockWriter) {
                        appendln("this.$name = $name;")
                        appendln("return this;")
                    }
                }
            }

            //DecodeBuilder
            codeBlock("public class DecodeBuilder", blockWriter) {
                appendln("Bundle bundle;")

                codeBlock("public DecodeBuilder(Bundle bundle)", blockWriter) {
                    appendln("this.bundle = bundle;")
                }


                appendln("private String tag;")
                codeBlock("DecodeBuilder tag(String tag)", blockWriter) {
                    appendln("this.tag = tag;")
                    appendln("return this;")
                }

                codeBlock("public void resolve()", blockWriter) {
                    appendln("if (bundle == null) return;")

                    //解析全部
                    codeBlock("if (tag == null)", blockWriter) {
                        for ((_, field) in classEntity.fields) {
                            decode(field)
                        }
                    }
                    //过滤出tag的字段解析
                    codeBlock("else", blockWriter) {
                        for ((_, field) in classEntity.fields) {
                            val fieldTags = field.name + "Tags"
                            appendln("ArrayList<String> $fieldTags = tagMap.get(\"${field.name}\");")
                            codeBlock("if ($fieldTags != null && ($fieldTags.contains(\"*\") || $fieldTags.contains(tag))) ", blockWriter) {
                                decode(field)
                            }
                        }
                    }
                }

                for ((_, field) in classEntity.fields) {
                    val name = "${field.name}Default"
                    val type = field.type
                    appendln("private ${type} ${name};")
                    codeBlock("public DecodeBuilder ${name}(${type} ${name})", blockWriter) {
                        appendln("this.${name} = ${name};")
                        appendln("return this;")
                    }
                }
            }
        }

        return sb.toString()
    }

    private fun BlockWriter.decode(field: FieldEntity) {
        val fieldDefaultName = "${field.name}Default"
        appendln("Object ${field.name} = bundle.get(\"${field.name}\");")
        appendln("target.${field.name} = ${field.name} != null ? (${field.type}) ${field.name} : ${fieldDefaultName};")
    }

    private fun BlockWriter.encode(field: FieldEntity, bundleHelper: String, blockWriter: BlockWriter) {
        val name = field.name
        val isParcelableStyle = field.name+"IsParcelableStyle"
        //过滤出tag的字段解析
        appendln("boolean $isParcelableStyle = $bundleHelper.isParcelableStyle(bundle, \"$name\", this.$name);")
        codeBlock(" if (! $isParcelableStyle)", blockWriter) {
            appendln("map.put( \"$name\", this.$name);")
        }
    }

    fun codeBlock(beginStr: String, blockWriter: BlockWriter, method: BlockWriter.() -> Unit) {
        blockWriter.appendln("$beginStr{")
        blockWriter.count++
        method.invoke(blockWriter)
        blockWriter.count--
        blockWriter.appendln("}")
    }

    class BlockWriter(val sb: StringBuilder) {
        var count = 0
        fun appendln(content: String) {
            if (count > 0) {
                for (i in 0 until count) {
                    sb.append("\t")
                }
            }
            sb.appendln("$content")
        }
    }


    fun brewHelperJava(): String {
        val sb = StringBuilder()
        sb.append("package ${ViewInjectProcessor.HELPER_PACKAGE_PATH};\n" +
                "\n" +
                "import android.os.BaseBundle;\n" +
                "import android.os.Build;\n" +
                "import android.os.Bundle;\n" +
                "import android.os.Parcelable;\n" +
                "import android.util.ArrayMap;\n" +
                "import android.util.SparseArray;\n" +
                "import androidx.annotation.RequiresApi;\n" +
                "import java.lang.reflect.InvocationTargetException;\n" +
                "import java.lang.reflect.Method;\n" +
                "import java.util.ArrayList;\n" +
                "import java.util.Map;\n" +
                "\n" +
                "public class BundleHelper {\n" +
                "\n" +
                "    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)\n" +
                "    public static void putValue(Bundle bundle, ArrayMap map) {\n" +
                "        try {\n" +
                "            Method method = BaseBundle.class.getDeclaredMethod(\"putAll\", Map.class);\n" +
                "            method.setAccessible(true);\n" +
                "            method.invoke(bundle, map);\n" +
                "        } catch (NoSuchMethodException e) {\n" +
                "            e.printStackTrace();\n" +
                "        } catch (IllegalAccessException e) {\n" +
                "            e.printStackTrace();\n" +
                "        } catch (InvocationTargetException e) {\n" +
                "            e.printStackTrace();\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    public static boolean isParcelableStyle(Bundle bundle, String key, Object value) {\n" +
                "        if (value == null) return false;\n" +
                "\n" +
                "        if (Parcelable.class.isAssignableFrom(value.getClass())) {\n" +
                "            bundle.putParcelable(key, (Parcelable) value);\n" +
                "            return true;\n" +
                "        } else if (Parcelable[].class.isAssignableFrom(value.getClass())) {\n" +
                "            bundle.putParcelableArray(key, (Parcelable[]) value);\n" +
                "            return true;\n" +
                "        } else if (ArrayList.class.isAssignableFrom(value.getClass())\n" +
                "                && ((ArrayList) value).size() > 0\n" +
                "                && Parcelable.class.isAssignableFrom(((ArrayList) value).get(0).getClass())\n" +
                "        ) {\n" +
                "            bundle.putParcelableArrayList(key, (ArrayList) value);\n" +
                "            return true;\n" +
                "        } else if (SparseArray.class.isAssignableFrom(value.getClass())\n" +
                "                && ((SparseArray) value).size() > 0\n" +
                "                && Parcelable.class.isAssignableFrom(((SparseArray) value).valueAt(0).getClass())\n" +
                "        ) {\n" +
                "            bundle.putSparseParcelableArray(key, (SparseArray) value);\n" +
                "            return true;\n" +
                "        } else {\n" +
                "            return false;\n" +
                "        }\n" +
                "    }\n" +
                "}")
        return sb.toString()
    }
}
