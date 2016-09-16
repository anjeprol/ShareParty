package com.prolan.partylist.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.prolan.partylist.R;
import com.prolan.partylist.utils.Constants;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private final static String ACTION = "Action";
    private TextView mEmailTextView;
    private TextView mUserNameTextView;
    private Intent mIntent;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mIntent = getIntent();

        // Getting the views from nav_bar
        NavigationView mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        View header = mNavigationView.getHeaderView(0);

        mEmailTextView = (TextView) header.findViewById(R.id.nav_tv_email);
        mUserNameTextView = (TextView) header.findViewById(R.id.nav_tv_userName);

        setUsrPreferences();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    //Method to read the information that the user filled when was authenticating
    private void setUsrPreferences() {
        if (!mIntent.getStringExtra(Constants.EMAIL).isEmpty())
        {
            mEmailTextView.setText(mIntent.getStringExtra(Constants.EMAIL));
        }
        else
        {
            mEmailTextView.setText(Constants.NO_EMAIL);
        }
        if (mIntent.getStringExtra(Constants.USER_NAME) != null)
        {
            mUserNameTextView.setText(mIntent.getStringExtra(Constants.USER_NAME));
        }
    }

    //Method to show a custom snackbar
    private void showSnackBar(View view) {
        Snackbar snack = Snackbar.make(view, R.string.msg_sync, Snackbar.LENGTH_LONG).setAction(ACTION, null);
        ViewGroup viewGroup = (ViewGroup) snack.getView();
        TextView textView = (TextView) viewGroup.findViewById(android.support.design.R.id.snackbar_text);
        viewGroup.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
        {
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
        snack.show();
    }

/*    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.

        drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement
        switch (item.getItemId())
        {
            case R.id.action_logout:
                logout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //Method to implement the logout from UI
    private void logout() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.title_logout)
                .setMessage(R.string.message_logout)
                .setNegativeButton(R.string.opt_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //No actions for now
                        return;
                    }
                })
                .setPositiveButton(R.string.opt_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        singOut();
                    }
                })
                .show();
    }

    //Method to sing out from firebase
    private void singOut() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        if (mFirebaseAuth.getCurrentUser() != null)
        {
            mFirebaseAuth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        switch (item.getItemId())
        {
            case R.id.nav_camera:
                break;
            case R.id.nav_gallery:
                break;
            case R.id.nav_slideshow:
                break;
            case R.id.nav_manage:
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_send:
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.fab:
                showSnackBar(view);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        logout();
    }

}
