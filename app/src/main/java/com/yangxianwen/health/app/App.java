package com.yangxianwen.health.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.veepoo.protocol.VPOperateManager;
import com.yangxianwen.health.util.display.DisplayUtil;

public class App extends Application implements Application.ActivityLifecycleCallbacks {

    private static Application ins;

    public static Application getIns() {
        return ins;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ins = this;
        registerActivityLifecycleCallbacks(this);
        VPOperateManager.getInstance().init(getApplicationContext());
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        //屏幕适配
        DisplayUtil.setCustomDensity(activity, this);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }
}
