package com.taxidriver.santos.third_party.ui.snackbar;


import com.google.android.material.snackbar.Snackbar;

/**
 * Created by 朱志强 on 2017/11/12.
 */

public interface SnackbarCallback {

    void onSnackbarShown(Snackbar sb);

    void onSnackbarDismissed(Snackbar sb, int event);

}
