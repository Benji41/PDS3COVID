package com.benjaminreynoso.covidamigopds3;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.benjaminreynoso.covidamigopds3.Interface.API;
import com.benjaminreynoso.covidamigopds3.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import retrofit2.* ;
import retrofit2.converter.gson.GsonConverterFactory;


public class registerUser extends AppCompatActivity implements View.OnClickListener {

    private TextView banner,registerUser;
    private EditText editTextname, editTextlastname, editTextage, editTextemail, editTextpassword;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://covid-amigo-pds3.herokuapp.com/").addConverterFactory(GsonConverterFactory.create());

    public static Retrofit retrofit = builder.build();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        editTextname = (EditText)  findViewById(R.id.Name);
        editTextlastname = (EditText)  findViewById(R.id.lastName);
        editTextage = (EditText) findViewById(R.id.age);
        editTextemail = (EditText) findViewById(R.id.email);
        editTextpassword = (EditText) findViewById(R.id.password);

        progressBar = (ProgressBar)  findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.banner:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.registerUser:
                registerUser();
                break;

    }
}

    private void registerUserAPI(String email, String name, String lastNames, Integer age){
        API RegistroAPI = retrofit.create(API.class);
        Call<User> Call = RegistroAPI.insertUser(email, name, lastNames, age);
        Call.enqueue(new Callback<User>() {
            @Override
           public void onResponse(retrofit2.Call<User> call, Response<User> response) {
              Toast.makeText(registerUser.this, "Usuario ha  Registrado!", Toast.LENGTH_LONG).show();
           }

            @Override
            public void onFailure(retrofit2.Call<User> call, Throwable t) {
                Toast.makeText(registerUser.this, t.toString(), Toast.LENGTH_LONG).show();

            }
        });

    }

    private void registerUser() {
        String email= editTextemail.getText().toString().trim();
        String password= editTextpassword.getText().toString().trim();
        String name= editTextname.getText().toString().trim();
        String lastName= editTextlastname.getText().toString().trim();
        String age= editTextage.getText().toString().trim();

        if((name.isEmpty())){
            editTextname.setError("Full name is required!");
            editTextname.requestFocus();
            return;
        }

        if((lastName.isEmpty())){
            editTextlastname.setError("Last name is required!");
            editTextlastname.requestFocus();
            return;
        }

        if(age.isEmpty()){
            editTextage.setError("Age is required!");
            editTextage.requestFocus();
            return;
        }

        if(email.isEmpty()){
            editTextemail.setError("Email is required!");
            editTextemail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextemail.setError("Please provide valid email");
            editTextemail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextpassword.setError("Password is required!");
            editTextpassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            editTextpassword.setError("Min password length should be 6 characters");
            editTextpassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            User User= new User(name, lastName, age, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(User).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        registerUserAPI(email, name, lastName, Integer.valueOf(age));

                                        Toast.makeText(registerUser.this, "Usuario registrado!!!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.VISIBLE);

                                        //Redirct to Login

                                    }else{
                                        Toast.makeText(registerUser.this, "Fallo al registrar! Intenta de nuevo ", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }

                            });

                        }else{
                            Toast.makeText(registerUser.this, "Fallo al registrar! Intento ", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

    }
}

