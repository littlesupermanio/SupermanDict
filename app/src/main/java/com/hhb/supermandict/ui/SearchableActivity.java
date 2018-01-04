package com.hhb.supermandict.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import com.hhb.supermandict.R;

/**
 * Created by HoHoibin on 03/01/2018.
 * Email: imhhb1997@gmail.com
 */

public class SearchableActivity extends AppCompatActivity {
    TextView mTvWord = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTvWord = (TextView) findViewById(R.id.tv_word);

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            String text = getString(R.string.notice) + query;
            mTvWord.setText(text);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);    // 显示“开始搜索”的按钮
        searchView.setQueryRefinementEnabled(true); // 提示内容右边提供一个将提示内容放到搜索框的按钮
        return true;
    }
}
