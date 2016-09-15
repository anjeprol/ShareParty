package com.prolan.partylist.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.prolan.partylist.utils.Constants;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText    mEmailEditText;
    private EditText    mPasswordEdithText;
    private Button      mLoginBtn;
    private Button      mSignUpBtn;
    private Button      mResetPasswordBtn;
    private ProgressBar mProgressBar;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Get Firebase instance
        mFirebaseAuth       = FirebaseAuth.getInstance();
        mLoginBtn           = (Button) findViewById(R.id.btn_login);
        mSignUpBtn          = (Button) findViewById(R.id.btn_signup);
        mResetPasswordBtn   = (Button) findViewById(R.id.btn_reset_password);
        mEmailEditText      = (EditText) findViewById(R.id.etEmail);
        mPasswordEdithText  = (EditText) findViewById(R.id.etPassword);
        mProgressBar        = (ProgressBar) findViewById(R.id.progressBar);

        mLoginBtn.setOnClickListener(this);
        mSignUpBtn.setOnClickListener(this);
        mResetPasswordBtn.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_login:
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                break;
            case R.id.btn_signup:
                singUp();
                break;
            case R.id.btn_reset_password:
                startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
                break;
        }
    }

    //Method to signUp with firebase
    private void singUp(){
        String email    = mEmailEditText.getText().toString().trim();
        String password = mPasswordEdithText.getText().toString().trim();
        if (TextUtils.isEmpty(email))
        {
            mEmailEditText.requestFocus();
            mEmailEditText.setError(getString(R.string.err_msg_email));
            return;
        }
        if (TextUtils.isEmpty(password))
        {
            mPasswordEdithText.setError(getString(R.string.err_msg_passwd));
            mPasswordEdithText.requestFocus();
            return;
        }
        else if (password.length() < 6)
        {
            mPasswordEdithText.setError(getString(R.string.err_msg_length_passwd));
            mPasswordEdithText.requestFocus();
            return;
        }

        mProgressBar.setVisibility(View.VISIBLE);
        //Creating the user
        mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mProgressBar.setVisibility(View.GONE);
                if (!task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), R.string.str_register_fail, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                    intent.putExtra(Constants.EMAIL, mEmailEditText.getText().toString());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}