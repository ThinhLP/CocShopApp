package com.thinhlp.cocshopapp;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.thinhlp.cocshopapp.commons.Const;

public class CustomerCheckoutActivity extends AppCompatActivity {
    private ImageView imgQRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_checkout);
        imgQRCode = (ImageView) findViewById(R.id.imgQRCode);
        Toast.makeText(this, "Order successfully!", Toast.LENGTH_SHORT).show();
        createQRCode();
    }

    public void createQRCode() {
        String cartJson = getIntent().getStringExtra(Const.INTENT_EXTRA.CART_JSON);
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(cartJson, BarcodeFormat.QR_CODE, 270, 270);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            imgQRCode.setImageBitmap(bmp);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

}
