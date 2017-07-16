package com.thinhlp.cocshopapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Toast;

import com.thinhlp.cocshopapp.R;
import com.thinhlp.cocshopapp.adapters.HistoryAdapter;
import com.thinhlp.cocshopapp.adapters.ProductAdapter;
import com.thinhlp.cocshopapp.commons.ApiUtils;
import com.thinhlp.cocshopapp.commons.Const;
import com.thinhlp.cocshopapp.commons.Utils;
import com.thinhlp.cocshopapp.entities.OrderDto;
import com.thinhlp.cocshopapp.services.OrderService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryFragment extends Fragment {
    private List<OrderDto> orderList;
    private RecyclerView rvHistory;
    private RecyclerView.LayoutManager mLayoutManager;
    private HistoryAdapter adapter;


    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        return fragment;
    }

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getOrders();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);
        rvHistory = (RecyclerView) rootView.findViewById(R.id.rvHistory);

        return rootView;
    }

    public void getOrders() {
        OrderService orderService = ApiUtils.getOrderService();
        int customerId = Utils.getCurrentUserId(getContext());
        orderService.getOrders(customerId).enqueue(new Callback<List<OrderDto>>() {
            @Override
            public void onResponse(Call<List<OrderDto>> call, Response<List<OrderDto>> response) {
                int statusCode = response.code();
                switch (statusCode) {
                    case Const.HTTP_STATUS.OK:
                        orderList = response.body();
                        mLayoutManager = new LinearLayoutManager(getActivity());
                        rvHistory.setLayoutManager(mLayoutManager);
                        adapter = new HistoryAdapter(getContext(), orderList);
                        rvHistory.setAdapter(adapter);
                        break;
                    default:
                        // TO DO
                }

            }

            @Override
            public void onFailure(Call<List<OrderDto>> call, Throwable t) {
                Toast.makeText(getContext(), "Can't connect to server", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
