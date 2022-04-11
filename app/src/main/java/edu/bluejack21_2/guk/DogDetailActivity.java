package edu.bluejack21_2.guk;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import edu.bluejack21_2.guk.model.Dog;

public class DogDetailActivity extends AppCompatActivity {

    private Button adoptBtn;
    private TextView nameTxt, breedTxt, dobTxt, descriptionTxt;
    private ImageView genderIcon, dogIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_detail);

        adoptBtn = findViewById(R.id.adopt_dog_btn);

        nameTxt = findViewById(R.id.detail_dog_name);
        breedTxt = findViewById(R.id.detail_dog_breed);
        dobTxt = findViewById(R.id.detail_dog_dob);
        descriptionTxt = findViewById(R.id.detail_dog_description);

        genderIcon = findViewById(R.id.detail_gender_icon);
        dogIcon = findViewById(R.id.detail_dog_image);


        Dog dog = new Dog();

        dog = (Dog)getIntent().getSerializableExtra("dog");
        nameTxt.setText(dog.getName());
        breedTxt.setText(dog.getBreed());
//        dobTxt.setText(dog.getDob());
        descriptionTxt.setText(dog.getDescription());

    }

}
