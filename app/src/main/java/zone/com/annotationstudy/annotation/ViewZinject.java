package zone.com.annotationstudy.annotation;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by fuzhipeng on 2016/10/28.
 */

public class ViewZinject {
    public static void inject(Activity activity) {
        Method[] methods = activity.getClass().getMethods();
        for (Method method : methods) {
            ZOnclick methodBinds = method.getAnnotation(ZOnclick.class);
            if (methodBinds != null) {
                int[] ids = methodBinds.value();
                Dynamic dynamic;
                Object proxy = Proxy.newProxyInstance(activity.getClass().getClassLoader(),
                        new Class[]{View.OnClickListener.class},
                        dynamic=new Dynamic(activity));

                for (int id : ids) {
                    dynamic.maps.put(id,method);
                    View view=activity.findViewById(id);
                    if(view!=null)
                        view.setOnClickListener((View.OnClickListener) proxy);
                }

            }
        }
    }

}
