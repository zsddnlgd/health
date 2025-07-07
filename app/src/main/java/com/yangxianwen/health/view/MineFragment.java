package com.yangxianwen.health.view;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yangxianwen.health.R;
import com.yangxianwen.health.base.BaseMvvmFragment;
import com.yangxianwen.health.databinding.MineFragBinding;
import com.yangxianwen.health.viewmodel.MineViewModel;

public class MineFragment extends BaseMvvmFragment<MineViewModel, MineFragBinding> {

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.mine_frag;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}