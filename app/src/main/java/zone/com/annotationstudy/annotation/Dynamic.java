package zone.com.annotationstudy.annotation;

import android.view.View;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fuzhipeng on 2016/10/28.
 */

public class Dynamic implements InvocationHandler {
    public Map<Integer,Method> maps=new HashMap<>();
    //要代理的原始对象
    private Object obj;
    //因为每次声明都返回一个 所以  acitivity 需要若引用持有 不然会导致内存泄漏
    //不可以在nodestory 方法执行完毕  去掉此引用 不行 因为ondestory执行完毕 案例来说引用应该消失吧

    private WeakReference<Object> handlerRef;

    public Dynamic(Object obj) {
        super();
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if("onClick".equals(method.getName())&&maps.size()>0){
            View view = (View) args[0];
            //找到 该id声明的onclick方法
            System.err.println("被代替的方法"+method.getName());
            System.err.println("代替后的方法"+ maps.get(view.getId()).getName());
            maps.get(view.getId()).invoke(obj,args);
        }
        return null;
    }
}
