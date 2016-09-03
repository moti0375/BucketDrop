package com.bartovapps.bucketdrop.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bartovapps.bucketdrop.R;
import com.bartovapps.bucketdrop.beans.Drop;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by BartovMoti on 08/31/16.
 */
public class DropsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SwipeListener {

    private final static String TAG = DropsAdapter.class.getSimpleName();
    public static final int NORMAL_TYPE = 10;
    public static final int FOOTER_TYPE = 20;


    LayoutInflater mInflater;
    RealmResults<Drop> mItems;
    AdapterEventListener mEventListener;
    Realm mRealm;

    public DropsAdapter(Context context, Realm realm, RealmResults<Drop> data, AdapterEventListener listener) {
        mInflater = LayoutInflater.from(context);
        updateDrops(data);
        mEventListener = listener;
        mRealm = realm;
    }

    private ArrayList<String> initValues() {
        ArrayList<String> dummyItems = new ArrayList<>();
        for (int i = 1; i < 101; i++) {
            dummyItems.add("Item " + i);
        }
        return dummyItems;
    }

    public void updateDrops(RealmResults<Drop> results) {
        Log.i(TAG, "updateDrops");

        this.mItems = results;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        Log.i(TAG, "View type: " + viewType);
        if (viewType == FOOTER_TYPE) {
            view = mInflater.inflate(R.layout.recycler_view_footer, parent, false);
            RecyclerView.ViewHolder viewHolder = new FooterHolder(view);
            return viewHolder;
        } else {
            view = mInflater.inflate(R.layout.drop_row_item, parent, false);
            RecyclerView.ViewHolder viewHolder = new DropsViewHolder(view);
            return viewHolder;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DropsViewHolder) {
            DropsViewHolder dropsViewHolder = (DropsViewHolder) holder;
            Drop drop = mItems.get(position);
            dropsViewHolder.tvRowItemDrop.setText(drop.getGoal());
        }
    }

    @Override
    public int getItemCount() {
        if(mItems == null || mItems.isEmpty()){
            return 0;
        }else{
            return mItems.size() + 1;
        }
    }

    @Override
    public void onSwipe(int position) {
        if(position < mItems.size()){
            mRealm.beginTransaction();
            mItems.get(position).deleteFromRealm();
            mRealm.commitTransaction();
            notifyItemRemoved(position);
        }
    }

    public static class DropsViewHolder extends RecyclerView.ViewHolder {
        TextView tvRowItemDrop;
        TextView tvWhen;

        public DropsViewHolder(View itemView) {
            super(itemView);
            tvRowItemDrop = (TextView) itemView.findViewById(R.id.tv_drop_row_item);
            tvWhen = (TextView) itemView.findViewById(R.id.tv_row_item_when);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (mItems == null || position < mItems.size()) {
            return NORMAL_TYPE;
        } else {
            return FOOTER_TYPE;
        }
    }

    public class FooterHolder extends RecyclerView.ViewHolder {
        Button button;

        public FooterHolder(View itemView) {
            super(itemView);
            button = (Button) itemView.findViewById(R.id.tb_footer_button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mEventListener.onClick();
                }
            });
        }


    }

    public interface AdapterEventListener {
        public void onClick();
    }

}
