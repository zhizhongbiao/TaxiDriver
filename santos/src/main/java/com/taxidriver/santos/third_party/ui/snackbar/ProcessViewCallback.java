package com.taxidriver.santos.third_party.ui.snackbar;


import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

/**
 * Created by 喜欢、陪你看风景 on 2017/11/15.
 */

public interface ProcessViewCallback {
    void processSnackbarView(Snackbar.SnackbarLayout layout, TextView msgView, TextView actionView);
}
