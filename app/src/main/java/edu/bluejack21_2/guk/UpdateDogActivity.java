package edu.bluejack21_2.guk;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.Timestamp;

import java.util.Calendar;
import java.util.Date;

import edu.bluejack21_2.guk.controller.DogController;
import edu.bluejack21_2.guk.model.Dog;

public class UpdateDogActivity extends AppCompatActivity {

    private Button updateDogBtn;
    private EditText nameTxt, breedTxt, descriptionTxt;
    private RadioGroup genderGroup;
    private String genderTxt;
    private DatePicker dobPicker;
    private ImageView backIcon;
    private RadioButton femaleRb, maleRb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_dog);

        updateDogBtn = findViewById(R.id.update_dog_value_btn);

        nameTxt = findViewById(R.id.update_dog_name);
        breedTxt = findViewById(R.id.update_dog_breed);
        descriptionTxt = findViewById(R.id.update_dog_description);

        genderGroup = findViewById(R.id.update_gender_rb);

        dobPicker = (DatePicker)findViewById(R.id.update_dog_dob);

        backIcon = findViewById(R.id.detail_back_icon);

        maleRb = findViewById(R.id.update_dog_gender_male);
        femaleRb = findViewById(R.id.update_dog_gender_female);

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Activity.finish();
//                onBackPressed();

            }
        });

        Dog dog = new Dog();

        dog = (Dog)getIntent().getParcelableExtra("dog");
        Log.d("coba intent3", "a: " + dog.getName());
        Log.d("coba intent3", "a: " + dog.getId());

        String dogId = dog.getId();

        nameTxt.setText(dog.getName());
        breedTxt.setText(dog.getBreed());
        descriptionTxt.setText(dog.getDescription());


        int mon =dog.getDob().toDate().getMonth();
        int dd = Integer.parseInt(dog.getDob().toDate().toString().substring(8,10));
        int yyyy = Integer.parseInt(dog.getDob().toDate().toString().substring(30,34));
//
//        Calendar cal = Calendar.getInstance();
//        cal.setTimeInMillis(System.currentTimeMillis());
//        dobPicker.init(yyyy, mon, dd, new DatePicker.OnDateChangedListener() {
//            @Override
//            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
//                Log.d("selected date of birth", i + " " +i1 + " " + i2);
//            }
//        });
//        dobPicker.setOnDateChangedListener(dog.getDob());

//        dobPicker.init(2022, 4, 4, null);
        dobPicker.updateDate(yyyy, mon, dd);


        genderTxt = dog.getGender().equals("Male") ? "Male" : "Female";

        Log.d("gender anjing", genderTxt);

        if(genderTxt.equals("Male")){
            maleRb.setChecked(true);
        }else{
            femaleRb.setChecked(true);
        }

        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i){
                    case R.id.update_dog_gender_female:
                        genderTxt = "Female";
                        break;
                    case R.id.update_dog_gender_male:
                        genderTxt = "Male";
                        break;
                }
            }
        });

        updateDogBtn.setOnClickListener(view -> {
            Date date = getDateFromDatePicker(dobPicker);
            String name = nameTxt.getText().toString();
            String breed = breedTxt.getText().toString();
            String description = descriptionTxt.getText().toString();
            Timestamp dob = new Timestamp(date);
            String gender = genderTxt;

            DogController.updateDog(dogId, breed, description, dob, gender, name);
            Toast.makeText(this, "Updated dog", Toast.LENGTH_SHORT).show();
//            UpdateDogActivity.finish();


        });




    }

    public static java.util.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }


}