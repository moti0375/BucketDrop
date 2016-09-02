package com.bartovapps.bucketdrop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bartovapps.bucketdrop.adapters.DropsAdapter;
import com.bartovapps.bucketdrop.beans.Drop;
import com.bumptech.glide.Glide;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class ActivityMain extends AppCompatActivity implements View.OnClickListener{

    private static final String LOG_TAG = ActivityMain.class.getSimpleName();
    Toolbar mToolbar;
    Button btAddDrop;
    RecyclerView recyclerView;
    Realm mRealm;
    RealmResults<Drop> mRealmResults;
    DropsAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mRealm = Realm.getDefaultInstance();
        initBackgroundImage();
        initViews();
    }


    @Override
    protected void onStart() {
        super.onStart();
        mRealmResults = mRealm.where(Drop.class).findAllAsync();
        mRealmResults.addChangeListener(callback);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mRealmResults.removeChangeListeners();
    }

    private void initViews() {
        btAddDrop = (Button) findViewById(R.id.bt_add_drop);
        btAddDrop.setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.rv_drops);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        mAdapter = new DropsAdapter(this, mRealmResults);
        recyclerView.setAdapter(mAdapter);
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

    private RealmChangeListener callback = new RealmChangeListener() {
        @Override
        public void onChange(Object element) {
            mAdapter.updateDrops(mRealmResults);
        }
    };
}
