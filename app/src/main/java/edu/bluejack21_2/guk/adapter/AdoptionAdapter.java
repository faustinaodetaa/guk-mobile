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
import edu.bluejack21_2.guk.util.Database;

public class AdoptionAdapter extends RecyclerView.Adapter<AdoptionAdapter.AdoptionViewHolder> {
    Context context;

    private ArrayList<Adoption> adoptions;
    private boolean isUser = false;

    public AdoptionAdapter(Context context, ArrayList<Adoption> adoptions) {
        this.context = context;
        this.adoptions = adoptions;
    }

    public AdoptionAdapter(Context context, ArrayList<Adoption> adoptions, boolean isUser) {
        this.context = context;
        this.adoptions = adoptions;
        this.isUser = isUser;
    }

    @NonNull
    @Override
    public AdoptionAdapter.AdoptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_template,parent,false);
        return new AdoptionAdapter.AdoptionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdoptionAdapter.AdoptionViewHolder holder, int position) {
        Adoption adoption = adoptions.get(position);


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
                holder.descriptionTxt.setText(Html.fromHtml(text));
            }));
        });

        if(!isUser){
            holder.viewProofBtn.setOnClickListener(view -> {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
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
            });

            if(adoption.getStatus() == 0){
                holder.approveBtn.setOnClickListener(view -> {
                    AdoptionController.changeAdoptionStatus(context, adoption, true);
                });
                holder.rejectBtn.setOnClickListener(view -> {
                    AdoptionController.changeAdoptionStatus(context, adoption, false);
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
        if(adoption.getStatus() == 1){
            color = R.color.success;
        } else if(adoption.getStatus() == 2){
            color = R.color.danger_light;
        }
        holder.adoptionCard.setCardBackgroundColor(context.getColor(color));


    }

    @Override
    public int getItemCount() {
        return adoptions.size();
    }

    public class AdoptionViewHolder extends RecyclerView.ViewHolder {

        CardView adoptionCard;
        TextView descriptionTxt;
        ImageView viewProofBtn, approveBtn, rejectBtn;

        ViewGroup leftTxt, action;

        public AdoptionViewHolder(@NonNull View itemView) {
            super(itemView);

            adoptionCard = itemView.findViewById(R.id.list_card_template);
            descriptionTxt = itemView.findViewById(R.id.list_description_txt);
            viewProofBtn = itemView.findViewById(R.id.list_view_btn);
            approveBtn = itemView.findViewById(R.id.list_approve_btn);
            rejectBtn = itemView.findViewById(R.id.list_reject_btn);

            leftTxt = itemView.findViewById(R.id.list_description_container);
            action = itemView.findViewById(R.id.list_action);

        }
    }
}
