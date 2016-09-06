package com.bartovapps.bucketdrop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.bartovapps.bucketdrop.adapters.CompleteListener;

/**
 * Created by BartovMoti on 09/04/16.
 */
public class DialogChecked extends DialogFragment implements View.OnClickListener{

    Button bt_chekced;
    ImageButton ib_close;
    CompleteListener mCompleteListener;

    public DialogChecked() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_checked, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bt_chekced = (Button) view.findViewById(R.id.bt_checked);
        bt_chekced.setOnClickListener(this);
        ib_close = (ImageButton) view.findViewById(R.id.ib_close);
        ib_close.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        switch (viewId){
            case R.id.ib_close:
                break;
            case R.id.bt_checked:
                markAsComplete();
                break;
        }
        dismiss();

    }

    private void markAsComplete() {
        Bundle bundle = getArguments();
        if(bundle != null && mCompleteListener != null){
            int position = bundle.getInt("pos");
            mCompleteListener.onComplete(position);
        }

    }

    public void setCompleteListener(CompleteListener completeListener) {
        mCompleteListener = completeListener;
    }
}
