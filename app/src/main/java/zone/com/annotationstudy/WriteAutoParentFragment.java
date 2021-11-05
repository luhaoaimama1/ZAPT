package zone.com.annotationstudy;

import android.graphics.Point;

import androidx.fragment.app.Fragment;

import com.zone.AutoBundle;
import java.util.List;

public class WriteAutoParentFragment extends Fragment {
    @AutoBundle
    List<Point> pointListParent;
    @AutoBundle
    boolean keyBooleanParent;
}
