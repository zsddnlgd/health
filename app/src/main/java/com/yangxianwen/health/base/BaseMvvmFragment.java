package com.yangxianwen.health.base;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import com.yangxianwen.health.manager.LiveDataManager;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseMvvmFragment<VM extends BaseViewModel, VB extends ViewBinding> extends BaseFragment {

    protected VM mViewModel;
    protected VB mBinding;

    protected LiveDataManager mLiveDataManager;

    private final Handler mHandler = new Handler();
    private Thread mUiThread;

    protected abstract int getLayoutId();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUiThread = Thread.currentThread();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = (VB) DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
        mLiveDataManager = new LiveDataManager();
        mLiveDataManager.observeForever(mViewModel.getTips(), s -> {
            if (s == null) {
                return;
            }
            showToast(s);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getLifecycle().removeObserver(mViewModel);
        mLiveDataManager.clearAllObservers();
    }

    @SuppressLint("RestrictedApi")
    private void initViewModel() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        if (parameterizedType == null) {
            getActivity().finish();
            throw new IllegalArgumentException("incorrect base model class param");
        } else {
            Type type = parameterizedType.getActualTypeArguments()[0];
            Class<VM> viewModelClass = (Class<VM>) type;
            mViewModel = (new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).create(viewModelClass);
            getLifecycle().addObserver(mViewModel);
        }
    }

    public final void runOnUiThread(Runnable action) {
        if (Thread.currentThread() != mUiThread) {
            mHandler.post(action);
        } else {
            action.run();
        }
    }
}
