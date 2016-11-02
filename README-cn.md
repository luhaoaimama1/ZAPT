#环境搭建

##First
首先建一个module，必须是javaLib 不然没有AbstractProcessor类；
##Second
 此module的build文件中增加次依赖  主要是为了省事，不然很麻烦的
``` 
 compile 'com.google.auto.service:auto-service:1.0-rc2'
```
##Third
注解和apt解析项目记得分开；不然会站很大内存据说~

###Third的解释：分离处理器和注解
参考:http://www.race604.com/annotation-processing/
如果你已经看了我们的代码库，你将发现我们组织我们的代码到两个maven模块中了。
我们这么做是因为，我们想让我们的工厂模式的例子的使用者，在他们的工程中只编译注解
，而包含处理器模块只是为了编译。有点晕？我们举个例子，如果我们只有一个包。如果另一个开发者
想要把我们的工厂模式处理器用于他的项目中，他就必须包含@Factory注解和整个FactoryProces
sor的代码（包括FactoryAnnotatedClass和FactoryGroupedClasses）到他们项目中。
我非常确定的是，他并不需要在他已经编译好的项目中包含处理器相关的代码。
如果你是一个Android的开发者，你肯定听说过65k个方法的限制（即在一个.dex文件中，
只能寻址65000个方法）。如果你在FactoryProcessor中使用guava，并且把注解和处理器打包在一个包中，
这样的话，Android APK安装包中不只是包含FactoryProcessor的代码，而也包含了整个guava的代码
。Guava有大约20000个方法。所以分开注解和处理器是非常有意义的。

##Four
去做个demo吧~如果基础不是很懂的参考  下面的链接进行学习；

# Reference&Thanks：
https://lizhaoxuan.github.io/

http://www.race604.com/annotation-processing/

https://segmentfault.com/a/1190000002785541

http://blog.csdn.net/lmj623565791/article/details/51931859
