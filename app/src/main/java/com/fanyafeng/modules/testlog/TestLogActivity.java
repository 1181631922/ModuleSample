package com.fanyafeng.modules.testlog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.fanyafeng.modules.BuildConfig;
import com.fanyafeng.modules.R;
import com.fanyafeng.modules.test.LogHelper;
import com.fanyafeng.modules.test.Zprint;
import com.ripple.log.RippleLog;


public class TestLogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogHelper.initDebuggable(this);
        setContentView(R.layout.activity_test_log);
        initView();
        initData();

    }

    private void initView() {
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Zprint.log(this.getClass(), "运行");
                LogHelper.log(Log.ERROR, "查看kt的log测试类");
            }
        });
    }

    private void initData() {

    }
}