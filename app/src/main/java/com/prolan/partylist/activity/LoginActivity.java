package com.prolan.partylist.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mLoginBtn;
    private Button mSingUpBtn;
    private Button mResetPasswordBtn;
    private ProgressBar mProgressBar;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Getting Firebase instance
        mFirebaseAuth       = FirebaseAuth.getInstance();
        mLoginBtn           = (Button) findViewById(R.id.btn_login);
        mSingUpBtn          = (Button) findViewById(R.id.btn_signup);
        mResetPasswordBtn   = (Button) findViewById(R.id.btn_reset_password);
        mEmailEditText      = (EditText) findViewById(R.id.etEmail);
        mPasswordEditText   = (EditText) findViewById(R.id.etPassword);
        mProgressBar        = (ProgressBar) findViewById(R.id.progressBar);

        if (mFirebaseAuth.getCurrentUser() != null)
        {
            Intent mIntent = new Intent(LoginActivity.this, MainActivity.class);
            mIntent.putExtra(Constants.USER_NAME, mFirebaseAuth.getCurrentUser().getDisplayName());
            mIntent.putExtra(Constants.EMAIL, mFirebaseAuth.getCurrentUser().getEmail());
            startActivity(mIntent);
            finish();
        }

        mSingUpBtn.setOnClickListener(this);
        mResetPasswordBtn.setOnClickListener(this);
        mLoginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_signup:
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                break;
            case R.id.btn_reset_password:
                break;
        }
    }

    //Method to authenticate the user
    private void login() {
        String email = mEmailEditText.getText().toString();
        final String password = mPasswordEditText.getText().toString();

        if (TextUtils.isEmpty(email))
        {
            mEmailEditText.setError(getString(R.string.err_msg_email));
            return;
        }

        if (TextUtils.isEmpty(password))
        {
            mPasswordEditText.setError(getString(R.string.err_msg_passwd));
            return;
        }
        mProgressBar.setVisibility(View.VISIBLE);

        //authenticate user
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mProgressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful())
                        {
                            // there was an error
                            if (password.length() < 6)
                            {
                                mPasswordEditText.setError(getString(R.string.err_msg_length_passwd));
                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this, getString(R.string.str_register_fail), Toast.LENGTH_LONG).show();
                            }
                        }
                        else
                        {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra(Constants.EMAIL, mEmailEditText.getText().toString());
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }
}