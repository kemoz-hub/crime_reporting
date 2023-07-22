package com.example.crimereporting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class citizenstatus extends FirebaseRecyclerAdapter<complaintsfirebase,citizenstatus.Myviewholder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public citizenstatus(@NonNull FirebaseRecyclerOptions<complaintsfirebase> options) {
        super(options);
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.citizenstatus,parent,false);
        return new Myviewholder(view);
    }


    @Override
    protected void onBindViewHolder(@NonNull Myviewholder holder, int position, @NonNull complaintsfirebase model) {
        holder.Name.setText(model.getPhone());
        holder.idno.setText(model.getId_number());
        holder.Email.setText(model.getEmail());
        holder.gender.setText(model.getGender());
        holder.DOC.setText(model.getDOC());
        holder.TOC.setText(model.getTOC());
        holder.OBnumber.setText(model.getOBnumber());
        holder.status.setText(model.getStatus());
    }



    public static class Myviewholder extends RecyclerView.ViewHolder {
        TextView Name,idno,Email,gender,DOC,TOC,OBnumber,status;
        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            Name=itemView.findViewById(R.id.textname3);
            idno=itemView.findViewById(R.id.textidno2);
            Email=itemView.findViewById(R.id.textemail2);
            gender=itemView.findViewById(R.id.textgender2);
            DOC=itemView.findViewById(R.id.textdate2);
            TOC=itemView.findViewById(R.id.texttype2);
            OBnumber=itemView.findViewById(R.id.textobnumber2);
            status=itemView.findViewById(R.id.textstatus2);
        }
    }
}
