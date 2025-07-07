package com.yangxianwen.health.manager;

import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.yangxianwen.health.view.DeviceFragment;
import com.yangxianwen.health.view.HomeFragment;
import com.yangxianwen.health.view.MineFragment;

public class FragManager {

    public static final String TAG = FragManager.class.getName();

    //健康数据
    public static final int PAGE_HOME = 1;
    //设备管理
    public static final int PAGE_DEVICE = 2;
    //个人信息
    public static final int PAGE_MINE = 3;

    private int lastTab = -1;

    private FragManager() {
    }

    private final SparseArray<Fragment> cache = new SparseArray<>();

    public static FragManager getInstance() {
        return FragManager.SingleInternalHolder.instance;
    }

    private static class SingleInternalHolder {
        private static final FragManager instance = new FragManager();
    }

    public void showFrag(@NonNull FragmentActivity host, int containerId, int type) {
        FragmentManager manager = host.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        //通过tag获取FragmentManager中的Fragment
        String fragmentTag = getFragmentTagByType(type);
        Fragment fragment = manager.findFragmentByTag(fragmentTag);
        if (fragment == null || fragment.isDetached()) {
            Fragment newFragment = createFragmentByType(type);
            cache.put(type, newFragment);
            transaction.add(containerId, newFragment, fragmentTag);
        }
        for (int i = 0; i < cache.size(); i++) {
            int key = cache.keyAt(i);
            Fragment target = cache.get(key);
            if (key == type) {
                transaction.show(target);
            } else {
                transaction.hide(target);
            }
        }
        transaction.commitAllowingStateLoss();
        lastTab = type;
    }


    private Fragment createFragmentByType(int type) {
        switch (type) {
            case PAGE_HOME:
                return HomeFragment.newInstance();
            case PAGE_DEVICE:
                return DeviceFragment.newInstance();
            case PAGE_MINE:
                return MineFragment.newInstance();
            default:
                throw new NullPointerException();
        }
    }

    private String getFragmentTagByType(int type) {
        switch (type) {
            case PAGE_HOME:
                return HomeFragment.class.getName();
            case PAGE_DEVICE:
                return DeviceFragment.class.getName();
            case PAGE_MINE:
                return MineFragment.class.getName();
            default:
                throw new NullPointerException();
        }
    }

    public int getLastTab() {
        return lastTab;
    }
}
