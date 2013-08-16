package com.iptton.android_demo.crash_handler;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Author: iptton
 * Date: 13-8-16
 * Time: 下午3:19
 */
public class MyApplication extends Application {

    class MyCrashHandler implements Thread.UncaughtExceptionHandler {

        WeakReference<Context> contextRef;
        Thread.UncaughtExceptionHandler oldHandler;
        boolean isCrashing = false;

        public MyCrashHandler(Context applicationContext, Thread.UncaughtExceptionHandler oldHandler) {
            contextRef = new WeakReference<Context>(applicationContext);
            this.oldHandler = oldHandler;
        }

        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            synchronized (this){
                if(isCrashing){
                    Log.i("Crashes", "isCrashing");
                    return;
                }
                isCrashing = true;
            }
            if(ex == null)return;
            Context context = contextRef.get();
            Intent i = new Intent(context, CrashActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtra("exception",ex);
            context.startActivity(i);
            // 原进程已出错，直接关闭
            //System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        String name = "";
        name = getProcessName();
        Log.d(this.getClass().getSimpleName(), "onCreate "+name);

        // setup crash handler
        Thread.UncaughtExceptionHandler oldHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new MyCrashHandler(this.getApplicationContext(), oldHandler));
    }

    private String getProcessName()  {
        int pID = android.os.Process.myPid();
        String name = "";
        ActivityManager activityManager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> l = activityManager.getRunningAppProcesses();
        for(ActivityManager.RunningAppProcessInfo info : l){
            if(info.pid == pID){
                name = info.processName;
                break;
            }
        }
        return name;
    }


    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        Log.d(this.getClass().getSimpleName(), getProcessName() + " startActivity "+intent.getComponent().getShortClassName());
    }
}
