package com.bartovapps.bucketdrop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class ActivityMain extends AppCompatActivity implements View.OnClickListener{

    private static final String LOG_TAG = ActivityMain.class.getSimpleName();
    Toolbar mToolbar;
    Button btAddDrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(LOG_TAG, "onCreate was called");
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        initBackgroundImage();
        initViews();

    }

    private void initViews() {
        btAddDrop = (Button) findViewById(R.id.bt_add_drop);
        btAddDrop.setOnClickListener(this);
    }

    private void initBackgroundImage() {
        ImageView ivBackground = (ImageView) findViewById(R.id.iv_background);
        Glide.with(this).load(R.drawable.background).centerCrop().into(ivBackground);
    }

    @Override
    public void onClick(View view) {
        if(view == btAddDrop){
            displayToast("Button was clicked");
            showDialogAdd();
        }
    }

    private void showDialogAdd() {
        DialogAdd dialogAdd = new DialogAdd();
        dialogAdd.show(getSupportFragmentManager(), "AddDialog");
    }


    private void displayToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
