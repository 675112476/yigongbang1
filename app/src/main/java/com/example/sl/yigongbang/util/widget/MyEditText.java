package com.example.sl.yigongbang.util.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

public class MyEditText extends android.support.v7.widget.AppCompatEditText {

    public MyEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
       // init();
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
      //  init();
    }

    public MyEditText(Context context) {
        super(context);
      //  init();
    }

//    private void init() {
//        if (!isInEditMode()) {
//            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/MavenPro-Regular.ttf");
//            setTypeface(tf);
//        }
//    }

}
