package com.benjaminreynoso.covidamigopds3;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class qr extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        EditText txtDatos = (EditText) findViewById(R.id.txtDatos);
        Button bG = (Button) findViewById(R.id.bG);
        ImageView imgQr = findViewById(R.id.qr);

        bG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.encodeBitmap(txtDatos.getText().toString(), BarcodeFormat.QR_CODE, 750, 750);
                    imgQr.setImageBitmap(bitmap);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }
}