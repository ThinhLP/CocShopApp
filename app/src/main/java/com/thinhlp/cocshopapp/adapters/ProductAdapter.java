package com.thinhlp.cocshopapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.squareup.picasso.Picasso;
import com.thinhlp.cocshopapp.R;
import com.thinhlp.cocshopapp.entities.Product;
import com.thinhlp.cocshopapp.filters.ProductFilter;
import com.thinhlp.cocshopapp.viewholders.ProductViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thinhlp on 7/7/17.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> implements Filterable {
    public List<Product> productList = new ArrayList<>(), filterList;
    private Context context;
    private ProductFilter productFilter;

    public ProductAdapter(Context context, List<Product> productList) {
        this.productList = productList;
        this.context = context;
        this.filterList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_cart_view, parent, false);

        return new ProductViewHolder(context, itemView);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.description.setText(product.getDescription() == null ? "" : product.getDescription());
        holder.productName.setText(product.getProductName());
        holder.price.setText(product.getPrice() + "đ");
        holder.product = product;
        String imageUrl = product.getImageUrl();
        if (imageUrl != null) {
            Picasso.with(context).load(imageUrl).into(holder.productImg);
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    @Override
    public Filter getFilter() {
        if (productFilter == null) {
            productFilter = new ProductFilter(this, filterList);
        }
        return productFilter;
    }
}
