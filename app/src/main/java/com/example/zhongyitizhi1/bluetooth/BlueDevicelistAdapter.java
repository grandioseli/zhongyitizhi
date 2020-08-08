package com.example.zhongyitizhi1.bluetooth;//package com.example.zhongyitizhi1.bluetooth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inuker.bluetooth.library.search.SearchResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;


public class BlueDevicelistAdapter extends RecyclerView.Adapter<BlueDeviceHolder> implements Comparator<SearchResult> {
    private Context mContext;
    private ArrayList<SearchResult> mBlueDeviceslist;

    public BlueDevicelistAdapter(Context context){
        mContext = context;
        mBlueDeviceslist = new ArrayList<SearchResult>();
    }

    public void setBlueDeviceslist(Set<SearchResult> blueDevices) {
        mBlueDeviceslist.clear();
        mBlueDeviceslist.addAll(blueDevices);
        Collections.sort(mBlueDeviceslist, this);
        notifyDataSetChanged();
    }

    @Override
    public BlueDeviceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        return new BlueDeviceHolder(layoutInflater,parent,mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull BlueDeviceHolder holder, int position) {
        SearchResult blueDevice = mBlueDeviceslist.get(position);
        holder.bind(blueDevice);
    }
    private static class ViewHolder {
        TextView name;
        TextView mac;
        TextView rssi;
        TextView adv;
    }
    @Override
    public int compare(SearchResult lhs, SearchResult rhs) {
        return rhs.rssi - lhs.rssi;
    }
    @Override
    public int getItemCount() {
        return mBlueDeviceslist.size();
    }
}