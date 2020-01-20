//package com.zone;
//import com.zone.ZBinder;
//import android.view.View;
//import androidx.annotation.UiThread;
//public class MainActivity_Injector implements ZBinder{
//private zone.com.annotationstudy.MainActivity target;
//@UiThread
//public MainActivity_Injector(zone.com.annotationstudy.MainActivity target){
// this.target=target;
// }
// @Override public void bind() {
//target.bt_processor =  (android.view.View)target.findViewById(2131165219);
//target.bt_annotation =  (android.view.View)target.findViewById(2131165218);
//target.findViewById(2131165218).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                    //target.onclick(v);
//            }
//        });
//target.findViewById(2131165219).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                    //target.onclick(v);
//            }
//        });
//} @Override public void unbind() {
//target.bt_processor =  null;
//target.bt_annotation =  null;
// }
// }
