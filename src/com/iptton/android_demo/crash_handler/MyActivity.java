package com.iptton.android_demo.crash_handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        // 直接抛错
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                throw new NullPointerException("hello I am Exception");
            }
        },3000);

    }
}
