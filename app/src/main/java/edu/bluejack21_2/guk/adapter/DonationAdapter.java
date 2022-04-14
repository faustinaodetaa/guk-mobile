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
        View v = LayoutInflater.from(context).inflate(R.layout.donation_template,parent,false);
        return new DonationViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DonationViewHolder holder, int position) {
        Donation donation = donations.get(position);

        UserController.getUserById(donation.getUser().getId(), (data, message) -> {
            holder.titleNameTxt.setText(data.getName());
        });

        String amountTxt = String.format("IDR %,d", donation.getAmount());
        holder.titleAmountTxt.setText(amountTxt);

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
                holder.titleAmountTxt.setText(Html.fromHtml("<s>" + amountTxt + "</s>"));
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
        TextView titleNameTxt, titleAmountTxt;
        ImageView viewProofBtn;
        ImageView approveBtn, rejectBtn;

        public DonationViewHolder(@NonNull View itemView) {
            super(itemView);

            background = itemView.findViewById(R.id.donation_card);
            titleNameTxt = itemView.findViewById(R.id.donate_title_name_txt);
            titleAmountTxt = itemView.findViewById(R.id.donate_title_amount_txt);
            viewProofBtn = itemView.findViewById(R.id.donate_view_proof_btn);
            approveBtn = itemView.findViewById(R.id.donate_approve_btn);
            rejectBtn = itemView.findViewById(R.id.donate_reject_btn);

        }
    }
}
