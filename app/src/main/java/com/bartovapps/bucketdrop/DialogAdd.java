package com.bartovapps.bucketdrop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bartovapps.bucketdrop.beans.Drop;

import java.util.Calendar;

import io.realm.Realm;

/**
 * Created by BartovMoti on 08/29/16.
 */
public class DialogAdd extends DialogFragment implements View.OnClickListener {

    private static final String LOG_TAG = DialogAdd.class.getSimpleName();
    Button btAdd;
    EditText etAddDrop;
    ImageButton ibClose;
    DatePicker datePicker;

    public DialogAdd() {

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
        btAdd = (Button) view.findViewById(R.id.bt_add_drop_dialog);
        btAdd.setOnClickListener(this);
        ibClose = (ImageButton) view.findViewById(R.id.ib_close);
        ibClose.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.bt_add_drop_dialog:
                addAction();
                break;
            case R.id.ib_close:
                break;
        }
        dismiss();
    }

    private void addAction() {
        String goal = etAddDrop.getText().toString();
        String date = datePicker.getDayOfMonth() + "/" + datePicker.getMonth() + "/" + datePicker.getYear();
        Toast.makeText(getContext(), "Date selected: " + date, Toast.LENGTH_SHORT).show();
        long now = System.currentTimeMillis();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
        calendar.set(Calendar.MONTH, datePicker.getMonth());
        calendar.set(Calendar.YEAR, datePicker.getYear());
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);


        Drop drop = new Drop(goal, now, calendar.getTimeInMillis(), false);

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(drop);
        realm.commitTransaction();
        realm.close();
        Log.i(LOG_TAG, "Realm item added");

    }
}
