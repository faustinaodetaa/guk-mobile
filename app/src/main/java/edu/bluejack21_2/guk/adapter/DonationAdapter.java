package edu.bluejack21_2.guk.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.bluejack21_2.guk.R;
import edu.bluejack21_2.guk.controller.DonationController;
import edu.bluejack21_2.guk.controller.UserController;
import edu.bluejack21_2.guk.model.Donation;
import edu.bluejack21_2.guk.model.History;
import edu.bluejack21_2.guk.util.Database;

public class DonationAdapter extends HistoryAdapter<Donation>{

    public DonationAdapter(Context context, ArrayList<Donation> list) {
        super(context, list);
    }

    public DonationAdapter(Context context, ArrayList<Donation> list, boolean isUser) {
        super(context, list, isUser);
    }

    @Override
    protected void changeStatus(Context context, Donation curr, boolean isApproved) {
        DonationController.changeDonationStatus(context, curr, isApproved);
    }

    @Override
    protected void showViewDialog(Donation curr, Context context, Dialog dialog) {
        Donation donation = curr;

        dialog.setContentView(R.layout.image_dialog);

        ImageView closeBtn = dialog.findViewById(R.id.image_dialog_close);
        ImageView proofPic = dialog.findViewById(R.id.image_dialog_image);

        Database.showImage(donation.getProofPic(), (Activity) context, proofPic);

        closeBtn.setOnClickListener(v -> {
            dialog.cancel();
        });

        dialog.show();
    }

    @Override
    protected void setDescriptionText(Donation curr, TextView descriptionTxt) {
        Donation donation = curr;

        StringBuilder builder = new StringBuilder();
        String donated = context.getResources().getString(R.string.donated);
        String donatedLowercase = context.getResources().getString(R.string.donated_lowercase);

        UserController.getUserById(donation.getUser().getId(), (data, message) -> {
            if(!isUser){
                builder.append("<b>" + data.getName() + "</b> " + donatedLowercase + " ");
            } else {
                builder.append(donated + " ");
            }
            String amountTxt = String.format("IDR %,d", donation.getAmount());
            builder.append("<b>" + amountTxt + "</b>");
            String text = builder.toString();
            if(donation.getStatus() == 2){
                text = "<s>" + text + "</s>";
            }
            descriptionTxt.setText(Html.fromHtml(text));
        });
    }
}
