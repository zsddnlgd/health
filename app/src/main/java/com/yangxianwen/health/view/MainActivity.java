package com.yangxianwen.health.view;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.veepoo.protocol.VPOperateManager;
import com.yangxianwen.health.R;
import com.yangxianwen.health.base.BaseMvvmActivity;
import com.yangxianwen.health.databinding.MainActBinding;
import com.yangxianwen.health.manager.FragManager;
import com.yangxianwen.health.viewmodel.MainViewModel;

public class MainActivity extends BaseMvvmActivity<MainViewModel, MainActBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.main_act;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int tab = FragManager.getInstance().getLastTab();
        if (tab == -1) {
            tab = FragManager.PAGE_HOME;
        }
        setTab(tab);

        mBinding.health.setOnClickListener(v -> setTab(FragManager.PAGE_HOME));
        mBinding.device.setOnClickListener(v -> setTab(FragManager.PAGE_DEVICE));
        mBinding.mine.setOnClickListener(v -> setTab(FragManager.PAGE_MINE));
    }

    private void setTab(int tab) {
        switch (tab) {
            case FragManager.PAGE_HOME:
                mBinding.health.setBackgroundColor(Color.YELLOW);
                mBinding.device.setBackgroundColor(Color.TRANSPARENT);
                mBinding.mine.setBackgroundColor(Color.TRANSPARENT);
                break;
            case FragManager.PAGE_DEVICE:
                mBinding.health.setBackgroundColor(Color.TRANSPARENT);
                mBinding.device.setBackgroundColor(Color.YELLOW);
                mBinding.mine.setBackgroundColor(Color.TRANSPARENT);
                break;
            case FragManager.PAGE_MINE:
                mBinding.health.setBackgroundColor(Color.TRANSPARENT);
                mBinding.device.setBackgroundColor(Color.TRANSPARENT);
                mBinding.mine.setBackgroundColor(Color.YELLOW);
                break;
        }
        FragManager.getInstance().showFrag(getActivity(), R.id.center_container_frag, tab);
    }
}
