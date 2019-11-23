package com.example.ibrahimshaltout.test.tracks;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.ibrahimshaltout.test.R;
import com.example.ibrahimshaltout.test.dataclass.PathDataClass;
import java.util.ArrayList;

public class PathAdapter extends RecyclerView.Adapter<PathAdapter.PathsViewHolder> {

    private Context pathContext;
    private ArrayList<PathDataClass> pathList;

    public PathAdapter(Context mContext, ArrayList<PathDataClass> list) {
        this.pathContext = mContext;
        this.pathList = list;
    }

    @NonNull
    @Override
    public PathsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(pathContext)
                .inflate(R.layout.path_item, viewGroup, false);

        return new PathAdapter.PathsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PathsViewHolder pathsViewHolder, int i) {

        pathsViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(pathContext, PathProfileActivity.class);
                pathContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pathList == null ? 0 : pathList.size();
    }

    public class PathsViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;

        public PathsViewHolder(@NonNull View itemView) {

            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view_path);
        }


    }
}

