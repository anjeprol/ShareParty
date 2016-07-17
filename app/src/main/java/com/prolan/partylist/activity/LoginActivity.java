package com.prolan.partylist.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
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
import com.prolan.partylist.utils.Constants;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private Button btn_login, btn_singUp, btn_resetPassword;
    private TextInputLayout til_email, til_password;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();



        btn_login          = (Button) findViewById(R.id.btn_login);
        btn_singUp         = (Button) findViewById(R.id.btn_signup);
        btn_resetPassword  = (Button) findViewById(R.id.btn_reset_password);

        til_email          = (TextInputLayout) findViewById(R.id.til_email);
        til_password       = (TextInputLayout) findViewById(R.id.til_password);


        inputEmail         = (EditText) findViewById(R.id.etEmail);
        inputPassword      = (EditText) findViewById(R.id.etPassword);
        progressBar        = (ProgressBar) findViewById(R.id.progressBar);

        if (auth.getCurrentUser() != null) {
            Intent mIntent = new Intent(LoginActivity.this, MainActivity.class);
            mIntent.putExtra(Constants.EMAIL,inputEmail.getText().toString());
            startActivity(mIntent);
            finish();
        }

        btn_singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
            }
        });

        btn_resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(LoginActivity.this, .class));
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputPassword.setError("Minimal length password is 6");
                                    } else {
                                        Toast.makeText(LoginActivity.this, getString(R.string.str_register_fail), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Intent mIntent = new Intent(LoginActivity.this, MainActivity.class);
                                    mIntent.putExtra(Constants.EMAIL, inputEmail.getText().toString());
                                    startActivity(mIntent);
                                    finish();
                                }
                            }
                        });
            }
        });

    }

}
