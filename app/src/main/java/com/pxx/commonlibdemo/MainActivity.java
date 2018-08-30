package com.pxx.commonlibdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.pxx.commonlib.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tv_test)
    TextView tvTest;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        tvTest.setText("Hello!!!!!!!!!");
    }

    @Override
    protected void onViewClick(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
