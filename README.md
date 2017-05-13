# Music_2.0
Music_app
知识点回顾：
1、Activity启动模式，数据传递
2、Service启动，与Activity数据传递
3、Broadcast动态注册
4、Recycleview支持多布局item
5、DataBase增、删、查、改

一、Activity
1、用到了activity启动模式中的：singleTask
  在Music_2.0的作用：通知栏启动Mainactivity时，有就复用并且清除其上所有activity，没有就重新创建
                    从其他activity跳转到Mainactivity时也会清除其他activity，保证了MAinActivity的唯一
  MainActivity的launchMode设置singleTask，作用是只要栈中有MAinActivity实例就不创建，MAinActivity变为栈顶对象时，会把它上面的activity实例全部出栈
  设置方法：1是在manifest中设置某个activity的launchMode
           2是在代码中通过intent.flag=Intent.FLAG_ACTIVITY_CLEAR_TOP
            注意：1. intnet.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 跳转的页面会从 OnCreate（）方法开始执行。
                  2.FLAG_ACTIVITY_CLEAR_TOP  相当于 加载模式的 signleTask
                   3.设置了此模式要重写OnNewIntent(),因为栈中如果已经有了MAinActivity，再次启动时通过getIntent拿到的是旧的intent，要拿的新数据就重写此方法
2、数据传递：
    将数据Data对象放入Bundle中，通过intent的携带传递出去
     

        
