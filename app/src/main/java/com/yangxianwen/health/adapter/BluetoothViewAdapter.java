package com.yangxianwen.health.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.inuker.bluetooth.library.search.SearchResult;
import com.yangxianwen.health.R;
import com.yangxianwen.health.listener.OnRecycleViewClickListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by timaimee on 2016/7/25.
 */
public class BluetoothViewAdapter extends RecyclerView.Adapter<BluetoothViewAdapter.NormalTextViewHolder> {
    private final LayoutInflater mLayoutInflater;
    List<SearchResult> itemData;
    OnRecycleViewClickListener mBleCallback;

    public BluetoothViewAdapter(Context context) {
        this.itemData = new ArrayList<>();
        mLayoutInflater = LayoutInflater.from(context);
    }

    public BluetoothViewAdapter(Context context, List<SearchResult> data) {
        this.itemData = data;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public NormalTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalTextViewHolder(mLayoutInflater.inflate(R.layout.bluetooth_item, parent, false));
    }

    @Override
    public void onBindViewHolder(NormalTextViewHolder holder, int position) {
        holder.mBleRssi.setText(itemData.get(position).getName() + " - " + itemData.get(position).getAddress());

    }


    @Override
    public int getItemCount() {
        return itemData == null ? 0 : itemData.size();
    }

    public void setBleItemOnclick(OnRecycleViewClickListener bleCallback) {
        this.mBleCallback = bleCallback;
    }


    public class NormalTextViewHolder extends RecyclerView.ViewHolder {

        TextView mBleRssi;


        NormalTextViewHolder(View view) {
            super(view);
            mBleRssi = (TextView) view.findViewById(R.id.tv);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBleCallback.OnRecycleViewClick(getPosition());
                    Log.d("NormalTextViewHolder", "onClick--> position = " + getPosition());
                }
            });
        }
    }
}