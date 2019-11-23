package com.example.ibrahimshaltout.test.tracks;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ibrahimshaltout.test.R;
import com.example.ibrahimshaltout.test.dataclass.TrackDataClass;

import java.util.ArrayList;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.TracksViewHolder> {


    private Context trackContext;
    private ArrayList<TrackDataClass> trackList;

    public TrackAdapter(Context mContext, ArrayList<TrackDataClass> tracks) {
        this.trackContext = mContext;
        this.trackList = tracks;
    }


    @NonNull
    @Override
    public TracksViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(trackContext)
                .inflate(R.layout.your_track_item, viewGroup, false);

        return new TrackAdapter.TracksViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TracksViewHolder newsFeedViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return trackList == null ? 0 : trackList.size();
    }

    class TracksViewHolder extends RecyclerView.ViewHolder {
//        public TextView from, subject, message, iconText, timestamp;

        TracksViewHolder(View view) {
            super(view);
//            from = (TextView) view.findViewById(R.id.from);

        }
    }
}

