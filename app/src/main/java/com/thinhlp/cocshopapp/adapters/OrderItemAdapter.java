package com.thinhlp.cocshopapp.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.thinhlp.cocshopapp.R;
import com.thinhlp.cocshopapp.entities.OrderDetail;
import com.thinhlp.cocshopapp.entities.Product;
import com.thinhlp.cocshopapp.viewholders.OrderItemViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thinhlp on 7/16/17.
 */

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemViewHolder> {
    private List<OrderDetail> orderDetails = new ArrayList<>();
    private Context context;

    public OrderItemAdapter(Context context, List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
        this.context = context;
    }

    @Override
    public OrderItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_detail, parent, false);
        return new OrderItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrderItemViewHolder holder, int position) {
        OrderDetail orderDetail = orderDetails.get(position);
        Product product = orderDetail.getProduct();
        holder.itemName.setText(product.getProductName());
        holder.itemPrice.setText(product.getPrice() + "đ");
        holder.itemQuantity.setText(orderDetail.getQuantity() + "");
        String imageUrl = product.getImageUrl();
        if (imageUrl != null) {
            Picasso.with(context).load(imageUrl).into(holder.itemImg);
        }
    }

    @Override
    public int getItemCount() {
        return orderDetails.size();
    }
}
