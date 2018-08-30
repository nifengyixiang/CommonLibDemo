package com.pxx.commonlib.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pxx.commonlib.CommonConstants;
import com.vondear.rxtool.RxLogTool;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by pxx on 2018/8/24 11:45;
 * Description：fragment基类
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    private Context mContext;
    private String mName = getClass().getSimpleName().toString();
    private Unbinder unbinder;
    private long lastClickTime;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        RxLogTool.d(CommonConstants.LOG_TAG, mName + ": onAttach");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RxLogTool.d(CommonConstants.LOG_TAG, mName + ": onCreateView");
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RxLogTool.d(CommonConstants.LOG_TAG, mName + ": onViewCreated");
        if (getLayoutId() > 0) {
            initData();
            initView(view);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RxLogTool.d(CommonConstants.LOG_TAG, mName + ": onActivityCreated");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    /**
     * 获取Fragment Layout id
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化View
     *
     * @param view
     */
    protected abstract void initView(View view);

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
