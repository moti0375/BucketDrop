package widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.bartovapps.bucketdrop.utils.Utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by BartovMoti on 09/02/16.
 */
public class BucketRecyclerView extends RecyclerView {

    private List<View> mShowWhenEmpty = Collections.emptyList();
    private List<View> mHideWhenEmpty = Collections.emptyList();

    private static final String TAG = BucketRecyclerView.class.getSimpleName();

    private AdapterDataObserver mAdapterDateObserver = new AdapterDataObserver() {

        @Override
        public void onChanged() {
            Log.i(TAG, "mAdapterDateObserver onChanged: ");
            toggleViews();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            toggleViews();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            toggleViews();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            toggleViews();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            toggleViews();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            toggleViews();
        }
    };


    public BucketRecyclerView(Context context) {
        super(context);
    }

    public BucketRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BucketRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);

        if (adapter != null) {
            adapter.registerAdapterDataObserver(mAdapterDateObserver);
        }

        mAdapterDateObserver.onChanged();
    }

    public void hideIfEmpty(View... views) {
        mHideWhenEmpty = Arrays.asList(views);

    }

    public void shoIfEmpty(View... views) {
        mShowWhenEmpty = Arrays.asList(views);
    }

    private void toggleViews() {
        Log.i(TAG, "toggleViews");

        if (getAdapter() != null && !mHideWhenEmpty.isEmpty() && !mShowWhenEmpty.isEmpty()) {
            int items = getAdapter().getItemCount();
            if (items == 0) {
                Log.i(TAG, "recycler is empty");

                Utils.showViews(mShowWhenEmpty);

                setVisibility(View.GONE);
                Utils.hideViews(mHideWhenEmpty);


            } else {
                Log.i(TAG, "recycler is not empty");
                Utils.showViews(mHideWhenEmpty);
                setVisibility(View.VISIBLE);

                Utils.hideViews(mShowWhenEmpty);
            }
        }
    }
}
