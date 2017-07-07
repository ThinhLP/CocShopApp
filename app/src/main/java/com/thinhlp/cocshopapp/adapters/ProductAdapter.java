package com.thinhlp.cocshopapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thinhlp.cocshopapp.R;
import com.thinhlp.cocshopapp.entities.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thinhlp on 7/7/17.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList = new ArrayList<>();
    private Context context;

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        public TextView productName, description, price;
        public ImageView productImg;

        public ProductViewHolder(View view) {
            super(view);
            productName = (TextView) view.findViewById(R.id.txtProductName);
            description = (TextView) view.findViewById(R.id.txtDescription);
            price = (TextView) view.findViewById(R.id.txtPrice);
            productImg = (ImageView) view.findViewById(R.id.imgProduct);
        }
    }

    public ProductAdapter(Context context, List<Product> productList) {
        this.productList = productList;
        this.context = context;
    }

    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_cart_view, parent, false);

        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductAdapter.ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.description.setText(product.getDescription() == null ? "" : product.getDescription());
        holder.productName.setText(product.getProductName());
        holder.price.setText(product.getPrice() + "đ");
        String imageUrl = product.getImageUrl();
        if (imageUrl != null) {
            Picasso.with(context).load(imageUrl).into(holder.productImg);
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
