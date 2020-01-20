package zone.com.annotationstudy

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import butterknife.BindView
import com.zone.ZField
import com.zone.ZMethod
import com.zone.ZMethodCallbackTest

/**
 * 学习注解的
 * 1.根据注解生成类
 * 2. 解析注解动态代理生成类
 */
//@ZClass("MainActivity____")
class MainActivity : AppCompatActivity(), ZMethodCallbackTest {

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
        MainActivity_Injector(this).bind()
    }

    @ZMethod(R.id.bt_annotation, R.id.bt_processor)
    override fun onClick(viewId: Int) {
        when (viewId) {
            //运行时注解
            R.id.bt_annotation -> startActivity(Intent(this, AnnotationActivity::class.java))
            R.id.bt_processor -> {
            }
        }
    }
}
