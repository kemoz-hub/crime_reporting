package com.example.crimereporting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class personAdapter extends FirebaseRecyclerAdapter<missingFire,personAdapter.MyviewHolder> {

    public personAdapter(@NonNull FirebaseRecyclerOptions<missingFire> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull personAdapter.MyviewHolder holder, int position, @NonNull missingFire model) {
        holder.reportedby.setText(model.getFullName());
        Picasso.get()
                .load(model.getUri())
                .into(holder.image);
    }

    @NonNull
    @Override
    public personAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.missing_peson_entry,parent,false);
        return  new MyviewHolder(view);
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView reportedby,seenby;
        ImageView image;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            reportedby=itemView.findViewById(R.id.textreportedby);
            seenby=itemView.findViewById(R.id.textsportedby);
            image=itemView.findViewById(R.id.imageeviM);


        }
    }
}
