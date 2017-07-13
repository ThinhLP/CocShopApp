package com.thinhlp.cocshopapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QRFragment extends Fragment {

    private RecyclerView.LayoutManager mLayoutManager;
    public static QRFragment newInstance() {
        QRFragment fragment = new QRFragment();
        return fragment;
    }

    public QRFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_qr, container, false);
        return rootView;
    }




}
