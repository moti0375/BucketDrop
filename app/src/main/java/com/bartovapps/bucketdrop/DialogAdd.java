package com.bartovapps.bucketdrop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * Created by BartovMoti on 08/29/16.
 */
public class DialogAdd extends DialogFragment implements View.OnClickListener{

    Button btAdd;
    EditText etAddDrop;
    ImageButton ibClose;
    DatePicker datePicker;

    public DialogAdd(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_add, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etAddDrop = (EditText) view.findViewById(R.id.et_add_a_drop);
        datePicker = (DatePicker) view.findViewById(R.id.bdv_date);
        btAdd = (Button)view.findViewById(R.id.bt_add_drop_dialog);
        btAdd.setOnClickListener(this);
        ibClose = (ImageButton) view.findViewById(R.id.ib_close);
        ibClose.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        if(view == btAdd || view == ibClose){
            dismiss();
        }

    }
}
