package com.thinhlp.cocshopapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.thinhlp.cocshopapp.R;
import com.thinhlp.cocshopapp.adapters.ProductAdapter;
import com.thinhlp.cocshopapp.commons.ApiUtils;
import com.thinhlp.cocshopapp.commons.Const;
import com.thinhlp.cocshopapp.entities.Product;
import com.thinhlp.cocshopapp.services.ProductService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
* Create by thinhlp
* */
public class ProductFragment extends Fragment {
    private List<Product> productList;
    private RecyclerView recyclerView;
    private ProductAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProductService productService;

    public static ProductFragment newInstance() {
        ProductFragment fragment = new ProductFragment();
        return fragment;
    }

    public ProductFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getProductList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_product, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvProduct);
        return rootView;
    }

    public void getProductList() {
        productService = ApiUtils.getProductService();

        productService.listProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                int statusCode = response.code();
                switch (statusCode) {
                    case Const.HTTP_STATUS.OK:
                        productList = response.body();
                        break;
                    case Const.HTTP_STATUS.NO_CONTENT:
                        // To-do
                        break;
                }
                mLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(mLayoutManager);
                mAdapter = new ProductAdapter(getActivity(), productList);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(getActivity(), "Can't connect to server. Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
