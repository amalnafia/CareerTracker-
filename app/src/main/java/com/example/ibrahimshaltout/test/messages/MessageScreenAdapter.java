package com.example.ibrahimshaltout.test.messages;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.ibrahimshaltout.test.R;
import java.util.ArrayList;

public class MessageScreenAdapter extends RecyclerView.Adapter<MessageScreenAdapter.MessagesViewHolder> {


    private Context trackContext;
    private ArrayList<Message> messageList;

    public MessageScreenAdapter(Context mContext, ArrayList<Message> messageList) {
        this.trackContext = mContext;
        this.messageList = messageList;
    }


    @NonNull
    @Override
    public MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(trackContext)
                .inflate(R.layout.messege_item, viewGroup, false);

        return new MessagesViewHolder(itemView);    }

    @Override
    public void onBindViewHolder(@NonNull MessagesViewHolder newsFeedViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return messageList == null ? 0 : messageList.size();
    }

    class MessagesViewHolder extends RecyclerView.ViewHolder {
//        public TextView from, subject, message, iconText, timestamp;

        MessagesViewHolder(View view) {
            super(view);
//            from = (TextView) view.findViewById(R.id.from);

        }
    }
}

