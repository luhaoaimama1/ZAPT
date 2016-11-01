package zone.com.annotationstudy;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import zone.com.annotationstudy.annotation.ViewZinject;
import zone.com.annotationstudy.annotation.ZOnclick;
public class AnnotationActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ViewZinject.inject(this);
	}

	@ZOnclick({ R.id.id_btn, R.id.id_btn02 })
	public void clickBtnInvoked(View view)
	{
		switch (view.getId())
		{
		case R.id.id_btn:
			Toast.makeText(this, "Inject Btn01 !", Toast.LENGTH_SHORT).show();
			break;
		case R.id.id_btn02:
			Toast.makeText(this, "Inject Btn02 !", Toast.LENGTH_SHORT).show();
			break;
		}
	}

}
