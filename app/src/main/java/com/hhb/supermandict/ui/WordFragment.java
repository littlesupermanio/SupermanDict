package com.hhb.supermandict.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hhb.supermandict.R;
import com.hhb.supermandict.constant.Constants;
import com.hhb.supermandict.db.NoteBookDatabaseHelper;
import com.hhb.supermandict.model.ShanBayModel;
import com.hhb.supermandict.util.DBUtil;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by HoHoibin on 13/01/2018.
 * Email: imhhb1997@gmail.com
 */

public class WordFragment extends Fragment {
    private String queryWord;
    private String result=null;

    private NestedScrollView detail_view;
    private TextView tv_DefCh_Detail,tv_DefEn_Detail;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppCompatButton AmEButton;
    private AppCompatButton BrEButton;
    private AppCompatButton SnackButton;
    private FloatingActionButton floatingActionButton;


    private ShanBayModel.ShanBay model;
    private ShanBayModel smodel;
    private NoteBookDatabaseHelper dbHelper;
    private boolean isMarked = false;


    private MediaPlayer mediaPlayer;
    private MediaPlayer mediaPlayer2;

    public WordFragment(){

    }

    public static WordFragment newInstance()
    {
        return new WordFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new NoteBookDatabaseHelper(getActivity(),"MyNote.db",null,1);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word,container,false);
        initViews(view);

        Bundle bundle = this.getArguments();
        if (bundle != null)
        {
            queryWord = bundle.getString("queryWord");
            collapsingToolbarLayout.setTitle(queryWord);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer2 = new MediaPlayer();
            mediaPlayer2.setAudioStreamType(AudioManager.STREAM_MUSIC);
            sendRequest();
        }



        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 在没有被收藏的情况下
                if (!isMarked){
                    floatingActionButton.setImageResource(R.drawable.ic_star_white_24dp);
                    Snackbar.make(collapsingToolbarLayout, R.string.add_to_note,Snackbar.LENGTH_SHORT)
                            .show();
                    isMarked = true;

                    ContentValues values = new ContentValues();
                    values.put("input",queryWord);
                    values.put("output",model.getCn_definition().getDefn());
                    DBUtil.insertValue(dbHelper,values);

                    values.clear();

                } else {
                    floatingActionButton.setImageResource(R.drawable.ic_star_border_white_24dp);
                    Snackbar.make(collapsingToolbarLayout,R.string.remove_from_notebook,Snackbar.LENGTH_SHORT)
                            .show();
                    isMarked = false;

                    DBUtil.deleteValue(dbHelper, queryWord);
                }

            }
        });




        return view;
    }

    private void initViews(View view) {
        collapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar);
        floatingActionButton = view.findViewById(R.id.add_to_note);
        tv_DefCh_Detail = view.findViewById(R.id.tv_DefCh_Detail);
        tv_DefEn_Detail = view.findViewById(R.id.tv_DefEn_Detail);
        AmEButton = view.findViewById(R.id.pronounceAmE);
        BrEButton = view.findViewById(R.id.pronounceBrE);
        detail_view = view.findViewById(R.id.layout_word_detail);
    }

    public void sendRequest()
    {
        OkHttpClient okHttpClient = new OkHttpClient();
        final Handler mainHandler = new Handler(Looper.getMainLooper());
        Request request = new Request.Builder()
                .url(Constants.SHANBAY_API + queryWord)
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
                response.body().close();
            }
        });
    }

    private void parseJSONWithGSON(String string) {
        Log.e("word",string );
        Gson gson = new Gson();
        //用泛型封装model 并解析
        smodel = gson.fromJson(string, new TypeToken<ShanBayModel<ShanBayModel.ShanBay>>(){}.getType());
        model = (ShanBayModel.ShanBay) smodel.getData();
        if(smodel.getStatus_code()==0)
        {
            if (smodel != null) {
                if (model.getPronunciations() != null) {
                    final ShanBayModel.ShanBay.Pronunciations p = model.getPronunciations();
                    final ShanBayModel.ShanBay.Definition cn_def = model.getCn_definition();
                    final ShanBayModel.ShanBay.Definition en_def = model.getEn_definition();
                    final String us_audio = model.getUs_audio();
                    final String uk_audio = model.getUk_audio();

                    Log.e("us_audio",us_audio );
                    try {
                        mediaPlayer.setDataSource(us_audio);
                        mediaPlayer2.setDataSource(uk_audio);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    AmEButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                mediaPlayer.prepareAsync();
                                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                    @Override
                                    public void onPrepared(MediaPlayer mediaPlayer) {
                                        mediaPlayer.start();
                                    }
                                });

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    BrEButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                mediaPlayer2.prepareAsync();
                                mediaPlayer2.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                    @Override
                                    public void onPrepared(MediaPlayer mediaPlayer) {
                                        mediaPlayer.start();
                                    }
                                });

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });


                    result = "\nAmE:" + p.getUs() + "\nBrE:" + p.getUk() + "\n";

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (DBUtil.queryIfItemExist(dbHelper, queryWord)) {
                                    floatingActionButton.setImageResource(R.drawable.ic_star_white_24dp);
                                    isMarked = true;
                                }

                                AmEButton.setText("美音："+p.getUs());
                                BrEButton.setText("英音："+p.getUk());
                                tv_DefCh_Detail.setText(cn_def.getDefn());
                                tv_DefEn_Detail.setText(en_def.getDefn());

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                Log.e("result",result+"");
                Log.e("result",smodel.getMsg()+"");
            }
        }else{
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    detail_view.setVisibility(View.INVISIBLE);
                    Snackbar snackbar = Snackbar.make(collapsingToolbarLayout, R.string.no_result, Snackbar.LENGTH_SHORT);
                    snackbar.setAction(R.string.retype, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getActivity().finish();
                        }
                    });
                    snackbar.show();
                    floatingActionButton.setVisibility(View.INVISIBLE);
                }
            });

        }

    }

}
