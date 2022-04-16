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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register, forgotpassword;
    private EditText editTextemail, editTextPassword;
    private Button signIn;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        signIn = (Button) findViewById(R.id.signIn);
        signIn.setOnClickListener(this);

        editTextemail = (EditText) findViewById(R.id.EmailAddress);
        editTextPassword = (EditText) findViewById(R.id.Password);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        forgotpassword = (TextView) findViewById(R.id.forgotPassword);
        forgotpassword.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register:
                startActivity(new Intent(this, registerUser.class));
                break;

            case R.id.signIn:
                userLogin();
                break;

            case R.id.forgotPassword:
                startActivity(new Intent(this, ForgotPassword.class));
                break;
        }
    }

    private void userLogin() {
        String email = editTextemail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextemail.setError("Email es requerido!");
            editTextemail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            System.out.println(email);
            editTextemail.setError("Porfavor ingresa un email valido");
            editTextemail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Contrasena es requerida!");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            editTextPassword.setError("Min password length is 6 characters!");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if(user.isEmailVerified()){
                    //Redirct to Profile
                    startActivity(new Intent(MainActivity.this, ProfileActivity.class) );
                }else{
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this, "Verifica tu email!",Toast.LENGTH_LONG).show();

                    }
                }else{
                    Toast.makeText(MainActivity.this, "Failed to Login!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void sp(View view){
        Intent sig = new Intent(this, menu.class);
        startActivity(sig);
    }
}