package com.benjaminreynoso.covidamigopds3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


    }

    public void sigVentana(View view){

        Intent sig = new Intent(this, epd.class);
        startActivity(sig);

    }
}