package com.bartovapps.bucketdrop.utils;

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

}
