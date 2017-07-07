package com.thinhlp.cocshopapp.viewholders;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.thinhlp.cocshopapp.R;
import com.thinhlp.cocshopapp.entities.Product;
import com.thinhlp.cocshopapp.services.CartService;

/**
 * Created by thinhlp on 7/7/17.
 */

public class ProductViewHolder extends RecyclerView.ViewHolder {
    public TextView productName, description, price;
    public ImageView productImg, addToCartImg;
    public Product product;
    public Context context;

    public ProductViewHolder(Context context, View view) {
        super(view);
        this.context = context;
        productName = (TextView) view.findViewById(R.id.txtProductName);
        description = (TextView) view.findViewById(R.id.txtDescription);
        price = (TextView) view.findViewById(R.id.txtPrice);
        productImg = (ImageView) view.findViewById(R.id.imgProduct);
        addToCartImg = (ImageView) view.findViewById(R.id.btnAddToCart);
        addToCartImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();
            }
        });
    }

    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Input quantity");

        // Set up the input
        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setText("1");
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String quantityStr = input.getText().toString();
                if (quantityStr.isEmpty()) {
                    Toast.makeText(context, "Please input quantity", Toast.LENGTH_SHORT).show();
                    return;
                }
                String msg = "Quantity must be greater than 0";
                int quantity = Integer.parseInt(quantityStr);
                if (quantity > 0) {
                    CartService cartService = new CartService(context);
                    boolean result = cartService.addToCart(product, quantity);
                    if (result) {
                        msg = "Add to cart successfully";
                    } else {
                        msg = "Can't add this item to cart";
                    }
                }
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
