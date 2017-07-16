package com.thinhlp.cocshopapp.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.thinhlp.cocshopapp.R;
import com.thinhlp.cocshopapp.entities.OrderDetail;
import com.thinhlp.cocshopapp.entities.OrderDto;
import com.thinhlp.cocshopapp.listeners.OrderListener;

import java.util.List;

/**
 * Created by thinhlp on 7/15/17.
 */

public class HistoryViewHolder extends RecyclerView.ViewHolder {
    public TextView txtOrderId, txtPrice, txtOrderTime, txtViewOrder;
    public OrderListener orderListener;
    public OrderDto order;

    public HistoryViewHolder(View v, OrderListener listener) {
        super(v);
        this.orderListener = listener;
        txtOrderId = (TextView) v.findViewById(R.id.txtOrderId);
        txtPrice = (TextView) v.findViewById(R.id.txtTotalPrice);
        txtOrderTime = (TextView) v.findViewById(R.id.txtOrderTime);
        txtViewOrder = (TextView) v.findViewById(R.id.txtViewOrder);

        txtViewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderListener.viewOrderItem(order);
            }
        });
    }
}
