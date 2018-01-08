package com.hhb.supermandict.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by HoHoibin on 08/01/2018.
 * Email: imhhb1997@gmail.com
 */

public class NetworkUtil {

    public static boolean isNetworkConnected(Context context){

        if (context != null){
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null){
                return true;
            }
        }
        return false;
    }

}
