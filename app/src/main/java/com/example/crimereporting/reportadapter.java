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

public class reportadapter extends ArrayAdapter {
    private Activity context;
    List<complaintsfirebase>complaintsfirebase;

    public reportadapter(Activity context,List<complaintsfirebase>complaintsfirebase){
        super(context,R.layout.reports,complaintsfirebase);
        this.context=context;
        this.complaintsfirebase=complaintsfirebase;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listItem=inflater.inflate(R.layout.reports,null,true);

        TextView idno=listItem.findViewById(R.id.idnumR);
        TextView DOC=listItem.findViewById(R.id.DOCR);
        TextView TOC=listItem.findViewById(R.id.TOC);
        TextView gender=listItem.findViewById(R.id.genderR);
        TextView obno=listItem.findViewById(R.id.OBnumR);
        TextView status=listItem.findViewById(R.id.StatusR);
        TextView phone=listItem.findViewById(R.id.phoneR);

        complaintsfirebase cmm=complaintsfirebase.get(position);

        idno.setText(cmm.getId_number());
        DOC.setText(cmm.getDOC());
        TOC.setText(cmm.getTOC());
        gender.setText(cmm.getGender());
        obno.setText(cmm.getOBnumber());
        status.setText(cmm.getStatus());
        phone.setText(cmm.getPhone());

        return listItem;

    }
}
