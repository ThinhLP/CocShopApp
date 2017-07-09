package com.thinhlp.cocshopapp.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thinhlp.cocshopapp.R;
import com.thinhlp.cocshopapp.entities.CartItem;
import com.thinhlp.cocshopapp.entities.Product;

/**
 * Created by thinhlp on 7/9/17.
 */

public class CartViewHolder extends RecyclerView.ViewHolder  {
    public TextView itemName, itemPrice, itemQuantity;
    public ImageView itemImg;
    public CartItem cartItem;
    public Context context;

    public CartViewHolder(Context context, View v) {
        super(v);
        this.context = context;
        itemImg = (ImageView) v.findViewById(R.id.imgItem);
        itemName = (TextView) v.findViewById(R.id.txtItemName);
        itemPrice = (TextView) v.findViewById(R.id.txtItemPrice);
        itemQuantity = (TextView) v.findViewById(R.id.txtItemQuantity);

    }
}
