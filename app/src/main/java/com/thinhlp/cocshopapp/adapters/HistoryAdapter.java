package com.thinhlp.cocshopapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.thinhlp.cocshopapp.R;
import com.thinhlp.cocshopapp.activities.OrderViewActivity;
import com.thinhlp.cocshopapp.commons.Const;
import com.thinhlp.cocshopapp.commons.Utils;
import com.thinhlp.cocshopapp.entities.OrderDetail;
import com.thinhlp.cocshopapp.entities.OrderDto;
import com.thinhlp.cocshopapp.listeners.OrderListener;
import com.thinhlp.cocshopapp.viewholders.HistoryViewHolder;

import java.util.List;

/**
 * Created by thinhlp on 7/15/17.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryViewHolder> implements OrderListener{
    private List<OrderDto> orderList;
    private Context context;

    public HistoryAdapter(Context context, List<OrderDto> orders) {
        orderList = orders;
        this.context = context;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View historyView = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_cart_view, parent, false);
        return new HistoryViewHolder(historyView, this);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        OrderDto order = orderList.get(position);
        holder.order = order;
        holder.txtOrderId.setText(order.getOrderId() + "");
        holder.txtOrderTime.setText(order.getOrderDate());
        int total = Utils.computeTotalOfOrderDetails(order.getOrderDetails());
        holder.txtPrice.setText(total + "đ");
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    // Implement OrderListener
    @Override
    public void viewOrderItem(OrderDto order) {
        Intent i = new Intent(context, OrderViewActivity.class);
        Gson gson = new Gson();
        i.putExtra(Const.INTENT_EXTRA.ORDER_JSON, gson.toJson(order));
        context.startActivity(i);
    }
}
