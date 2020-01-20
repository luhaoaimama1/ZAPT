package zone.com.annotationstudy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.zone.ZField;
import com.zone.ZMethod;

//@ZClass("MainActivity____")
public class MainActivity2 extends AppCompatActivity {

    @ZField(R.id.bt_annotation)
    public View bt_annotation;
    @ZField(R.id.bt_processor)
    View bt_processor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//        ButterKnife.bind(this);
        //不报错代表我成功了~
        new zone.com.annotationstudy.MainActivity2_Injector(this).bind();
    }


    @ZMethod({R.id.bt_annotation, R.id.bt_processor})
    public void onClick(View v,int var2) {
        switch (v.getId()) {
            case R.id.bt_annotation:
                //运行时注解
                startActivity(new Intent(this, AnnotationActivity.class));
                break;
            case R.id.bt_processor:
                break;
        }
    }
}
