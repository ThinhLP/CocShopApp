package com.thinhlp.cocshopapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.thinhlp.cocshopapp.R;
import com.thinhlp.cocshopapp.adapters.OrderItemAdapter;
import com.thinhlp.cocshopapp.commons.Const;
import com.thinhlp.cocshopapp.commons.Utils;
import com.thinhlp.cocshopapp.entities.OrderDetail;
import com.thinhlp.cocshopapp.entities.OrderDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderViewActivity extends AppCompatActivity {
    private OrderDto order;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private OrderItemAdapter mAdapter;
    private TextView txtOrderId, txtTotal, txtOrderDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_view);
        Intent i = getIntent();
        String json = i.getStringExtra(Const.INTENT_EXTRA.ORDER_JSON);
        Gson gson = new Gson();
        order = gson.fromJson(json, OrderDto.class);

        txtOrderId = (TextView) findViewById(R.id.txtOrderId);
        txtOrderDate = (TextView) findViewById(R.id.txtOrderDate);
        txtTotal = (TextView) findViewById(R.id.txtTotal);
        recyclerView = (RecyclerView) findViewById(R.id.rvOrderDetailItems);

        loadData();
    }

    private void loadData() {
        txtOrderId.setText(order.getOrderId() + "");
        txtOrderDate.setText(order.getOrderDate());
        txtTotal.setText(Utils.computeTotalOfOrderDetails(order.getOrderDetails()) + "đ");

        // Load data to recycle view

        mLayoutManager = new LinearLayoutManager(getBaseContext());
        mAdapter = new OrderItemAdapter(getBaseContext(), order.getOrderDetails());

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

    }
}
