package com.justadeveloper96.githubbrowser.helpers;

import android.graphics.Typeface;
import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by harshith on 27/9/17.
 */

public class Font {

    public static Typeface getFont(Context context, String fontPath) {
        return Typeface.createFromAsset(context.getApplicationContext().getAssets(), fontPath);
    }
}