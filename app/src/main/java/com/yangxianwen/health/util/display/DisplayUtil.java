package com.yangxianwen.health.util.display;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

public class DisplayUtil {

    //基础dp 界面的宽度最大为360dp
    private static final float baseDp = 360f;

    private static float sNonCompatDensity;
    private static float sNonCompatScaledDensity;

    /**
     * 修改设备密度
     */
    public static void setCustomDensity(Activity activity, final Application application) {
        if (activity == null || application == null) return;

        DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();
        if (sNonCompatDensity == 0) {
            sNonCompatDensity = appDisplayMetrics.density;
            sNonCompatScaledDensity = appDisplayMetrics.scaledDensity;
            // 防止系统切换后不起作用
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(@NonNull Configuration newConfig) {
                    if (newConfig.fontScale > 0) {
                        sNonCompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }
        float targetDensity = appDisplayMetrics.widthPixels / baseDp;
        // 防止字体变小
        float targetScaleDensity = targetDensity * (sNonCompatScaledDensity / sNonCompatDensity);
        int targetDensityDpi = (int) (160 * targetDensity);

        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaleDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaleDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }
}
