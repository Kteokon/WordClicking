package com.example.wordclicking;

import android.view.View;

public class WordClickListener implements View.OnClickListener{
    @Override
    public void onClick(View v) {
        String tag = v.getTag().toString();
    }
}
