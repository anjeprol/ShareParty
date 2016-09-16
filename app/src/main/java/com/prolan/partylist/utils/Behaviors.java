package com.prolan.partylist.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by antoniopradoo on 9/15/16.
 */
public abstract class Behaviors {
    public static void hideKeyboard(View view,Context context){
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
