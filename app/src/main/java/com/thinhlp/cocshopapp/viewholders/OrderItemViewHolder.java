package com.thinhlp.cocshopapp.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thinhlp.cocshopapp.R;

/**
 * Created by thinhlp on 7/16/17.
 */

public class OrderItemViewHolder extends RecyclerView.ViewHolder  {
    public TextView itemName, itemPrice, itemQuantity;
    public ImageView itemImg;

    public OrderItemViewHolder(View v) {
        super(v);
        itemImg = (ImageView) v.findViewById(R.id.imgItem);
        itemName = (TextView) v.findViewById(R.id.txtItemName);
        itemPrice = (TextView) v.findViewById(R.id.txtItemPrice);
        itemQuantity = (TextView) v.findViewById(R.id.txtItemQuantity);
    }
}
