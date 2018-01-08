package com.hhb.supermandict.ui;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hhb.supermandict.R;
import com.hhb.supermandict.constant.Constants;
import com.hhb.supermandict.model.DailyOneItem;
import com.hhb.supermandict.util.NetworkUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by HoHoibin on 08/01/2018.
 * Email: imhhb1997@gmail.com
 */

public class IndexFragment extends Fragment {
    private TextView textViewEng;
    private TextView textViewChi;
    private ImageView imageViewMain;
    private ImageView ivStar;
    private ImageView ivCopy;
    private ImageView ivShare;
    private AppCompatButton button;

    private Boolean isMarked = false;
    private String imageUrl = null;

    private Activity activity;

    private String message;

    public IndexFragment(){

    }

    public static IndexFragment newInstance()
    {
        return new IndexFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private void sendRequest() {
        OkHttpClient okHttpClient = new OkHttpClient();
        final Handler mainHandler = new Handler(Looper.getMainLooper());
        Request request = new Request.Builder()
                .url(Constants.DAILY_SENTENCE)
                .build();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println(e.getMessage());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    parseJSONWithGSON(response.body().string());
                }

            }
        });
    }

    private void parseJSONWithGSON(String responseData) {
//        Gson gson = new Gson();
//        final DailyOneItem dailyOneItem = gson.fromJson(responseData,DailyOneItem.class);

        try {
            final JSONObject dailyOneItem = new JSONObject(responseData);
            Log.e("IndexFragment",dailyOneItem.getString("content") );
            Log.e("IndexFragment",dailyOneItem.getString("note") );
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        textViewEng.setText(dailyOneItem.getString("content"));
                        textViewChi.setText(dailyOneItem.getString("note"));
                        imageUrl = dailyOneItem.getString("picture2");
                        Glide.with(getActivity())
                                .load(imageUrl)
                                .asBitmap()
                                .centerCrop()
                                .into(imageViewMain);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        initViews(view);

        if (!NetworkUtil.isNetworkConnected(getActivity())){
            showNoNetwork();
        }

        sendRequest();
        return view;
    }

    private void initViews(View view) {

        textViewEng = (TextView) view.findViewById(R.id.text_view_eng);
        textViewChi = (TextView) view.findViewById(R.id.text_view_chi);
        imageViewMain = (ImageView) view.findViewById(R.id.image_view_daily);

        ivStar = (ImageView) view.findViewById(R.id.image_view_mark_star);
        ivCopy = (ImageView) view.findViewById(R.id.image_view_copy);

    }

    private void showNoNetwork(){
        Snackbar.make(button, R.string.no_network_err, Snackbar.LENGTH_INDEFINITE)
                .setAction("设置", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Settings.ACTION_SETTINGS));
                    }
                }).show();
    }

}
