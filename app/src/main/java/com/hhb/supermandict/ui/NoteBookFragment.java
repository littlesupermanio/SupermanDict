package com.hhb.supermandict.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hhb.supermandict.R;
import com.hhb.supermandict.adapter.NoteBookItemAdapter;
import com.hhb.supermandict.db.NoteBookDatabaseHelper;
import com.hhb.supermandict.listener.OnRecyclerViewOnClickListener;
import com.hhb.supermandict.model.NoteBookItem;
import com.hhb.supermandict.util.DBUtil;

import java.util.ArrayList;
import java.util.Collections;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by HoHoibin on 14/01/2018.
 * Email: imhhb1997@gmail.com
 */

public class NoteBookFragment extends Fragment {
    private RecyclerView recyclerViewNotebook;
    private ArrayList<NoteBookItem> list = new ArrayList<NoteBookItem>();
    private NoteBookItemAdapter adapter;
    private NoteBookDatabaseHelper dbHelper;
    private FragmentManager fragmentmanager;
    private CoordinatorLayout coLayout;


    public NoteBookFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new NoteBookDatabaseHelper(getActivity(),"MyNote.db",null,1);
        fragmentmanager = getFragmentManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_book,container,false);
        initViews(view);

        getDataFromDB();

        Collections.reverse(list);
        adapter = new NoteBookItemAdapter(getActivity(),list);
        recyclerViewNotebook.setAdapter(adapter);
        adapter.setItemClickListener(new OnRecyclerViewOnClickListener() {

            @Override
            public void OnItemClick(View view, int position) {

            }

            @Override
            public void OnSubViewClick(View view, final int position) {

                switch (view.getId()){

                    case R.id.image_view_copy:

                        NoteBookItem item1 = list.get(position);

                        ClipboardManager manager = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
                        ClipData clipData = ClipData.newPlainText("text", String.valueOf(item1.getInput() + "\n" + item1.getOutput()));
                        manager.setPrimaryClip(clipData);

                        Snackbar.make(coLayout, R.string.copy_done, Snackbar.LENGTH_SHORT).show();

                        break;

                    case R.id.image_view_mark_star:

                        final NoteBookItem item2 = list.get(position);

                        DBUtil.deleteValue(dbHelper,item2.getInput());

                        list.remove(position);

                        adapter.notifyItemRemoved(position);
                        adapter.notifyItemRangeChanged(position,list.size());

                        Snackbar.make(coLayout, R.string.remove_from_notebook, Snackbar.LENGTH_SHORT)
                                .setAction(R.string.undo, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ContentValues values = new ContentValues();
                                        values.put("input",item2.getInput());
                                        values.put("output",item2.getOutput());

                                        DBUtil.insertValue(dbHelper,values);

                                        values.clear();

                                        list.add(position,item2);
                                        adapter.notifyItemInserted(position);
                                        recyclerViewNotebook.smoothScrollToPosition(position);
                                    }
                                }).show();

                        break;

                    case R.id.image_view_search:
                        NoteBookItem noteBookItem = list.get(position);
                        String word = noteBookItem.getInput();
                        WordFragment wordFragment = new WordFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("queryWord", word);
                        wordFragment.setArguments(bundle);
                        FragmentTransaction ft = fragmentmanager.beginTransaction();
                        ft.replace(R.id.container_main,wordFragment);
                        ft.addToBackStack(null);
                        ft.commit();
                        break;

                    default:
                        break;
                }
            }
        });
        return view;
    }

    private void initViews(View view) {

        recyclerViewNotebook = (RecyclerView) view.findViewById(R.id.recycler_view_notebook);
        recyclerViewNotebook.setLayoutManager(new LinearLayoutManager(getActivity()));

        coLayout = view.findViewById(R.id.notebook_layout);

    }

    @Override
    public void onResume() {
        super.onResume();
        getDataFromDB();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }


    //hidden状态改变的时候触发的事情
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        getDataFromDB();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
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
}
