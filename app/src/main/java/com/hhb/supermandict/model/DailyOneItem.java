package com.hhb.supermandict.model;

/**
 * Created by HoHoibin on 08/01/2018.
 * Email: imhhb1997@gmail.com
 */

public class DailyOneItem {

    // 英文内容
    private String content = null;
    // 中文内容
    private String note = null;
    // 图片地址
    private String imgUrl = null;
    // 每一句的id
    private int id;

    public String getContent() {
        return content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getNote() {
        return note;
    }

    public int getId() {
        return id;
    }
}
