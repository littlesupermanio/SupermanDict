package com.hhb.supermandict.ui;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hhb.supermandict.R;
import com.hhb.supermandict.constant.Constants;
import com.hhb.supermandict.db.NoteBookDatabaseHelper;
import com.hhb.supermandict.model.NoteBookItem;
import com.hhb.supermandict.util.DBUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

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
    private TextView textViewCount;
    private ImageView imageViewMain;
    private ImageView ivStar;
    private ImageView ivCopy;
    private CardView cvStoreHint;

    private Boolean isMarked = false;
    private String imageUrl = null;

    private Boolean isRendered = false;

    private NoteBookDatabaseHelper dbHelper;
    private ArrayList<NoteBookItem> list = new ArrayList<NoteBookItem>();

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
        dbHelper = new NoteBookDatabaseHelper(getActivity(),"MyNote.db",null,1);

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
                    parseJSONWithJSONObject(response.body().string());
                }

            }
        });
    }

    private void parseJSONWithJSONObject(String responseData) {
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

        if (DBUtil.queryIfItemExist(dbHelper,textViewEng.getText().toString())){
            ivStar.setImageResource(R.drawable.ic_star_white_24dp);
            isMarked = true;
        } else {
            ivStar.setImageResource(R.drawable.ic_star_border_white_24dp);
            isMarked = false;
        }
        getDataFromDB();

        textViewCount.setText(list.size()+"");

        ivStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 在没有被收藏的情况下
                if (!isMarked){
                    ivStar.setImageResource(R.drawable.ic_star_white_24dp);
                    Snackbar.make(ivStar, R.string.add_to_note, Snackbar.LENGTH_SHORT).show();
                    isMarked = true;

                    ContentValues values = new ContentValues();
                    values.put("input",textViewEng.getText().toString());
                    values.put("output",textViewChi.getText().toString());
                    DBUtil.insertValue(dbHelper,values);
                    getDataFromDB();
                    textViewCount.setText(list.size()+"");
                    values.clear();

                } else {
                    ivStar.setImageResource(R.drawable.ic_star_border_white_24dp);
                    Snackbar.make(ivStar, R.string.remove_from_notebook, Snackbar.LENGTH_SHORT).show();
                    isMarked = false;
                    DBUtil.deleteValue(dbHelper,textViewEng.getText().toString());
                    getDataFromDB();
                    textViewCount.setText(list.size()+"");

                }
            }
        });

        ivCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager manager = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("text", String.valueOf(textViewEng.getText() + "\n" + textViewChi.getText()));
                manager.setPrimaryClip(clipData);

                Snackbar.make(ivCopy, R.string.copy_done, Snackbar.LENGTH_SHORT).show();
            }
        });


        if(!isRendered)
        {
            sendRequest();
            isRendered = true;
        }


        return view;
    }

    private void initViews(View view) {

        textViewEng = view.findViewById(R.id.text_view_eng);
        textViewChi =  view.findViewById(R.id.text_view_chi);
        textViewCount = view.findViewById(R.id.tv_ItemCount);
        imageViewMain = view.findViewById(R.id.image_view_daily);
        cvStoreHint = view.findViewById(R.id.cardView_StoreHint);

        ivStar = view.findViewById(R.id.image_view_mark_star);
        ivCopy = view.findViewById(R.id.image_view_copy);

    }


    private void getDataFromDB() {
        if (list != null) {
            list.clear();
        }
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("notebook",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                String in = cursor.getString(cursor.getColumnIndex("input"));
                String out = cursor.getString(cursor.getColumnIndex("output"));
                NoteBookItem item = new NoteBookItem(in,out);
                list.add(item);
            } while (cursor.moveToNext());
        }

        cursor.close();
    }


    @Override
    public void onResume() {
        super.onResume();
        sendRequest();
        getDataFromDB();
        textViewCount.setText(list.size()+"");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        getDataFromDB();
        if (list != null) {
            textViewCount.setText(list.size()+"");
        }
    }
}
