package edu.bluejack21_2.guk.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.bluejack21_2.guk.R;
import edu.bluejack21_2.guk.controller.DonationController;
import edu.bluejack21_2.guk.controller.UserController;
import edu.bluejack21_2.guk.model.Donation;
import edu.bluejack21_2.guk.util.Database;

public class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.DonationViewHolder>{

    Context context;

    private ArrayList<Donation> donations;
    private boolean isUser = false;

    public DonationAdapter(Context context, ArrayList<Donation> donations) {
        this.context = context;
        this.donations = donations;
    }

    public DonationAdapter(Context context, ArrayList<Donation> donations, boolean isUser) {
        this.context = context;
        this.donations = donations;
        this.isUser = isUser;
    }

    @NonNull
    @Override
    public DonationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_template,parent,false);
        return new DonationViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DonationViewHolder holder, int position) {
        Donation donation = donations.get(position);

        StringBuilder builder = new StringBuilder();
        UserController.getUserById(donation.getUser().getId(), (data, message) -> {
            if(!isUser){
                builder.append("<b>" + data.getName() + "</b> donated ");
            } else {
                builder.append("Donated ");
            }
            String amountTxt = String.format("IDR %,d", donation.getAmount());
            builder.append("<b>" + amountTxt + "</b>");
            String text = builder.toString();
            if(donation.getStatus() == 2){
                text = "<s>" + text + "</s>";
            }
            holder.descriptionTxt.setText(Html.fromHtml(text));
        });

        if(!isUser){
            holder.viewProofBtn.setOnClickListener(view -> {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.image_dialog);

                ImageView closeBtn = dialog.findViewById(R.id.image_dialog_close);
                ImageView proofPic = dialog.findViewById(R.id.image_dialog_image);

                Database.showImage(donation.getProofPic(), (Activity) context, proofPic);

                closeBtn.setOnClickListener(v -> {
                    dialog.cancel();
                });

                dialog.show();
            });

            if(donation.getStatus() == 0){
                holder.approveBtn.setOnClickListener(view -> {
                    DonationController.changeDonationStatus(context, donation, true);
                });
                holder.rejectBtn.setOnClickListener(view -> {
                    DonationController.changeDonationStatus(context, donation, false);
                });

            } else {
                holder.approveBtn.setVisibility(View.GONE);
                holder.rejectBtn.setVisibility(View.GONE);

                int marginInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, context.getResources().getDisplayMetrics());

                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) holder.leftTxt.getLayoutParams();
                params.setMargins(0, 0, marginInDp, 0);

                holder.leftTxt.setLayoutParams(params);
            }
        } else {
            holder.action.setVisibility(View.GONE);
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) holder.leftTxt.getLayoutParams();
            params.setMargins(0, 0, 0, 0);

            holder.leftTxt.setLayoutParams(params);
        }

        int color = R.color.white;
        if(donation.getStatus() == 1){
            color = R.color.success;
        } else if(donation.getStatus() == 2){
            color = R.color.danger_light;
        }
        holder.background.setCardBackgroundColor(context.getColor(color));

    }

    @Override
    public int getItemCount() {
        return donations.size();
    }

    public class DonationViewHolder extends RecyclerView.ViewHolder {

        CardView background;
        TextView descriptionTxt;
        ImageView viewProofBtn;
        ImageView approveBtn, rejectBtn;

        ViewGroup leftTxt, action;

        public DonationViewHolder(@NonNull View itemView) {
            super(itemView);

            background = itemView.findViewById(R.id.list_card_template);
            descriptionTxt = itemView.findViewById(R.id.list_description_txt);
            viewProofBtn = itemView.findViewById(R.id.list_view_btn);
            approveBtn = itemView.findViewById(R.id.list_approve_btn);
            rejectBtn = itemView.findViewById(R.id.list_reject_btn);

            leftTxt = itemView.findViewById(R.id.list_description_container);
            action = itemView.findViewById(R.id.list_action);
        }
    }
}
