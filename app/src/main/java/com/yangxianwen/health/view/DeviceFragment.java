package com.yangxianwen.health.view;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.inuker.bluetooth.library.Code;
import com.inuker.bluetooth.library.Constants;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;
import com.orhanobut.logger.Logger;
import com.veepoo.protocol.VPOperateManager;
import com.veepoo.protocol.listener.base.IABleConnectStatusListener;
import com.yangxianwen.health.R;
import com.yangxianwen.health.adapter.BluetoothViewAdapter;
import com.yangxianwen.health.base.BaseMvvmFragment;
import com.yangxianwen.health.databinding.DeviceFragBinding;
import com.yangxianwen.health.viewmodel.DeviceViewModel;

import java.util.ArrayList;
import java.util.List;

public class DeviceFragment extends BaseMvvmFragment<DeviceViewModel, DeviceFragBinding> {

    private BluetoothViewAdapter saveAdapter;
    private BluetoothViewAdapter searchAdapter;
    private final List<SearchResult> mSearchResults = new ArrayList<>();
    private final List<String> mSearchAddress = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.device_frag;
    }

    public static DeviceFragment newInstance() {
        return new DeviceFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        saveAdapter = new BluetoothViewAdapter(getContext());
        saveAdapter.setBleItemOnclick(position -> {

        });
        mBinding.saveBluetoothList.setAdapter(saveAdapter);

        searchAdapter = new BluetoothViewAdapter(getContext(), mSearchResults);
        searchAdapter.setBleItemOnclick(position -> {
            mBinding.bluetoothStatus.setClickable(false);
            mBinding.bluetoothStatus.setTag("connect");
            mBinding.bluetoothStatus.setText("正在连接设备...");

            VPOperateManager.getInstance().stopScanDevice();

            connectDevice(mSearchResults.get(position).getAddress(), mSearchResults.get(position).getName());
        });
        mBinding.searchBluetoothList.setAdapter(searchAdapter);

        mBinding.naviBar.back.setOnClickListener(v -> finish());
        mBinding.bluetoothStatus.setOnClickListener(v -> VPOperateManager.getInstance().startScanDevice(mSearchResponse));
    }

    private final SearchResponse mSearchResponse = new SearchResponse() {
        @Override
        public void onSearchStarted() {
            Logger.t(TAG).i("onSearchStarted");
            mBinding.bluetoothStatus.setClickable(false);
            mBinding.bluetoothStatus.setTag("search");
            mBinding.bluetoothStatus.setText("正在搜索附近设备...");
        }

        @Override
        public void onDeviceFounded(final SearchResult device) {
            Logger.t(TAG).i(String.format("device for %s-%s-%s", device.getName(), device.getAddress(), device.rssi));
            runOnUiThread(() -> {
                if (!mSearchAddress.contains(device.getAddress())) {
                    mSearchResults.add(device);
                    mSearchAddress.add(device.getAddress());
                }
                searchAdapter.notifyDataSetChanged();
            });
        }

        @Override
        public void onSearchStopped() {
            Logger.t(TAG).i("onSearchStopped");
            if ("connect".equals(mBinding.bluetoothStatus.getTag())) {
                return;
            }
            runOnUiThread(() -> {
                mBinding.bluetoothStatus.setClickable(true);
                mBinding.bluetoothStatus.setTag("stop");
                mBinding.bluetoothStatus.setText("重新搜索");
            });
        }

        @Override
        public void onSearchCanceled() {
            Logger.t(TAG).i("onSearchCanceled");
            if ("connect".equals(mBinding.bluetoothStatus.getTag())) {
                return;
            }
            runOnUiThread(() -> {
                mBinding.bluetoothStatus.setClickable(true);
                mBinding.bluetoothStatus.setTag("stop");
                mBinding.bluetoothStatus.setText("重新搜索");
            });
        }
    };

    private void connectDevice(final String mac, final String deviceName) {
        VPOperateManager.getInstance().registerConnectStatusListener(mac, new IABleConnectStatusListener() {
            @Override
            public void onConnectStatusChanged(String mac, int status) {
                if (status == Constants.STATUS_CONNECTED) {
                    Logger.t(TAG).i("STATUS_CONNECTED");
                } else if (status == Constants.STATUS_DISCONNECTED) {
                    Logger.t(TAG).i("STATUS_DISCONNECTED");
                }
            }
        });

        VPOperateManager.getInstance().connectDevice(mac, deviceName, (code, profile, isoadModel) -> {
            if (code == Code.REQUEST_SUCCESS) {
                //蓝牙与设备的连接状态
                Logger.t(TAG).i("连接成功");
                runOnUiThread(() -> {
                    mBinding.bluetoothStatus.setClickable(true);
                    mBinding.bluetoothStatus.setTag("connected");
                    mBinding.bluetoothStatus.setText("设备已连接，点击重新搜索设备");
                });
            } else {
                Logger.t(TAG).i("连接失败");
                runOnUiThread(() -> {
                    mBinding.bluetoothStatus.setClickable(true);
                    mBinding.bluetoothStatus.setTag("connect_fail");
                    mBinding.bluetoothStatus.setText("连接失败，点击重新搜索设备");
                });
            }
        }, state -> {
            if (state == Code.REQUEST_SUCCESS) {
                //蓝牙与设备的连接状态
                Logger.t(TAG).i("监听成功-可进行其他操作");
                runOnUiThread(() -> {
                    mBinding.bluetoothStatus.setClickable(true);
                    mBinding.bluetoothStatus.setTag("callback");
                    mBinding.bluetoothStatus.setText("设备已监听，点击重新搜索设备");
                });
            } else {
                Logger.t(TAG).i("监听失败，重新连接");
                runOnUiThread(() -> {
                    mBinding.bluetoothStatus.setClickable(true);
                    mBinding.bluetoothStatus.setTag("callback_fail");
                    mBinding.bluetoothStatus.setText("监听失败，点击重新搜索设备");
                });
            }
        });
    }
}