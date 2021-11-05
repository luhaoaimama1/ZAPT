package zone.com.annotationstudy.sample;

import android.os.BaseBundle;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.ArrayMap;
import android.util.SparseArray;
import androidx.annotation.RequiresApi;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

public class BundleHelper {

    public static void putValue(Bundle bundle, ArrayMap map) {
        //兼容到android11
        try {
            Method method = null;
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                method = BaseBundle.class.getDeclaredMethod("putAll", ArrayMap.class);
            } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                method = BaseBundle.class.getDeclaredMethod("putAll", Map.class);
            }

            if (method != null) {
                method.setAccessible(true);
                method.invoke(bundle, map);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static boolean isParcelableStyle(Bundle bundle, String key, Object value) {
        if (value == null) return false;

        if (Parcelable.class.isAssignableFrom(value.getClass())) {
            bundle.putParcelable(key, (Parcelable) value);
            return true;
        } else if (Parcelable[].class.isAssignableFrom(value.getClass())) {
            bundle.putParcelableArray(key, (Parcelable[]) value);
            return true;
        } else if (ArrayList.class.isAssignableFrom(value.getClass())
                && ((ArrayList) value).size() > 0
                && Parcelable.class.isAssignableFrom(((ArrayList) value).get(0).getClass())
        ) {
            bundle.putParcelableArrayList(key, (ArrayList) value);
            return true;
        } else if (SparseArray.class.isAssignableFrom(value.getClass())
                && ((SparseArray) value).size() > 0
                && Parcelable.class.isAssignableFrom(((SparseArray) value).valueAt(0).getClass())
        ) {
            bundle.putSparseParcelableArray(key, (SparseArray) value);
            return true;
        } else {
            return false;
        }
    }
}

//        Point p = new Point(100, 100);
//    Parcelable.class.isAssignableFrom(object.getClass());
//
//    Point[] parr = new Point[3];
////ok
//    Parcelable[].class.isAssignableFrom(parr.getClass());
//
//    ArrayList<Point> points = new ArrayList<>();
//        points.add(p);
////points.get()
//
//    List.class.isAssignableFrom(points.getClass());
//    Parcelable.class.isAssignableFrom(points.get(0).getClass());
//
//    SparseArray<Point> sparseArray = new SparseArray();
//        sparseArray.put(3, p);
//
//    SparseArray.class.isAssignableFrom(sparseArray.getClass());
//    Parcelable.class.isAssignableFrom(sparseArray.valueAt(0).getClass());
