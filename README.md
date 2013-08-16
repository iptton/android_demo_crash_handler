android_demo_crash_handler
==========================

little demo shows how to handle crash

1. 自定义 Application
1. 在自定义的Application onCreate 里设置 Thread.setDefaultUncaughtExceptionHandler
1. 在新建的UncaughtExceptionHandler里处理未捕获的异常
1. 在捕获异常后，要么用原 Handler来处理，要么就退出当前进程（本例中退出当前进程）
1. 如果捕获异常后需要启动另一个Activity，则此Activity应该有独立的Process。(Manifest.xml 及 Intent 中都需要有所设置)
