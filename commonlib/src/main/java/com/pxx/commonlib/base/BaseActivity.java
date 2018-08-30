package com.pxx.commonlib.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.pxx.commonlib.CommonConstants;
import com.vondear.rxtool.RxActivityTool;
import com.vondear.rxtool.RxBarTool;
import com.vondear.rxtool.RxLogTool;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by pxx on 2018/8/24.
 * Description：Activity基类
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    private String mActivityName = getClass().getSimpleName().toString();
    private Unbinder unbinder;
    private long lastClickTime = 0;//记录上次点击按钮的时间

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxLogTool.d(CommonConstants.LOG_TAG, mActivityName + ":onCreate");
        if (getLayoutId() > 0) {
            setContentView(getLayoutId());
            unbinder = ButterKnife.bind(this);
            initData(savedInstanceState);
            initView();
        }
        //将Activity放入堆栈
        RxActivityTool.addActivity(this);

        //设置状态栏颜色
        if (RxBarTool.isStatusBarExists(this)) {
            if (Build.VERSION.SDK_INT >= 19) {
                RxBarTool.setStatusBarColor(this, CommonConstants.STATUS_BAR_COLOR);
            } else {
                int height = RxBarTool.getStatusBarHeight(this);
                ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
                View statusBarView = new View(this);
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                statusBarView.setBackgroundColor(getResources().getColor(CommonConstants.STATUS_BAR_COLOR));
                decorView.addView(statusBarView, lp);
            }
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        RxLogTool.d(CommonConstants.LOG_TAG, mActivityName + ":onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        RxLogTool.d(CommonConstants.LOG_TAG, mActivityName + ":onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        RxLogTool.d(CommonConstants.LOG_TAG, mActivityName + ":onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        RxLogTool.d(CommonConstants.LOG_TAG, mActivityName + ":onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        RxLogTool.d(CommonConstants.LOG_TAG, mActivityName + ":onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxLogTool.d(CommonConstants.LOG_TAG, mActivityName + ":onDestroy");
        unbinder.unbind();
        RxActivityTool.finishActivity();
    }

    /**
     * 获取布局文件Id
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化数据
     */
    protected abstract void initData(Bundle savedInstanceState);

    /**
     * 初始化View
     */
    protected abstract void initView();


    /**
     * click点击事件
     *
     * @param v
     */
    protected abstract void onViewClick(View v);

    @Override
    public void onClick(View view) {
        if (isClickFast()) {
            if (view.getTag() != null) {
                RxLogTool.d(CommonConstants.LOG_TAG, "onClick " + view.getTag());
            }
            onViewClick(view);
        }
    }

    /**
     * 防止快速点击,500ms内二次点击无效
     *
     * @return true可执行 false不可执行
     */
    private boolean isClickFast() {
        if (System.currentTimeMillis() - lastClickTime <= 500) {
            return false;
        }
        lastClickTime = System.currentTimeMillis();
        return true;
    }
}
