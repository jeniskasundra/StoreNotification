package com.tops.storenotification.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tops.storenotification.NotificationListActivity;
import com.tops.storenotification.R;
import com.tops.storenotification.model.NotificationModel;
import com.tops.storenotification.utils.TimeAgo;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private List<NotificationModel> allNotification;
    private Context context;

    public NotificationAdapter(NotificationListActivity notificationListActivity, List<NotificationModel> allNotification) {
        this.context = notificationListActivity;
        this.allNotification = allNotification;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_notification_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final NotificationModel notificationModel = allNotification.get(position);
        holder.tvTitle.setText(notificationModel.getTitle());
        holder.tvMessage.setText(notificationModel.getMessage());
        holder.tvTime.setText(new TimeAgo().timeAgo(notificationModel.getTime()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (notificationModel.getUrl()!=null) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(notificationModel.getUrl()));
                    context.startActivity(browserIntent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return allNotification.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle, tvMessage, tvTime;
        public ImageView imgNotification;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitleRow);
            tvMessage = (TextView) itemView.findViewById(R.id.tvMessageRow);
            tvTime = (TextView) itemView.findViewById(R.id.tvTimeRow);
            imgNotification = (ImageView) itemView.findViewById(R.id.imgNotificationRow);
        }
    }
}
