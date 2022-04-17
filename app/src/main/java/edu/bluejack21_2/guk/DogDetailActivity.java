package edu.bluejack21_2.guk;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;

import edu.bluejack21_2.guk.controller.AdoptionController;
import edu.bluejack21_2.guk.controller.DogController;
import edu.bluejack21_2.guk.model.Adoption;
import edu.bluejack21_2.guk.model.Dog;
import edu.bluejack21_2.guk.model.User;
import edu.bluejack21_2.guk.util.Database;

public class DogDetailActivity extends BaseActivity {

    private Button adoptDogBtn, deleteDogBtn, updateDogBtn;
    private TextView nameTxt, breedTxt, dobTxt, descriptionTxt, rescuedDateTxt, statusTxt;
    private ImageView genderIcon, dogIcon, backIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_detail);
        adoptDogBtn = findViewById(R.id.adopt_dog_btn);
        deleteDogBtn = findViewById(R.id.delete_dog_btn);
        updateDogBtn = findViewById(R.id.update_dog_btn);


        nameTxt = findViewById(R.id.detail_dog_name);
        breedTxt = findViewById(R.id.detail_dog_breed);
        dobTxt = findViewById(R.id.detail_dog_dob);
        descriptionTxt = findViewById(R.id.detail_dog_description);
        rescuedDateTxt = findViewById(R.id.detail_rescued_date);
        statusTxt = findViewById(R.id.detail_dog_status);

        genderIcon = findViewById(R.id.detail_gender_icon);
        dogIcon = findViewById(R.id.detail_dog_image);
        backIcon = findViewById(R.id.detail_back_icon);



        backIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(DogDetailActivity.this, HomeActivity.class));
                finish();
            }
        });

        final Dog dog = (Dog)getIntent().getParcelableExtra("dog");
//        Log.d("coba intent2", "a: " + dog.getName());
//        Log.d("coba intent2", "a: " + dog.getId());

        adoptDogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(view.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.tnc_dialog);

                ImageView closeBtn = dialog.findViewById(R.id.tnc_dialog_close);
                Button tncAdoptDogBtn = dialog.findViewById(R.id.tnc_adopt_dog_btn);
                CheckBox tncCheckBox = dialog.findViewById(R.id.tnc_agree);

                closeBtn.setOnClickListener(v -> {
                    dialog.cancel();
                });
                tncAdoptDogBtn.setOnClickListener(v -> {
                    if(!tncCheckBox.isChecked()){
                        Toast.makeText(DogDetailActivity.this, "You must agree with the tnc!", Toast.LENGTH_SHORT).show();
                    }else{
//                        Toast.makeText(DogDetailActivity.this, "agreed", Toast.LENGTH_SHORT).show();
                        AdoptionController.insertAdoption(DogDetailActivity.this, dog);
                        finish();
                        dialog.cancel();
                    }
                });

                dialog.show();
            }
        });

        deleteDogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d("ini dog id", dog.getId());

                DogController.deleteDog(dog.getId());
                startActivity(new Intent(DogDetailActivity.this, HomeActivity.class));
            }
        });

        updateDogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DogDetailActivity.this, UpdateDogActivity.class);
//                Log.d("coba intent update", "onClick: " + dog.getName());

                intent.putExtra("dog", dog);
                startActivity(intent);

//                startActivity(new Intent(DogDetailActivity.this, UpdateDogActivity.class));

            }
        });

        String mon = dog.getDob().toDate().toString().substring(4,7);
        String dd = dog.getDob().toDate().toString().substring(8,10);
        String yyyy = dog.getDob().toDate().toString().substring(30,34);

        String dob = mon + " " + dd + ", " + yyyy;

        String mon2 = dog.getRescuedDate().toDate().toString().substring(4,7);
        String dd2 = dog.getRescuedDate().toDate().toString().substring(8,10);
        String yyyy2 = dog.getRescuedDate().toDate().toString().substring(30,34);

        String rescuedDate = mon2 + " " + dd2 + ", " + yyyy2;

        genderIcon.setImageResource(dog.getGender().equals("Male") ? R.drawable.gender_male : R.drawable.gender_female);

        nameTxt.setText(dog.getName());
        breedTxt.setText(dog.getBreed());
        dobTxt.setText(dob);
        descriptionTxt.setText(dog.getDescription());
        rescuedDateTxt.setText(rescuedDate);
        statusTxt.setText(dog.getStatus());

        Database.showImage(dog.getPicture(), this, dogIcon);

        if(User.CURRENT_USER.getRole().equals("admin")){
            deleteDogBtn.setVisibility(View.VISIBLE);
            updateDogBtn.setVisibility(View.VISIBLE);
            adoptDogBtn.setVisibility(View.GONE);
        }else{
            deleteDogBtn.setVisibility(View.GONE);
            updateDogBtn.setVisibility(View.GONE);
            adoptDogBtn.setVisibility(View.VISIBLE);
        }

    }
}