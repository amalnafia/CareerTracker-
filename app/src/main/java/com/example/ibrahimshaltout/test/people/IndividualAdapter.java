package com.example.ibrahimshaltout.test.people;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ibrahimshaltout.test.R;
import com.example.ibrahimshaltout.test.dataclass.IndividualDataClass;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class IndividualAdapter extends RecyclerView.Adapter<IndividualAdapter.PeopleViewHolder> {


    private Context peopleContext;
    private ArrayList<IndividualDataClass> PeopleIconList;

    public IndividualAdapter(Context peopleContext, ArrayList<IndividualDataClass> PeopleIconList) {
        this.peopleContext = peopleContext;
        this.PeopleIconList = PeopleIconList;
    }

    @NonNull
    @Override
    public PeopleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(peopleContext).inflate(R.layout.people_item, viewGroup, false);

        return new PeopleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleViewHolder peopleViewHolder, int position) {

        final IndividualDataClass item = PeopleIconList.get(position);
        peopleViewHolder.people_name.setText(item.getUser_name());
        if (item.getUserBio() == null) {
            peopleViewHolder.people_bio.setText(item.getCollegeName());
        } else {
            peopleViewHolder.people_bio.setText(item.getUserBio());
        }
        if (item.getUser_image() != null) {
            Picasso.get()
                    .load(item.getUser_image())
                    .into(peopleViewHolder.people_image);        }

    }

    @Override
    public int getItemCount() {
        return PeopleIconList == null ? 0 : PeopleIconList.size();
    }


    public class PeopleViewHolder extends RecyclerView.ViewHolder {


        public TextView people_name, people_bio;
        public Button people_follow_btn;
        public CircleImageView people_image;

        public PeopleViewHolder(@NonNull View itemView) {
            super(itemView);

            people_name = itemView.findViewById(R.id.people_name);
            people_bio = itemView.findViewById(R.id.people_bio);
            people_follow_btn = itemView.findViewById(R.id.people_follow_btn);
            people_image = itemView.findViewById(R.id.people_image);

        }
    }
}
