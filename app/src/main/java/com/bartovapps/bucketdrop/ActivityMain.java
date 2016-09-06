package com.bartovapps.bucketdrop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bartovapps.bucketdrop.adapters.CompleteListener;
import com.bartovapps.bucketdrop.adapters.Divider;
import com.bartovapps.bucketdrop.adapters.DropsAdapter;
import com.bartovapps.bucketdrop.adapters.SimpleTouchCallback;
import com.bartovapps.bucketdrop.beans.Drop;
import com.bumptech.glide.Glide;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import widgets.BucketRecyclerView;

public class ActivityMain extends AppCompatActivity implements View.OnClickListener, DropsAdapter.AdapterEventListener, DropsAdapter.MarkListener{

    private static final String LOG_TAG = ActivityMain.class.getSimpleName();
    Toolbar mToolbar;
    Button btAddDrop;
    BucketRecyclerView recyclerView;
    Realm mRealm;
    RealmResults<Drop> mRealmResults;
    DropsAdapter mAdapter;
    View mEmptyView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRealm = Realm.getDefaultInstance();
        mRealmResults = mRealm.where(Drop.class).findAllAsync();
        initViews();
        initBackgroundImage();
    }


    @Override
    protected void onStart() {
        super.onStart();
        mRealmResults.addChangeListener(callback);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mRealmResults.removeChangeListeners();
    }

    private void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        btAddDrop = (Button) findViewById(R.id.bt_add_drop);
        btAddDrop.setOnClickListener(this);
        mEmptyView = findViewById(R.id.empty_drops_layout);

        recyclerView = (BucketRecyclerView) findViewById(R.id.rv_drops);
        recyclerView.addItemDecoration(new Divider(this, LinearLayoutManager.VERTICAL));
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.hideIfEmpty(mToolbar);
        recyclerView.shoIfEmpty(mEmptyView);
        mAdapter = new DropsAdapter(this, mRealm, mRealmResults, this, this);
        recyclerView.setAdapter(mAdapter);
        SimpleTouchCallback touchCallback = new SimpleTouchCallback(mAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(touchCallback);
        touchHelper.attachToRecyclerView(recyclerView);
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

    @Override
    public void onClick() {
        showDialogAdd();
    }

    @Override
    public void onMark(int position) {
        showCheckedDialog(position);
    }

    private void showCheckedDialog(int position) {
        DialogChecked dialogChecked = new DialogChecked();
        dialogChecked.setCompleteListener(completeListener);
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        dialogChecked.setArguments(bundle);
        dialogChecked.show(getSupportFragmentManager(), "Mark");
    }

    CompleteListener completeListener = new CompleteListener() {
        @Override
        public void onComplete(int position) {
            mAdapter.markAsCompleted(position);
            Toast.makeText(ActivityMain.this, "Item " + position + " completed", Toast.LENGTH_SHORT).show();
        }
    };

}
