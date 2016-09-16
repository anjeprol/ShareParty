package com.prolan.partylist.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.prolan.partylist.R;
import com.prolan.partylist.utils.Behaviors;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBackBtn;
    private Button mResetPasswdBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mBackBtn        = (Button) findViewById(R.id.btn_back);
        mResetPasswdBtn = (Button) findViewById(R.id.btn_reset_password);

        mBackBtn.setOnClickListener(this);
        mResetPasswdBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_reset_password:
                reset();
                break;
        }
    }

    private void reset() {
        // Check if no view has focus:
        Behaviors.hideKeyboard(this.getCurrentFocus(),this);
    }

}
