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

    //En la línea 34 sustituír la cadena de x, por el usuario y contraseña de cada usuario


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        //EditText txtDatos = (EditText) findViewById(R.id.txtDatos);
        //Button bG = (Button) findViewById(R.id.bG);

        ImageView imgQr = findViewById(R.id.qr);

        try{
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap("xxxxxxxxxxxxxxxxx", BarcodeFormat.QR_CODE, 750, 750);
            imgQr.setImageBitmap(bitmap);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}