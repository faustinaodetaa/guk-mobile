package edu.bluejack21_2.guk.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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

    public DonationAdapter(Context context, ArrayList<Donation> donations) {
        this.context = context;
        this.donations = donations;
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
            builder.append("<b>" + data.getName() + "</b>");
            builder.append(" donated ");
            String amountTxt = String.format("IDR %,d", donation.getAmount());
            builder.append("<b>" + amountTxt + "</b>");
            String text = builder.toString();
            if(donation.getStatus() == 2){
                text = "<s>" + text + "</s>";
            }
            holder.descriptionTxt.setText(Html.fromHtml(text));
        });


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
            int color;
            if(donation.getStatus() == 1){
                color = R.color.success;
            } else {
                color = R.color.danger_light;
            }
            holder.background.setCardBackgroundColor(context.getColor(color));
            holder.approveBtn.setVisibility(View.GONE);
            holder.rejectBtn.setVisibility(View.GONE);
        }

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

        ViewGroup leftTxt;

        public DonationViewHolder(@NonNull View itemView) {
            super(itemView);

            background = itemView.findViewById(R.id.list_card_template);
            descriptionTxt = itemView.findViewById(R.id.list_description_txt);
            viewProofBtn = itemView.findViewById(R.id.list_view_btn);
            approveBtn = itemView.findViewById(R.id.list_approve_btn);
            rejectBtn = itemView.findViewById(R.id.list_reject_btn);

            leftTxt = itemView.findViewById(R.id.list_description_container);

        }
    }
}
