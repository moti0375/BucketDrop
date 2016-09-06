package com.bartovapps.bucketdrop.utils;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

import java.util.List;

/**
 * Created by BartovMoti on 09/02/16.
 */
public class Utils {

    public static void showViews(List<View> views){
        for(View view: views){
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void hideViews(List<View> views){
        for(View view: views){
            view.setVisibility(View.GONE);
        }
    }

    public static boolean moreThanJelliBean(){
        return Build.VERSION.SDK_INT > 15;

    }
    public static void setBackground(View view, Drawable drawable){

        if(moreThanJelliBean()){
            view.setBackground(drawable);
        }else{
            view.setBackgroundDrawable(drawable);
        }

    }

}
