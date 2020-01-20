package zone.com.annotationstudy

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import butterknife.BindView
import butterknife.BindViews
import butterknife.ButterKnife
import com.zone.ZField
import com.zone.ZMethod

//@ZClass("MainActivity____")
class MainActivity : AppCompatActivity() {

    @ZField(R.id.bt_annotation)
    @JvmField //must be
    var bt_annotation: View? = null
    @ZField(R.id.bt_processor)
    @JvmField  //must be
    @BindView(R.id.bt_processor)
    var bt_processor: View? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        ButterKnife.bind(this);
        MainActivity_ViewBinding(this).unbind()
        //不报错代表我成功了~
        zone.com.annotationstudy.MainActivity_Injector(this).bind()
    }


    @ZMethod(R.id.bt_annotation, R.id.bt_processor)
    fun onClick(v: View, var2: Int) {
        when (v.id) {
            R.id.bt_annotation ->
                //运行时注解
                startActivity(Intent(this, AnnotationActivity::class.java))
            R.id.bt_processor -> {
            }
        }
    }
}
