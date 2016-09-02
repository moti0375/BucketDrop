package com.bartovapps.bucketdrop.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bartovapps.bucketdrop.R;
import com.bartovapps.bucketdrop.beans.Drop;

import java.util.ArrayList;

import io.realm.RealmResults;

/**
 * Created by BartovMoti on 08/31/16.
 */
public class DropsAdapter extends RecyclerView.Adapter<DropsAdapter.DropsViewHolder> {

    private final static String TAG = DropsAdapter.class.getSimpleName();

    LayoutInflater mInflater;
    RealmResults<Drop> mItems;

    public DropsAdapter(Context context, RealmResults<Drop> data) {
        mInflater = LayoutInflater.from(context);
        updateDrops(data);
    }

    private ArrayList<String> initValues() {
        ArrayList<String> dummyItems = new ArrayList<>();
        for(int i = 1; i < 101; i++){
            dummyItems.add("Item " + i);
        }
        return dummyItems;
    }

    public void updateDrops(RealmResults<Drop> results){
        Log.i(TAG, "updateDrops");

        this.mItems = results;
        notifyDataSetChanged();
    }

    @Override
    public DropsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.drop_row_item, parent, false);

        DropsViewHolder viewHolder = new DropsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DropsViewHolder holder, int position) {
        Drop drop = mItems.get(position);
        holder.tvRowItemDrop.setText(drop.getGoal());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class DropsViewHolder extends RecyclerView.ViewHolder{
        TextView tvRowItemDrop;
        TextView tvWhen;

        public DropsViewHolder(View itemView) {
            super(itemView);
            tvRowItemDrop = (TextView) itemView.findViewById(R.id.tv_drop_row_item);
            tvWhen = (TextView) itemView.findViewById(R.id.tv_row_item_when);
        }

    }
}
