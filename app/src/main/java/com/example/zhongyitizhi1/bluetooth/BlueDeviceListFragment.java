package com.example.zhongyitizhi1.bluetooth;//package com.example.zhongyitizhi1.bluetooth;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zhongyitizhi1.R;
import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;

import java.util.HashSet;
import java.util.Set;

public class BlueDeviceListFragment extends Fragment {
    private TextView mblueDevicesList;
    private RecyclerView mBlueDeviceRecyclerView;
    private BlueDevicelistAdapter mAdapter;
    private Set<SearchResult> mblueDevices;
    private String mBlueToothConnectedMac = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bluedevice_list, container, false);

        Intent intent = new Intent(getActivity(),MyBleService.class);
        getActivity().startService(intent);

        mblueDevicesList = (TextView) view.findViewById(R.id.bluetoothDeviceList);
        mBlueDeviceRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_device_list);
        mBlueDeviceRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new BlueDevicelistAdapter(getActivity());
        mBlueDeviceRecyclerView.setAdapter(mAdapter);
        return view;
    }
    public void searchDevice() {
        SearchRequest request = new SearchRequest.Builder()
                .searchBluetoothLeDevice(3000, 3)   // 先扫BLE设备3次，每次3s
                .build();
        ClientManager.getClient().search(request, mSearchResponse);
    }

    private final SearchResponse mSearchResponse = new SearchResponse() {
        @Override
        public void onSearchStarted() {
            mblueDevices = new HashSet<SearchResult>();
            mblueDevices.clear();
            Toast.makeText(getActivity(), "开始扫描", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onDeviceFounded(SearchResult device) {
            String deviceName = device.getName();
            if(deviceName == "NULL")
                return;
            mblueDevices.add(device);
            mAdapter.setBlueDeviceslist(mblueDevices);
        }
        @Override
        public void onSearchStopped() {
            ClientManager.getClient().stopSearch();
        }
        @Override
        public void onSearchCanceled() {
        }
    };
    @Override
    public void onResume(){
        super.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        ClientManager.getClient().stopSearch();
    }
}


