package com.yangxianwen.health.view;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yangxianwen.health.R;
import com.yangxianwen.health.base.BaseMvvmFragment;
import com.yangxianwen.health.databinding.HomeFragBinding;
import com.yangxianwen.health.viewmodel.HomeViewModel;

public class HomeFragment extends BaseMvvmFragment<HomeViewModel, HomeFragBinding> {

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_frag;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}