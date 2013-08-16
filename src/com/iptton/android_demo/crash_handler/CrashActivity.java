package com.iptton.android_demo.crash_handler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Author: iptton
 * Date: 13-8-16
 * Time: 下午3:27
 */
public class CrashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        setContentView(tv);

        Intent intent = getIntent();
        Throwable exception = (Throwable) intent.getSerializableExtra("exception");
        tv.setText(getDescript(exception));

    }

    private CharSequence getDescript(Throwable exception) {
        if(exception == null){
            return "";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(exception.getMessage());
        builder.append("\n");
        StackTraceElement[] elements = exception.getStackTrace();
        for (StackTraceElement element : elements) {
            builder.append(element.getFileName());
            builder.append("(" + element.getLineNumber() + ")");
            builder.append(element.getClassName());
            builder.append(element.getMethodName());
            builder.append("\n");
        }
        return builder.toString();
    }
}
