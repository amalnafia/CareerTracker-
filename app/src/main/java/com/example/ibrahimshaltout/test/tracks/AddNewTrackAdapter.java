package com.example.ibrahimshaltout.test.tracks;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ibrahimshaltout.test.R;
import com.example.ibrahimshaltout.test.dataclass.TrackDataClass;

import java.util.ArrayList;

public class AddNewTrackAdapter extends RecyclerView.Adapter<AddNewTrackAdapter.AddNewTrackViewHolder> {


    private Context trackContext;
    private ArrayList<TrackDataClass> trackList;

    public AddNewTrackAdapter(Context mContext, ArrayList<TrackDataClass> tracks) {
        this.trackContext = mContext;
        this.trackList = tracks;
    }


    @NonNull
    @Override
    public AddNewTrackViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(trackContext)
                .inflate(R.layout.track_recommendation_item, viewGroup, false);
        return new AddNewTrackViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull AddNewTrackViewHolder addNewTrackViewHolder, int i) {
        TrackDataClass item = trackList.get(i);

        if (item.getTrack_Name() != null) {
            addNewTrackViewHolder.trackNameNew.setText(item.getTrack_Name());
        }
        if (item.getTrack_Bio() != null) {
            addNewTrackViewHolder.trackBioNew.setText(item.getTrack_Bio());
        }
        if (item.getTrack_Rate() != null) {
            addNewTrackViewHolder.Track_Rate.setText(item.getTrack_Rate());
        }

        final String Track_ID = item.getTrackID();


        addNewTrackViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(trackContext, TrackProfileActivity.class);
                intent.putExtra("EXTRA_Track_ID", Track_ID);
                trackContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return trackList == null ? 0 : trackList.size();
    }

    class AddNewTrackViewHolder extends RecyclerView.ViewHolder {
        public TextView trackNameNew;
        public TextView trackBioNew;
        public CardView cardView;
        public TextView Track_Rate;

        AddNewTrackViewHolder(View view) {
            super(view);
            trackNameNew = (TextView) view.findViewById(R.id.name_of_track_new);
            trackBioNew = (TextView) view.findViewById(R.id.track_bio_new);
            Track_Rate = (TextView) view.findViewById(R.id.track_Rate_screen);
            cardView = (CardView) view.findViewById(R.id.card_view_track);


        }
    }
}
