package com.android.nanodegree.popularmovies.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;

import com.android.nanodegree.popularmovies.R;

/**
 * Created by rhatori on 09/01/2017.
 */

public class NetworkUtil {

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null) {
            return false;
        }

        if (!networkInfo.isConnected()) {
            return false;
        }

        if (!networkInfo.isAvailable()) {
            return false;
        }

        return true;
    }

    public static void showNetworkUnavailableError(final Activity activity, View container) {
        Snackbar snackbar = Snackbar.make(container, R.string.error_no_internet_connection_available, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(R.string.action_retry, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        activity.finish();
                        activity.startActivity(activity.getIntent());
                    }
                }, 0);
            }
        });
        snackbar.show();
    }
}

