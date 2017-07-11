package com.thinhlp.cocshopapp.filters;

import android.widget.Filter;

import com.thinhlp.cocshopapp.adapters.ProductAdapter;
import com.thinhlp.cocshopapp.entities.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thinhlp on 7/11/17.
 */

public class ProductFilter extends Filter {
    ProductAdapter adapter;
    List<Product> filterList;

    public ProductFilter(ProductAdapter adapter, List<Product> filterList) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        // Check constraint validity
        if (constraint != null && constraint.length() > 0) {
            //CHANGE TO UPPER
            constraint = constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<Product> filterProduct = new ArrayList<>();
            for (int i = 0; i < filterList.size(); i++) {
                //CHECK
                if (filterList.get(i).getProductName().toUpperCase().contains(constraint)) {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filterProduct.add(filterList.get(i));
                }
            }
            results.count = filterProduct.size();
            results.values = filterProduct;
        } else {
            results.count = filterList.size();
            results.values = filterList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.productList = (ArrayList<Product>) results.values;
        adapter.notifyDataSetChanged();
    }
}
