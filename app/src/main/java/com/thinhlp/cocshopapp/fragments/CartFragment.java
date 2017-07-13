package com.thinhlp.cocshopapp.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.thinhlp.cocshopapp.activities.CustomerCheckoutActivity;
import com.thinhlp.cocshopapp.R;
import com.thinhlp.cocshopapp.adapters.CartAdapter;
import com.thinhlp.cocshopapp.commons.ApiUtils;
import com.thinhlp.cocshopapp.commons.Const;
import com.thinhlp.cocshopapp.entities.CartItem;
import com.thinhlp.cocshopapp.entities.Order;
import com.thinhlp.cocshopapp.listeners.CartListener;
import com.thinhlp.cocshopapp.services.CartService;
import com.thinhlp.cocshopapp.services.OrderService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment implements CartListener {
    public List<CartItem> items;
    private RecyclerView recyclerView;
    private CartAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView txtTotal;
    public long total = 0;
    private Button btnCheckout;
    private CartService cartService;

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
        cartService = new CartService(getContext());
        getCart();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvCart);
        txtTotal = (TextView) rootView.findViewById(R.id.txtTotal);
        btnCheckout = (Button) rootView.findViewById(R.id.btnCheckout);

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickToCheckout();
            }
        });

        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new CartAdapter(getActivity(), items, this);
        recyclerView.setAdapter(mAdapter);
        calculateMoney();
        return rootView;
    }

    public void getCart() {
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

    @Override
    public void updateQuantity(int id, int quantity) {
        boolean isUpdated = cartService.updateQuantity(id, quantity);
        if (isUpdated) {
            getCart();
            int itemUpdatedPos = findPositionInList(id);
            if (itemUpdatedPos >= 0) {
                recyclerView.swapAdapter(new CartAdapter(getActivity(), items, this), false);
                // mAdapter.updateItemInCart(i, items);
                calculateMoney();
            }
        }
    }

    @Override
    public void deleteCartItem(int id) {
        int itemDeletedPos = findPositionInList(id);
        boolean isDeleted = cartService.deleteItem(id);
        if (isDeleted) {
            getCart();
            if (itemDeletedPos >= 0) {
                recyclerView.swapAdapter(new CartAdapter(getActivity(), items, this), false);
                calculateMoney();
            }

        }
    }

    public void clickToCheckout() {
        if (items.isEmpty()) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Confirmation")
                .setMessage("Do you want to check out?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resetCart();
                        toQRCodeActivity();
                    }
                })
                .setNegativeButton("Cancel", null);
        builder.show();
    }

    public void toQRCodeActivity() {
        Intent intent = new Intent(getContext(), CustomerCheckoutActivity.class);
        Gson gson = new Gson();
        String cartJson = gson.toJson(items);
        intent.putExtra(Const.INTENT_EXTRA.CART_JSON, cartJson);
        startActivity(intent);
    }


    public void checkout() {
        OrderService orderService = ApiUtils.getOrderService();
        Order order = cartService.convertCurrentCartToOrder();

        final ProgressDialog dialog = ProgressDialog.show(getContext(), "Loading", "Please wait...", true);
        orderService.checkout("application/json", order).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int statusCode = response.code();
                dialog.dismiss();
                switch (statusCode) {
                    case Const.HTTP_STATUS.OK:
                        Toast.makeText(getActivity(), "Checkout successfully!", Toast.LENGTH_SHORT).show();
                        resetCart();
                        break;
                    case Const.HTTP_STATUS.BAD_REQUEST:
                        Toast.makeText(getActivity(), "Checkout failed. Please try again!", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getActivity(), "Can't connect to server. Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void resetCart() {
        cartService.deleteAllItem();
        getCart();
        recyclerView.swapAdapter(new CartAdapter(getActivity(), items, this), false);
        calculateMoney();
    }
}
