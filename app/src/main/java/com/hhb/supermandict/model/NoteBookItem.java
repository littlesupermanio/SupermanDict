package com.hhb.supermandict.model;

/**
 * Created by HoHoibin on 14/01/2018.
 * Email: imhhb1997@gmail.com
 */


public class NoteBookItem {

    // 原文
    private String input = null;
    // 译文
    private String output = null;

    public NoteBookItem(String input,String output){
        this.input = input;
        this.output = output;
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }
}
