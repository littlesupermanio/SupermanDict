package com.hhb.supermandict.thread;

import android.app.Activity;
import android.app.Fragment;

import com.hhb.supermandict.constant.Constants;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by HoHoibin on 08/01/2018.
 * Email: imhhb1997@gmail.com
 */

public class DailyOneRequestThread extends Thread{

    OnDailyRequestFinishedListener onDailyRequestFinishedListener;
    String responseData;

    public DailyOneRequestThread(Activity activity) {
    }

    public void setOnDailyRequestFinishedListener(OnDailyRequestFinishedListener onDailyRequestFinishedListener)
    {
        this.onDailyRequestFinishedListener = onDailyRequestFinishedListener;
    }

    @Override
    public void run() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(Constants.DAILY_SENTENCE).build();
            Response response = client.newCall(request).execute();
            responseData = response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
        }
        super.run();
    }
}
