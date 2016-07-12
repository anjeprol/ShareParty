package com.prolan.partylist.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.prolan.partylist.R;

public class SignupActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private Button btn_login, btn_singUp, btn_resetPassword;
    private TextInputLayout til_email, til_password;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_singUp = (Button) findViewById(R.id.btn_signup);
        btn_resetPassword = (Button) findViewById(R.id.btn_reset_password);

        til_email = (TextInputLayout) findViewById(R.id.til_email);
        til_password = (TextInputLayout) findViewById(R.id.til_password);


        inputEmail = (EditText) findViewById(R.id.etEmail);
        inputPassword = (EditText) findViewById(R.id.etPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
            }
        });

        btn_singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    //Toast.makeText(getApplicationContext(),"Enter email address",Toast.LENGTH_LONG).show();
                    inputEmail.requestFocus();
                    til_email.setError("Enter email address");
                    til_email.setErrorEnabled(true);
                    til_password.setErrorEnabled(false);
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    til_password.setError("Enter password");
                    inputPassword.requestFocus();
                    til_password.setErrorEnabled(true);
                    til_email.setErrorEnabled(false);
                    //Toast.makeText(getApplicationContext(),"Enter password",Toast.LENGTH_LONG).show();
                    return;
                }else if(password.length() < 6 ){
                    til_password.setErrorEnabled(true);
                    til_password.setError("Password too short!");
                    inputPassword.requestFocus();
                    til_email.setErrorEnabled(false);
                    return;
                }

                til_password.setErrorEnabled(false);
                til_email.setErrorEnabled(false);

                progressBar.setVisibility(View.VISIBLE);
                //Creating the user

                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if(!task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), R.string.str_register_fail,Toast.LENGTH_SHORT).show();
                        }else{
                            startActivity(new Intent(SignupActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                });

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
