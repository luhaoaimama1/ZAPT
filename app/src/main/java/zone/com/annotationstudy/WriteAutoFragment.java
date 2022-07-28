package zone.com.annotationstudy;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.zone.AutoBundle;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 带优化的点：
 *
 * 1.WriteAutoFragmentAutoBundleInjector.decode(this, getArguments())
 * ======> .keyDefaults()  把当前的值自动都设置成默认值
 * .resolve();
 *
 * over~
 * 继承 带改良。
 *
 * 不能用继承 原因是 target onSaveInstanceState 继承关系只会走一次。
 * 但是子会调用 autoBundleInjector.decode 父也会autoBundleInjector.decode
 * 这样如果继承的话 父就会触发两次。分离更好一点。对于view也挺好就是fragment传入的时候比较麻烦
 *
 * 但是不用继承这个麻烦的事情如何解决。父属性符合设置？
 *
 * 如果用继承。对应的属性不去管理。又如何呢？好纠结
 *
 *
 * 需要每次都new WriteAutoParentFragmentAutoBundleInjector() 在用这个引用去使用
 * 考虑javassit 动态代理  weakhashmap 反射设置属性用委托处理 使原来的好使、实现接口 自己实现set/get。
 * 但是最后还是打算自己构造  因为这个是不仅仅是fragment用 也可以用view 也可以用于任何bundle的地方。
 */
public class WriteAutoFragment extends WriteAutoParentFragment {

    public static final String INIT = "Init";
    public static final String SAVE_STATE = "SaveState";

//    fragment从新创建的时候对象已经是新的了 所以每次取的都是默认值
//    Point pointOtherTest=new Point();

//onSaveInstanceState  存储数据： 每次都会触发。

//onViewStateRestored  取出数据： fragment从新创建的话就会触发 并会触发onActivityCreated。
// 正常不销毁view切后台在可见不会触发。
// 可以认为    onViewStateRestored的时候fragment的数据还都是新的

    int fieldNoAno;

    @AutoBundle({INIT, SAVE_STATE})
    int age;

    @AutoBundle
    List<Point> pointList;

    @AutoBundle
    boolean keyBoolean;
    @AutoBundle
    String keyString;
    @AutoBundle
    Byte keyByte;
    @AutoBundle
    short keyShort;
    @AutoBundle
    int keyInt;
    @AutoBundle
    long keyLong;
    @AutoBundle
    double keyDouble;
    @AutoBundle
    float keyFloat;
    @AutoBundle
    UUID keySerializable;
    @AutoBundle
    Point keyParcelable;

    @AutoBundle({INIT})
    UUID keyNoSaveState;
    private final WriteAutoFragmentAutoBundleInjector autoBundleInjector = new WriteAutoFragmentAutoBundleInjector(this);

    public static WriteAutoFragment newInstance(int age) {
        Log.d("WriteAutoFragment","newInstance");
        Bundle args = new Bundle();
        WriteAutoFragment fragment = new WriteAutoFragment();
        Point point = new Point(100, 50);
        ArrayList<Point> objects = new ArrayList<>();
        objects.add(point);

        fragment.autoBundleInjector.handleEncode(args)
                .age(age)
                .pointList(objects)
                .keyBoolean(true)
                .keyDouble(12.0)
                .keyFloat(10.f)
                .keyString("string~")
                .keySerializable(UUID.randomUUID())
                .keyNoSaveState(UUID.randomUUID())
                .keyInt(100)
                .keyLong(1000)
                .keyShort(Short.parseShort("10"))
                .keyByte("car".getBytes()[0])
                .keyParcelable(new Point(1000, 1000))
                .save();

        //继承关系的处理
        fragment.getWriteAutoParentFragmentAutoBundleInjector().handleEncode(args)
                .keyBooleanParent(true)
                .keyParentString("父string")
                .save();

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("WriteAutoFragment","onAttach");
        autoBundleInjector.decode(getArguments())
//                .keyDefaults()
                .resolve();
//        if (getArguments() != null) getArguments().clear();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("WriteAutoFragment","onCreate");
    }

    TextView textView;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        ScrollView scrollView = new ScrollView(inflater.getContext());
        textView = new TextView(inflater.getContext());
        textView.setTextSize(16);
        scrollView.addView(textView);
        return scrollView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("WriteAutoFragment","onStart");
        if (textView != null) {
            StringBuffer sb = new StringBuffer();
            sb.append("Fragment:" + this + "\n");
            getClassFields(sb, WriteAutoFragment.class);
            sb.append("=====父属性========\n");
            getClassFields(sb, WriteAutoParentFragment.class);
            textView.setText(sb.toString());
        }

    }

    private void getClassFields(StringBuffer sb, Class writeAutoFragmentClass) {
        Field[] fields = writeAutoFragmentClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            try {
                fields[i].setAccessible(true);
                if (fields[i].get(this) != null) {
                    sb.append(fields[i].getName() + ":" + fields[i].get(this).toString() + "\n");
                } else {
                    sb.append(fields[i].getName() + ": >>无<<\n");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("WriteAutoFragment","onActivityCreated");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("WriteAutoFragment","onResume");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("WriteAutoFragment","onViewCreated");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("WriteAutoFragment","onSaveInstanceState");
        autoBundleInjector.autoEncode(outState)
                .tag(SAVE_STATE)
                .save();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d("WriteAutoFragment","onViewStateRestored");
        autoBundleInjector.decode(savedInstanceState)
                .tag(SAVE_STATE)
                .resolve();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("WriteAutoFragment","onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("WriteAutoFragment","onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("WriteAutoFragment","onDestroyView");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("WriteAutoFragment","onDetach");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("WriteAutoFragment","onDestroy");
    }
}
