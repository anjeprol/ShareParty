package com.prolan.partylist.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.prolan.partylist.R;
import com.prolan.partylist.activity.MainActivity;

/**
 * Created by antoniopradoo on 9/15/16.
 */
public abstract class Behaviors {

    private final static String ACTION = "Action";

    //Method to hide the keyboard from all the activities
    public static void hideKeyboard(View view,Context context){
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    //Method to hide the keyBoard
    public static void showKeyboard(Context context){
        ((InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE)).
                toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    //Method to show a custom snackbar

    /**
     * Generate a custom snackbar
     *
     * @param view          view object to add the snack bar
     * @param activity      activity to associate the custom color
     * @param message       String message to show into the snack bar
     * @param time          int value to indicate duration
     * @param color         int value to add the color
     */

    public static void showSnackBar(View view, Activity activity,String message, int time, int color)
    {
        Snackbar snack = Snackbar.make(view,message, time).setAction(ACTION, null);
        ViewGroup viewGroup = (ViewGroup) snack.getView();
        TextView textView = (TextView) viewGroup.findViewById(android.support.design.R.id.snackbar_text);
        viewGroup.setBackgroundColor(ContextCompat.getColor(activity,color));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
        {
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
        snack.show();
    }

    public static void setVisibility(ImageView visibleImageView, ImageView hideImageView, TextView usrTextView, boolean status){
        usrTextView.setEnabled(status);
        visibleImageView.setVisibility(View.VISIBLE);
        hideImageView.setVisibility(View.GONE);
    }
}
