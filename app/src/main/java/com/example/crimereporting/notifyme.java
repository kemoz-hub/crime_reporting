package com.example.crimereporting;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class notifyme extends ArrayAdapter {
    private Activity context;
    List<citizensnotifications> notifyfirebases;
    public notifyme(Activity context, List<citizensnotifications> notifyfirebases) {
        super(context, R.layout.notificationentry, notifyfirebases);
        this.context = context;
        this.notifyfirebases = notifyfirebases;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listItem = inflater.inflate(R.layout.notificationentry, null, true);

        TextView idno = listItem.findViewById(R.id.messageN);


       citizensnotifications cmm = notifyfirebases.get(position);

        idno.setText(cmm.getNotification());


        return listItem;

    }

}
