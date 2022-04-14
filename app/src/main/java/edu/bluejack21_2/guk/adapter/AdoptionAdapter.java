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

import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;

import edu.bluejack21_2.guk.R;
import edu.bluejack21_2.guk.controller.AdoptionController;
import edu.bluejack21_2.guk.controller.DogController;
import edu.bluejack21_2.guk.controller.DonationController;
import edu.bluejack21_2.guk.controller.UserController;
import edu.bluejack21_2.guk.model.Adoption;
import edu.bluejack21_2.guk.model.Donation;
import edu.bluejack21_2.guk.util.Database;

public class AdoptionAdapter  extends RecyclerView.Adapter<AdoptionAdapter.AdoptionViewHolder> {
    Context context;

    private ArrayList<Adoption> adoptions;

    public AdoptionAdapter(Context context, ArrayList<Adoption> adoptions) {
        this.context = context;
        this.adoptions = adoptions;
    }

    @NonNull
    @Override
    public AdoptionAdapter.AdoptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.adoption_template,parent,false);
        return new AdoptionAdapter.AdoptionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdoptionAdapter.AdoptionViewHolder holder, int position) {
        Adoption adoption = adoptions.get(position);

        UserController.getUserById(adoption.getUser().getId(), (data, message) -> {
            holder.titleNameTxt.setText(data.getName());
        });

        DogController.getDogById(adoption.getDog().getId(), ((data, message) -> {
            holder.titleDogTxt.setText(data.getName() + " the " + data.getBreed());
        }));


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
            int color;
            if(adoption.getStatus() == 1){
                color = R.color.success;
            } else {
                color = R.color.danger_light;
            }
            holder.adoptionCard.setCardBackgroundColor(context.getColor(color));
            holder.approveBtn.setVisibility(View.GONE);
            holder.rejectBtn.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return adoptions.size();
    }

    public class AdoptionViewHolder extends RecyclerView.ViewHolder {

        CardView adoptionCard;
        TextView titleNameTxt, titleDogTxt, testing;
        ImageView viewProofBtn, approveBtn, rejectBtn;

        public AdoptionViewHolder(@NonNull View itemView) {
            super(itemView);

            adoptionCard = itemView.findViewById(R.id.adoption_card);
            titleNameTxt = itemView.findViewById(R.id.adopt_title_name_txt);
            titleDogTxt = itemView.findViewById(R.id.adopt_title_dog_txt);
            viewProofBtn = itemView.findViewById(R.id.adopt_view_proof_btn);
            approveBtn = itemView.findViewById(R.id.adopt_approve_btn);
            rejectBtn = itemView.findViewById(R.id.adopt_reject_btn);



        }
    }
}
