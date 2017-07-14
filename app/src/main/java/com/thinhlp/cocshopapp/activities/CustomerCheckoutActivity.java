package com.thinhlp.cocshopapp.activities;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.thinhlp.cocshopapp.R;
import com.thinhlp.cocshopapp.commons.Const;
import com.thinhlp.cocshopapp.commons.Utils;

import java.util.Date;

public class CustomerCheckoutActivity extends AppCompatActivity {
    private ImageView imgQRCode;
    private Button btnCapture;
    private TextView txtOrderDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_checkout);
        imgQRCode = (ImageView) findViewById(R.id.imgQRCode);
        btnCapture = (Button) findViewById(R.id.btnCaptureScreen);
        txtOrderDate = (TextView) findViewById(R.id.txtOrderDate);

        Toast.makeText(this, "Order successfully!", Toast.LENGTH_SHORT).show();
        createQRCode();

        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureScreen();
            }
        });

        txtOrderDate.setText(Utils.formatDate(new Date(), "hh:mm:ss dd/MM/yyyy"));
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

    public void captureScreen() {
        // Get rootview
        View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        Bitmap screenshot = getScreenShot(rootView);
        String title = "order_" + Utils.formatDate(new Date(), "yyyy_MM_dd_hh_mm_ss");
        Toast.makeText(this, "Capture screen successfully!", Toast.LENGTH_SHORT).show();
        MediaStore.Images.Media.insertImage(getContentResolver(), screenshot, title + ".jpg" , new Date().toString());
    }

    public Bitmap getScreenShot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }


}
