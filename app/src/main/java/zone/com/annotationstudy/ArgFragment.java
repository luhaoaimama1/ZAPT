package zone.com.annotationstudy;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.UUID;

public class ArgFragment extends Fragment {

    //Arg
    //SaveState
    private boolean keyBoolean;
    private String keyString;

    private Byte keyByte;
    private short keyShort;
    private int keyInt;
    int age;
    private long keyLong;

    private double keyDouble;
    private float keyFloat;

    private UUID keySerializable;
    private Point keyParcelable;

    private Bundle keyBundle;


    /**
     * 1. 存传入的值。 取传入的值
     * BundleInge
     * <p>
     * BundleInject.builder(Bundle outState)
     * .build()
     * <p>
     * BundleInject.builder(MyFragment)
     * .age(15)/.age(15,-1) 第二个参数是默认值。 age是字段名字
     * .build()
     * <p>
     * <p>
     * 2. 存取当前的值。
     * BundleInject.Autoencode(Bundle outState)
     * BundleInject.Autodecode(Bundle outState,Default.age()//设置默认值)
     *
     * @return
     */
    public static ArgFragment newInstance() {

        Bundle args = new Bundle();
        ArgFragment fragment = new ArgFragment();

        args.putBoolean("boolean", true);
        args.putChar("char", 'c');
        args.putString("string", "keyString");

        args.putByte("byte", "keyByte".getBytes()[0]);
        args.putShort("short", Short.parseShort("13"));
        args.putInt("int", 100);
        args.putLong("long", 456789);

        args.putDouble("double", 1.0);
        args.putFloat("float", 1.0f);

        args.putParcelable("Parcelable", new Point(100, 100));
        args.putSerializable("Serializable", UUID.randomUUID());


        Bundle value = new Bundle();
        value.putString("key", "bundle~");
        args.putBundle("bundle", value);

//        args.putBooleanArray();
//        args.putCharArray();
//        args.putStringArray();
//
//        args.putByteArray();
//        args.putShortArray();
//        args.putIntArray();
//        args.putLongArray();
//
//        args.putFloatArray();
//        args.putDoubleArray();
//
//        args.putParcelableArray();
//
//
//        args.putIntegerArrayList();
//        args.putStringArrayList();
//        args.putCharSequenceArrayList();
//        args.putParcelableArrayList();


        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }
}
