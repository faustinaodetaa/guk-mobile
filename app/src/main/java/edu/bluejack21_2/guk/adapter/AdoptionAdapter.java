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

import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;

import edu.bluejack21_2.guk.R;
import edu.bluejack21_2.guk.controller.AdoptionController;
import edu.bluejack21_2.guk.controller.DogController;
import edu.bluejack21_2.guk.controller.UserController;
import edu.bluejack21_2.guk.model.Adoption;
import edu.bluejack21_2.guk.model.History;
import edu.bluejack21_2.guk.util.Database;

public class AdoptionAdapter extends HistoryAdapter<Adoption> {

    public AdoptionAdapter(Context context, ArrayList<Adoption> list) {
        super(context, list);
    }

    public AdoptionAdapter(Context context, ArrayList<Adoption> list, boolean isUser) {
        super(context, list, isUser);
    }

    @Override
    protected void changeStatus(Context context, Adoption curr, boolean isApproved) {
        AdoptionController.changeAdoptionStatus(context, curr, isApproved);
    }

    @Override
    protected void showViewDialog(Adoption curr, Context context, Dialog dialog) {
        Adoption adoption = curr;

        dialog.setContentView(R.layout.user_dialog);

        ImageView closeBtn = dialog.findViewById(R.id.user_dialog_close);
        ImageView userProfile = dialog.findViewById(R.id.dialog_user_profile_picture);
        TextView userNameTxt  = dialog.findViewById(R.id.dialog_user_name);
        TextView userEmailTxt = dialog.findViewById(R.id.dialog_user_email);
        TextView userPhoneTxt= dialog.findViewById(R.id.dialog_user_phone);
        TextView userAddressTxt = dialog.findViewById(R.id.dialog_user_address);;

        UserController.getUserById(adoption.getUser().getId(), (data, message) -> {
            if(data != null){
                userNameTxt.setText("Name: " + data.getName());
                userEmailTxt.setText("Email: " + data.getEmail());
                userPhoneTxt.setText("Phone Number: " + data.getPhone());
                userAddressTxt.setText("Address: " + data.getAddress());
                Database.showImage(data.getProfilePicture(), (Activity) context, userProfile);
            }
        });
        closeBtn.setOnClickListener(v -> {
            dialog.cancel();
        });

        dialog.show();
    }

    @Override
    protected void setDescriptionText(Adoption curr, TextView descriptionTxt) {
        Adoption adoption = curr;

        StringBuilder builder = new StringBuilder();
        UserController.getUserById(adoption.getUser().getId(), (data, message) -> {
            if(!isUser){
                builder.append("<b>" + data.getName() + "</b> adopted ");
            } else {
                builder.append("Adopted ");
            }

            DogController.getDogById(adoption.getDog().getId(), ((d, m) -> {
                builder.append("<b>" + d.getName() + " the " + d.getBreed() + "</b>");
                String text = builder.toString();
                if(adoption.getStatus() == 2){
                    text = "<s>" + text + "</s>";
                }
                descriptionTxt.setText(Html.fromHtml(text));
            }));
        });
    }
}
