package com.yangxianwen.health.base;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.inuker.bluetooth.library.utils.BluetoothUtils;
import com.orhanobut.logger.Logger;

public class BaseActivity extends AppCompatActivity {

    protected final String TAG = getClass().getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();
    }

    private void checkPermission() {
        Logger.t(TAG).i("Build.VERSION.SDK_INT =" + Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT <= 22) {
            openBluetooth();
            return;
        }

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Logger.t(TAG).i("checkPermission,PERMISSION_GRANTED");
            openBluetooth();
        } else if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            Logger.t(TAG).i("checkPermission,PERMISSION_DENIED");
            requestPermission();
        }
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                Logger.t(TAG).i("requestPermission,shouldShowRequestPermissionRationale");
            } else {
                Logger.t(TAG).i("requestPermission,shouldShowRequestPermissionRationale else");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.BLUETOOTH_PRIVILEGED},
                        0);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Logger.t(TAG).i("onRequestPermissionsResult,MY_PERMISSIONS_REQUEST_BLUETOOTH ");
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openBluetooth();
        }
    }

    private void openBluetooth() {
        if (!BluetoothUtils.isBluetoothEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBtIntent);
        }
    }

    protected void showToast(String s) {
        if (s == null) {
            s = "null";
        }
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    protected void showLongToast(String s) {
        if (s == null) {
            s = "null";
        }
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    protected AppCompatActivity getActivity() {
        return this;
    }
}
