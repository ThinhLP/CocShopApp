package com.thinhlp.cocshopapp.viewholders;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.thinhlp.cocshopapp.R;
import com.thinhlp.cocshopapp.entities.CartItem;
import com.thinhlp.cocshopapp.entities.Product;
import com.thinhlp.cocshopapp.fragments.CartFragment;
import com.thinhlp.cocshopapp.listeners.CartListener;
import com.thinhlp.cocshopapp.services.CartService;

/**
 * Created by thinhlp on 7/9/17.
 */

public class CartViewHolder extends RecyclerView.ViewHolder  {
    public TextView itemName, itemPrice, itemQuantity;
    public ImageView itemImg, imgPlus, imgSub, imgRemove;
    public CartItem cartItem;
    private Context context;
    private CartListener cartListener;

    public CartViewHolder(Context context, View v, CartListener cartListener) {
        super(v);
        this.context = context;
        this.cartListener = cartListener;
        itemImg = (ImageView) v.findViewById(R.id.imgItem);
        itemName = (TextView) v.findViewById(R.id.txtItemName);
        itemPrice = (TextView) v.findViewById(R.id.txtItemPrice);
        itemQuantity = (TextView) v.findViewById(R.id.txtItemQuantity);
        imgPlus = (ImageView) v.findViewById(R.id.imgPlus);
        imgSub = (ImageView) v.findViewById(R.id.imgSubstract);
        imgRemove = (ImageView) v.findViewById(R.id.imgRemove);

        // Add on click listener
        imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseQuantity();
            }
        });
        imgSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseQuantity();
            }
        });
        imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem();
            }
        });

    }

    public void increaseQuantity() {
        int currentQuantity = cartItem.getQuantity();
        if (currentQuantity == cartItem.getProductInStock()) {
            Toast.makeText(context, cartItem.getProductName() + " has only " + cartItem.getProductInStock() + " left(s)", Toast.LENGTH_SHORT).show();
            return;
        }
        currentQuantity++;
        cartListener.updateQuantity(cartItem.getId(), currentQuantity);
    }


    public void decreaseQuantity() {
        int currentQuantity = cartItem.getQuantity();
        if (currentQuantity == 1) {
            return;
        }
        currentQuantity--;
        cartListener.updateQuantity(cartItem.getId(), currentQuantity);
    }

    public void removeItem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirmation")
                .setMessage("Do you want to remove this product from cart?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cartListener.deleteCartItem(cartItem.getId());
                    }
                })
                .setNegativeButton("Cancel", null);
        builder.show();
    }
}
