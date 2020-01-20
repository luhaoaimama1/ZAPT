package zone.com.annotationstudy;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.zone.ZClass;
import com.zone.ZField;
import com.zone.ZMethod;
import com.zone.ZMethodCallbackTest;

//主要是实现了接口和原来不一样了用来分辨数据用的
@ZClass("MTestMuplActivity____")
public class TestMuplActivity extends AppCompatActivity implements Interface1, ZMethodCallbackTest {

    @ZField(R.id.bt_annotation)
    View bt_annotation;
    @ZField(R.id.bt_processor)
    View bt_processor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    @Override
    public void heihei() {

    }

    @ZMethod({R.id.bt_annotation, R.id.bt_processor})
    @Override
    public void onClick(int viewId) {
        switch (viewId) {
            case R.id.bt_annotation:
                break;
            case R.id.bt_processor:
                break;
        }
    }
}
