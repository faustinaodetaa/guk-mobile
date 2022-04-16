package edu.bluejack21_2.guk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.bluejack21_2.guk.R;
import edu.bluejack21_2.guk.model.Notification;
import edu.bluejack21_2.guk.model.User;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private Context context;
    private ArrayList<Notification> notifications;

    public NotificationAdapter(Context context, ArrayList<Notification> notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public NotificationAdapter.NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.notification_template,parent,false);
        return new NotificationViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.NotificationViewHolder holder, int position) {
        Notification notification = notifications.get(position);

        holder.descriptionTxt.setText(notification.getContent());

        String mon = notification.getCreatedAt().toDate().toString().substring(4,7);
        String dd = notification.getCreatedAt().toDate().toString().substring(8,10);
        String yyyy = notification.getCreatedAt().toDate().toString().substring(30,34);

        String date = mon + " " + dd + ", " + yyyy;

        holder.dateTxt.setText(date);

        int icon;
        if(notification.getType().equals("donation")){
            icon = R.drawable.ic_donate;
        } else if(notification.getType().equals("adoption")){
            icon = R.drawable.main_logo;
        } else {
            icon = R.drawable.badge_1;
            int point = Integer.parseInt(notification.getType().split("-")[1]);
            holder.notificationIcon.setColorFilter(ContextCompat.getColor(context, User.getBadgeColor(point)));
        }
        holder.notificationIcon.setImageDrawable(context.getDrawable(icon));

    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        ImageView notificationIcon;
        TextView descriptionTxt, dateTxt;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationIcon = itemView.findViewById(R.id.notification_icon);
            descriptionTxt = itemView.findViewById(R.id.notification_description_txt);
            dateTxt = itemView.findViewById(R.id.notification_date_txt);
        }
    }
}
