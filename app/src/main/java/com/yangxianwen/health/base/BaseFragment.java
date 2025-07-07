package com.yangxianwen.health.base;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

    protected final String TAG = getClass().getName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void showToast(String s) {
        if (s == null) {
            s = "null";
        }
        Toast.makeText(getContext().getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    protected void showLongToast(String s) {
        if (s == null) {
            s = "null";
        }
        Toast.makeText(getContext().getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    protected void finish() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }
}
