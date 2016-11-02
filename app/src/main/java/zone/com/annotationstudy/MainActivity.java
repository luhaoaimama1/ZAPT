package zone.com.annotationstudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.example.ZField;
import com.example.ZClass;
import com.example.ZMethod;
import butterknife.ButterKnife;

//@ZClass("MainActivity____")
public class MainActivity extends AppCompatActivity {

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
        new MainActivity$$Injector<MainActivity>().bind(this);
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
