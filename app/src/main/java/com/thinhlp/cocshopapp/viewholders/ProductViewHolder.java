package com.thinhlp.cocshopapp.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thinhlp.cocshopapp.R;
import com.thinhlp.cocshopapp.entities.Product;

/**
 * Created by thinhlp on 7/7/17.
 */

public class ProductViewHolder extends RecyclerView.ViewHolder {
    public TextView productName, description, price;
    public ImageView productImg, addToCartImg;
    public Product product;

    public ProductViewHolder(View view) {
        super(view);
        productName = (TextView) view.findViewById(R.id.txtProductName);
        description = (TextView) view.findViewById(R.id.txtDescription);
        price = (TextView) view.findViewById(R.id.txtPrice);
        productImg = (ImageView) view.findViewById(R.id.imgProduct);
        addToCartImg = (ImageView) view.findViewById(R.id.btnAddToCart);
        addToCartImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //System.out.println(v.getId());
            }
        });

    }
}
