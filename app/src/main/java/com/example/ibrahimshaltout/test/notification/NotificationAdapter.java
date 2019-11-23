package com.example.ibrahimshaltout.test.notification;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.ibrahimshaltout.test.R;
import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationsViewHolder> {

    private Context trackContext;
    private ArrayList<Notification> notificationList;

    public NotificationAdapter(Context mContext, ArrayList<Notification> notifications) {
        this.trackContext = mContext;
        this.notificationList = notifications;
    }


    @NonNull
    @Override
    public NotificationsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(trackContext)
                .inflate(R.layout.notification_item, viewGroup, false);

        return new NotificationAdapter.NotificationsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsViewHolder notificationsViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return notificationList == null ? 0 : notificationList.size();
    }

    class NotificationsViewHolder extends RecyclerView.ViewHolder {
//        public TextView from, subject, message, iconText, timestamp;

        NotificationsViewHolder(View view) {
            super(view);
//            from = (TextView) view.findViewById(R.id.from);

        }
    }
}

