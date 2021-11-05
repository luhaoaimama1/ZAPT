package zone.com.annotationstudy.sample;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.zone.AutoBundle;

import java.lang.reflect.Method;
import java.util.List;

public class WriteSimpleFragment extends Fragment {

    public static final String INIT = "Init";
    public static final String SAVE_STATE = "SaveState";
    @AutoBundle({INIT, SAVE_STATE})
    int age;
    @AutoBundle(SAVE_STATE)
    int age2;

//    @Argument(SAVE_STATE)
    List<Point> pointList;
    int age3NoAno;
    Point keyParcelable;

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
    public static WriteSimpleFragment newInstance(int age) {

        Bundle args = new Bundle();
        WriteSimpleFragment fragment = new WriteSimpleFragment();
        WriteSimpleFragmentInject.handleEncode(fragment, args)
                .age(age)
                .tag(INIT)
                .save();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        WriteSimpleFragmentInject.decode(this, getArguments())
                .tag(INIT)
                .resolve();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        TextView textView = new TextView(inflater.getContext());
        textView.setText(age + "");
        for (Method declaredMethod : Bundle.class.getDeclaredMethods()) {
            if(declaredMethod.getName().startsWith("get")||declaredMethod.getName().startsWith("put")){
                StringBuilder sb=new StringBuilder();
                Class<?>[] parameterTypes = declaredMethod.getParameterTypes();
                for (int i = 0; i < parameterTypes.length; i++) {
                    sb.append(i+":"+parameterTypes[i].getSimpleName()+"\n");
                }
                Log.d("declaredMethod",
                        "name:"+declaredMethod.getName()+
                                "\t getParameterTypes:"+ sb.toString()
                );
            }

        }

        return textView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        WriteSimpleFragmentInject.autoEncode(this, outState)
                .tag(SAVE_STATE)
                .save();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        WriteSimpleFragmentInject.decode(this, savedInstanceState)
                .tag(SAVE_STATE)
                .resolve();
    }
}
