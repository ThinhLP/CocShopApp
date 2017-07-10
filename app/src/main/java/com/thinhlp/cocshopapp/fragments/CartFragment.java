package com.thinhlp.cocshopapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thinhlp.cocshopapp.R;
import com.thinhlp.cocshopapp.adapters.CartAdapter;
import com.thinhlp.cocshopapp.adapters.ProductAdapter;
import com.thinhlp.cocshopapp.entities.CartItem;
import com.thinhlp.cocshopapp.listeners.CartListener;
import com.thinhlp.cocshopapp.services.CartService;

import java.util.List;

public class CartFragment extends Fragment {
    public List<CartItem> items;
    private RecyclerView recyclerView;
    private CartAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView txtTotal;
    private CartListener cartListener;
    public long total = 0;

    public static CartFragment newInstance() {
        CartFragment fragment = new CartFragment();
        return fragment;
    }

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cart, container, false);
        getCart();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvCart);
        txtTotal = (TextView) rootView.findViewById(R.id.txtTotal);

        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        cartListener = new CartListener() {
            @Override
            public void updateQuantity(int id, int quantity) {
                CartService cartService = new CartService(getContext());
                boolean isUpdated = cartService.updateQuantity(id, quantity);
                if (isUpdated) {
                    getCart();
                    int itemUpdatedPos = findPositionInList(id);
                    if (itemUpdatedPos >= 0) {
                        recyclerView.swapAdapter(new CartAdapter(getActivity(), items, cartListener), false);
                        // mAdapter.updateItemInCart(i, items);
                        calculateMoney();
                    }
                }
            }

            @Override
            public void deleteCartItem(int id) {
                CartService cartService = new CartService(getContext());
                int itemDeletedPos = findPositionInList(id);
                boolean isDeleted = cartService.deleteItem(id);
                if (isDeleted) {
                    getCart();
                    if (itemDeletedPos >= 0) {
                        recyclerView.swapAdapter(new CartAdapter(getActivity(), items, cartListener), false);
                        calculateMoney();
                    }

                }
            }
        };

        mAdapter = new CartAdapter(getActivity(), items, cartListener);
        recyclerView.setAdapter(mAdapter);
        calculateMoney();
        return rootView;
    }

    public void getCart() {
        CartService cartService = new CartService(getActivity());
        items = cartService.getCart();
    }

    public void calculateMoney() {
        total = 0;
        if (items != null && !items.isEmpty()) {
            for (CartItem item: items) {
                total += item.getPrice() * item.getQuantity();
            }
        }
        txtTotal.setText(total + " đ");
    }

    private int findPositionInList(int id) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }


//    @Override
//    public void updateQuantity(int id, int quantity) {
//        CartService cartService = new CartService(getContext());
//        cartService.updateQuantity(id, quantity);
//        // Refresh cart
//        getCart();
//        int positionChanged = 0;
//        for (int i = 0; i < items.size(); i++) {
//            if (items.get(i).getId() == id) {
//                positionChanged = i;
//                break;
//            }
//        }
//        mAdapter.notifyItemChanged(positionChanged);
//        calculateMoney();
//    }
}
