package com.thinhlp.cocshopapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.thinhlp.cocshopapp.R;
import com.thinhlp.cocshopapp.entities.CartItem;
import com.thinhlp.cocshopapp.fragments.CartFragment;
import com.thinhlp.cocshopapp.listeners.CartListener;
import com.thinhlp.cocshopapp.viewholders.CartViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thinhlp on 7/9/17.
 */

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {
    private List<CartItem> items = new ArrayList<>();
    private Context context;
    private CartListener cartListener;

    public CartAdapter(Context context, List<CartItem> items, CartListener cartListener) {
        this.items = items;
        this.context = context;
        this.cartListener = cartListener;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item_view, parent, false);

        return new CartViewHolder(context, itemView, cartListener);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        CartItem item = items.get(position);
        holder.itemName.setText(item.getProductName());
        holder.itemPrice.setText(item.getPrice() + "đ");
        holder.itemQuantity.setText(item.getQuantity() + "");
        holder.cartItem = item;
        String imageUrl = item.getImageUrl();
        if (imageUrl != null) {
            Picasso.with(context).load(imageUrl).into(holder.itemImg);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateItemInCart(int position, List<CartItem> items) {
        this.items = items;
        notifyItemChanged(position);
    }
}
