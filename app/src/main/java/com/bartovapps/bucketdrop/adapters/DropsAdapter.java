package com.bartovapps.bucketdrop.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bartovapps.bucketdrop.R;
import com.bartovapps.bucketdrop.beans.Drop;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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
    MarkListener mMarkListener;
    Realm mRealm;
    Context mContext;

    public DropsAdapter(Context context, Realm realm, RealmResults<Drop> data, AdapterEventListener listener, MarkListener markListener) {
        mInflater = LayoutInflater.from(context);
        updateDrops(data);
        mEventListener = listener;
        mMarkListener = markListener;
        mRealm = realm;
        mContext = context;
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
            RecyclerView.ViewHolder viewHolder = new DropsViewHolder(view, mMarkListener);
            return viewHolder;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DropsViewHolder) {
            DropsViewHolder dropsViewHolder = (DropsViewHolder) holder;
            Drop drop = mItems.get(position);
            dropsViewHolder.tvRowItemDrop.setText(drop.getGoal());
            if(drop.isCompleted()){
                dropsViewHolder.mIcon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_checked));
            }else{
                dropsViewHolder.mIcon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_drop));
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(drop.getWhen());
            dropsViewHolder.setWhen(drop.getWhen());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        }
    }

    @Override
    public int getItemCount() {
        if (mItems == null || mItems.isEmpty()) {
            return 0;
        } else {
            return mItems.size() + 1;
        }
    }

    @Override
    public void onSwipe(int position) {
        if (position < mItems.size()) {
            mRealm.beginTransaction();
            mItems.get(position).deleteFromRealm();
            mRealm.commitTransaction();
            notifyItemRemoved(position);
        }
    }

    public void markAsCompleted(int position) {
        if (position < mItems.size()) {
            mRealm.beginTransaction();
            mItems.get(position).setCompleted(true);
            mRealm.commitTransaction();
            notifyItemChanged(position);
        }

    }

    public static class DropsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvRowItemDrop;
        TextView tvWhen;
        MarkListener mMarkListener;
        ImageView mIcon;
        Context mContext;
        View mItemView;


        public DropsViewHolder(View itemView, MarkListener markListener) {
            super(itemView);
            mContext = itemView.getContext();
            mItemView = itemView;
            mMarkListener = markListener;
            itemView.setOnClickListener(this);
            tvRowItemDrop = (TextView) itemView.findViewById(R.id.tv_drop_row_item);
            tvWhen = (TextView) itemView.findViewById(R.id.tv_row_item_when);
            mIcon = (ImageView) itemView.findViewById(R.id.iv_row_item_icon);
        }

        @Override
        public void onClick(View view) {
            mMarkListener.onMark(getAdapterPosition());
        }

        public void setWhen(long when) {
            tvWhen.setText(DateUtils.getRelativeTimeSpanString(when, System.currentTimeMillis(), DateUtils.DAY_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL));
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
        void onClick();
    }

    public interface MarkListener {
        void onMark(int position);
    }

}
