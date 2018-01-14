package com.hhb.supermandict.listener;

import android.view.View;

/**
 * Created by HoHoibin on 14/01/2018.
 * Email: imhhb1997@gmail.com
 */

public interface OnRecyclerViewOnClickListener {
    void OnItemClick(View view, int position);

    void OnSubViewClick(View view, int position);
}
