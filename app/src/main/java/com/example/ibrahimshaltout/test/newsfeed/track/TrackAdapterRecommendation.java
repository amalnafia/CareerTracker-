package com.example.ibrahimshaltout.test.newsfeed.track;

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
import com.example.ibrahimshaltout.test.tracks.TrackProfileActivity;

import java.util.ArrayList;

public class TrackAdapterRecommendation extends RecyclerView.Adapter<TrackAdapterRecommendation.NewsFeedTrackViewHolder> {

    private Context trackContext;
    private ArrayList<TrackDataClass> trackList;

    public TrackAdapterRecommendation(Context mContext, ArrayList<TrackDataClass> tracks) {
        this.trackContext = mContext;
        this.trackList = tracks;
    }


    @NonNull
    @Override
    public NewsFeedTrackViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(trackContext)
                .inflate(R.layout.track_bar_item, viewGroup, false);
        return new NewsFeedTrackViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsFeedTrackViewHolder newsFeedViewHolder, int position) {
        TrackDataClass item = trackList.get(position);

        if (item.getTrack_Name() != null) {
            newsFeedViewHolder.trackName.setText(item.getTrack_Name());
        }
        if (item.getTrack_Bio() != null) {
            newsFeedViewHolder.trackBio.setText(item.getTrack_Bio());
        }
        if (item.getTrack_Rate() != null) {
            newsFeedViewHolder.Track_Rate.setText(item.getTrack_Rate());
        }

        final String Track_ID = item.getTrackID();
        newsFeedViewHolder.card_view_Track_Recommendation.setOnClickListener(new View.OnClickListener() {
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

    class NewsFeedTrackViewHolder extends RecyclerView.ViewHolder {
        public TextView trackName;
        public TextView trackBio;
        public TextView Track_Rate;
        public CardView card_view_Track_Recommendation;

        NewsFeedTrackViewHolder(View view) {
            super(view);
            trackName = (TextView) view.findViewById(R.id.name_of_track);
            trackBio = (TextView) view.findViewById(R.id.track_bio);
            Track_Rate = (TextView) view.findViewById(R.id.Track_Rate);
            card_view_Track_Recommendation = (CardView) view.findViewById(R.id.card_view_Track_Recommendation);

        }
    }
}
