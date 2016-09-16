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
import com.google.firebase.auth.FirebaseAuth;
import com.prolan.partylist.R;
import com.prolan.partylist.utils.Behaviors;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private Button      mBackBtn;
    private Button      mResetPasswdBtn;
    private EditText    mInputEmailEditText;
    private ProgressBar mProgressBar;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mBackBtn            = (Button) findViewById(R.id.btn_back);
        mResetPasswdBtn     = (Button) findViewById(R.id.btn_reset_password);
        mInputEmailEditText = (EditText) findViewById(R.id.email);
        mProgressBar        = (ProgressBar) findViewById(R.id.progressBar);

        mFirebaseAuth = FirebaseAuth.getInstance();

        mBackBtn.setOnClickListener(this);
        mResetPasswdBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_reset_password:
                reset();
                break;
        }
    }

    private void reset() {
        String email = mInputEmailEditText.getText().toString().trim();
        // Check if no view has focus:
        Behaviors.hideKeyboard(this.getCurrentFocus(), this);
        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(getApplication(), R.string.msg_email_reset_pass, Toast.LENGTH_SHORT).show();
            return;
        }
        mProgressBar.setVisibility(View.VISIBLE);
        mFirebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(ResetPasswordActivity.this, R.string.msg_sent_email_reset, Toast.LENGTH_SHORT).show();
                    mInputEmailEditText.setText("");
                    startActivity(new Intent(ResetPasswordActivity.this,LoginActivity.class));
                }
                else
                {
                    Toast.makeText(ResetPasswordActivity.this, R.string.err_msg_reset_passwd, Toast.LENGTH_SHORT).show();
                }
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

}
