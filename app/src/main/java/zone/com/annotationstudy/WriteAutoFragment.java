package zone.com.annotationstudy;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.zone.AutoBundle;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WriteAutoFragment extends Fragment {

    public static final String INIT = "Init";
    public static final String SAVE_STATE = "SaveState";

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

    public static WriteAutoFragment newInstance(int age) {

        Bundle args = new Bundle();
        WriteAutoFragment fragment = new WriteAutoFragment();
        Point point = new Point(100, 50);
        ArrayList<Point> objects = new ArrayList<>();
        objects.add(point);
        WriteAutoFragmentAutoBundleInjector.handleEncode(fragment, args)
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
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        WriteAutoFragmentAutoBundleInjector.decode(this, getArguments())
                .resolve();
        if (getArguments() != null) getArguments().clear();
    }

    TextView textView;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        textView = new TextView(inflater.getContext());
        return textView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (textView != null) {
            StringBuffer sb = new StringBuffer();
            Field[] fields = WriteAutoFragment.class.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                try {
                    fields[i].setAccessible(true);
                    if (fields[i].get(this) != null) {
                        sb.append("name:" + fields[i].getName() + "\t value:" + fields[i].get(this).toString() + "\n");
                    } else {
                        sb.append("name:" + fields[i].getName() + "\t value: 无\n");
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            textView.setText(sb.toString());
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        WriteAutoFragmentAutoBundleInjector.autoEncode(this, outState)
                .tag(SAVE_STATE)
                .save();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        WriteAutoFragmentAutoBundleInjector.decode(this, savedInstanceState)
                .tag(SAVE_STATE)
                .resolve();
    }
}
