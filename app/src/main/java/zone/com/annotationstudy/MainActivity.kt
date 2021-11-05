package zone.com.annotationstudy

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.a_lifecircle_fragment_study.*

class MainActivity : AppCompatActivity() {
    var fragment: WriteAutoFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_lifecircle_fragment_study)
        fragment = WriteAutoFragment.newInstance(18)

        button.setOnClickListener {
            val beginTransaction = supportFragmentManager.beginTransaction()
            fragment?.let {
                beginTransaction.replace(R.id.fl1, it, "addFragment")
                beginTransaction.commitNowAllowingStateLoss()
            }
        }
    }
}
