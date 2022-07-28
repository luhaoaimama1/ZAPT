
## first
down aptlib for you


## this example is AutoBundle annotation

```java
public class WriteAutoFragment extends WriteAutoParentFragment {
    @AutoBundle
    int age;
    @AutoBundle
    List<Point> pointList;
    @AutoBundle
    boolean keyBoolean;
    @AutoBundle
    String keyString;
    public static WriteAutoFragment newInstance(int age) {
            Log.d("WriteAutoFragment","newInstance");
            Bundle args = new Bundle();
            WriteAutoFragment fragment = new WriteAutoFragment();
            Point point = new Point(100, 50);
            ArrayList<Point> objects = new ArrayList<>();
            objects.add(point);
            fragment.autoBundleInjector.handleEncode(args)
                    .age(age)
                    .pointList(objects)
                    .keyBoolean(true)
                    .keyString("string~")
                    .save();
            fragment.setArguments(args);
            return fragment;
        }
       @Override
       public void onAttach(Context context) {
           super.onAttach(context);
           Log.d("WriteAutoFragment","onAttach");
           autoBundleInjector.decode(getArguments())
                  .resolve();
       }

       @Override
       public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
           super.onViewStateRestored(savedInstanceState);
           Log.d("WriteAutoFragment","onViewStateRestored");
           autoBundleInjector.decode(savedInstanceState)
                   .resolve();
       }

       @Override
       public void onSaveInstanceState(@NonNull Bundle outState) {
           super.onSaveInstanceState(outState);
           Log.d("WriteAutoFragment","onSaveInstanceState");
           autoBundleInjector.autoEncode(outState)
                   .save();
       }

}
```

## use aptlib resolve annotation

```java

@AutoService(Processor::class)
class ViewInjectProcessor : AbstractProcessorAPT() {
    companion object {
        internal val SUFFIX = ElementResolver.GENERATE_LABEL + "Injector"

        init {
            LogConfig.setFileAddress("/Users/nutstore/AndroidStudioProjects/AnnotationStudy/apt/src/main/java/com/zone/process.txt")
        }
    }

    override fun getSupportedAnnotationClasses(): Array<Class<out Annotation>> =
            arrayOf(ZField::class.java, ZMethod::class.java, ZClass::class.java)

    override fun process(annotations: Set<TypeElement>, env: RoundEnvironment): Boolean {

        mElementResolver.messager.printMessage(Diagnostic.Kind.WARNING, "写入成功 ：")
        //解析成实体类
        for (support in supportedAnnotationClasses) {
            mElementResolver.resolve(env, support)
        }

        //文件写入
        for ((_, value) in mElementResolver.classEntityMap) {
            try {
                value.aptClassName = value.classSimpleName.toString() + SUFFIX
                JavaFileUtils.write(mElementResolver, value) { JavaGenerate.brewJava(value) }
            } catch (e: Exception) {
                mElementResolver.messager.printMessage(Diagnostic.Kind.ERROR, e.message)
            }

        }
        //log打印；
        mElementResolver.printLog()
        return true
    }
}
```


# Reference&Thanks：

https://lizhaoxuan.github.io/

http://www.race604.com/annotation-processing/

https://segmentfault.com/a/1190000002785541

http://blog.csdn.net/lmj623565791/article/details/51931859