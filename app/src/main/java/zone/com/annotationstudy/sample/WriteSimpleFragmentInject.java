package zone.com.annotationstudy.sample;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcel;
import android.util.ArrayMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 1. 存传入的值。 取传入的值
 * <p>
 * //builder(MyFragment) 这个因为可能是anrdoidx或者不是所以让使用的人自己封装
 * BundleInject.encode(obj inject,Bundle outState)
 * .age(15) 第二个参数是默认值。 age是字段名字
 * .save()
 * <p>
 * 2. 存取当前的值。
 * BundleInject.encode(obj inject,Bundle outState).save
 * <p>
 * 3.
 * BundleInject.decode(obj inject, Bundle outState)
 * .ageDefault()//设置默认值
 * .resolve()
 * 根据inject找到对应的生成类
 * <p>
 * <p>
 * 范例一个int age
 * todo
 * endcode/decode  不解析的 怎么办？ 字段都可以填写编号。
 * 不传String 则所有的都解析
 * 例如
 * <p>
 * onsave和建立fragment的时候。save的不一样怎么办？
 *
 * @Argment("create") int age
 * @Argment("saveSate") //当然是字符串数组
 * int age2
 * <p>
 * 那么建立fragment的endcode/decode 要传入"create"
 * 而onSave则 要传入"saveSate"
 * <p>
 * 如果不传入的话，则都编解码decode
 */
public class WriteSimpleFragmentInject {
    WriteSimpleFragment target;

    HashMap<String, List<String>> tagMap = new HashMap<>();

    private WriteSimpleFragmentInject(WriteSimpleFragment target) {
        this.target = target;
        tagMap.put("age", Arrays.asList(new String[]{"*", "a"}));
    }

    public static EncodeBuilder autoEncode(WriteSimpleFragment target, Bundle bundle) {
        WriteSimpleFragmentInject obj = new WriteSimpleFragmentInject(target);
        EncodeBuilder encodeBuilder = obj.new EncodeBuilder(bundle);

        encodeBuilder.age(target.age);//根据当前值自动设置
        return encodeBuilder;
    }

    public static EncodeBuilder handleEncode(WriteSimpleFragment target, Bundle bundle) {
        WriteSimpleFragmentInject obj = new WriteSimpleFragmentInject(target);
        return obj.new EncodeBuilder(bundle);
    }

    public static DecodeBuilder decode(WriteSimpleFragment target, Bundle bundle) {
        WriteSimpleFragmentInject obj = new WriteSimpleFragmentInject(target);
        DecodeBuilder decodeBuilder = obj.new DecodeBuilder(bundle);
        //因为每次解码 都是
        decodeBuilder.ageDefault(target.age);//
        return decodeBuilder;
    }


    //根据字段成
    public class EncodeBuilder {
        Bundle bundle;

        public EncodeBuilder(Bundle bundle) {
            this.bundle = bundle;
        }

        private String tag;

        EncodeBuilder tag(String tag) {
            this.tag = tag;
            return this;
        }

        private Parcel parcel;

        @SuppressLint("NewApi")
        public void save() {
            if (bundle == null) return;
            ArrayMap<Object, Object> map = new ArrayMap<>();
            if (tag == null) {
                //解析全部

                boolean isParcelableStyle = BundleHelper.isParcelableStyle(bundle, "age", this.age);
                if (!isParcelableStyle) {
                    map.put("age", age);
                }

            } else {
                //过滤出tag的字段解析
            }
            BundleHelper.putValue(bundle, map);
        }

        private int age;

        EncodeBuilder age(int age) {
            this.age = age;
            return this;
        }

    }


    //根据字段成 方法 XXDefault
    public class DecodeBuilder {
        Bundle bundle;

        public DecodeBuilder(Bundle bundle) {
            this.bundle = bundle;
        }

        private String tag;

        DecodeBuilder tag(String tag) {
            this.tag = tag;
            return this;
        }

        public void resolve() {
            if (bundle == null) return;


            if (tag == null) {
                //解析全部
            } else {
                //过滤出tag的字段解析
                List<String> ages = tagMap.get("age");
                if (ages == null || ages.contains("*") || ages.contains("tag")) {
                    Object age = bundle.get("age");
                    target.age = age == null ? (int) age : ageDefault;
                }
            }
        }

        private int ageDefault;

        public DecodeBuilder ageDefault(int ageDefault) {
            this.ageDefault = ageDefault;
            return this;
        }

    }

}
