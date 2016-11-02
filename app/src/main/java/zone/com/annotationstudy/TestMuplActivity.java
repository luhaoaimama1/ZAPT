package zone.com.annotationstudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.example.ZClass;
import com.example.ZField;
import com.example.ZMethod;

//主要是实现了接口和原来不一样了用来分辨数据用的
@ZClass("MTestMuplActivity____")
public class TestMuplActivity extends AppCompatActivity implements Interface1 {

    @ZField(R.id.bt_annotation)
    View bt_annotation;
    @ZField(R.id.bt_processor)
    View bt_processor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//        ButterKnife.bind(this);
    }
    @ZMethod({R.id.bt_annotation,R.id.bt_processor})
    public int onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_annotation:
                //运行时注解
                startActivity(new Intent(this,AnnotationActivity.class));
                break;
            case R.id.bt_processor:
                break;
        }
        return 0;
    }
    @Override
    public void heihei() {

    }
}
