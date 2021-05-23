package zone.com.annotationstudy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.a_lifecircle_fragment_study.*

class MainActivity : AppCompatActivity() {
    var fragment: WriteAutoFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_lifecircle_fragment_study)

        button.setOnClickListener {
            val beginTransaction = supportFragmentManager.beginTransaction()
            fragment = WriteAutoFragment.newInstance(18)
            fragment?.let {
                beginTransaction.add(R.id.fl1, it, "addFragment")
                beginTransaction.commitNowAllowingStateLoss()
            }
        }
    }
}
