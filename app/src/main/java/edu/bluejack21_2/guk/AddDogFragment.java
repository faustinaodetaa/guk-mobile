package edu.bluejack21_2.guk;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.Timestamp;

import java.io.IOException;
import java.util.ArrayList;

import edu.bluejack21_2.guk.controller.BreedController;
import edu.bluejack21_2.guk.controller.DogController;
import edu.bluejack21_2.guk.controller.UserController;
import edu.bluejack21_2.guk.model.Breed;
import edu.bluejack21_2.guk.model.Dog;

public class AddDogFragment extends Fragment {

    private Spinner spinner;
    private Button addDogBtn;
    private EditText nameTxt, breedTxt, dobTxt, descriptionTxt, rescuedDateTxt;
    private CardView choosePicBtn;
    private ImageView imageView;
    private RadioGroup genderGroup;
    private String genderTxt;

    private Uri filePath;

    public AddDogFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_dog, container, false);

        ArrayList<Breed> breedList;
        breedList = new ArrayList<>();
        String[] items = new String[]{"1", "2", "three"};
        choosePicBtn = view.findViewById(R.id.add_dog_choose_btn);

        imageView = view.findViewById(R.id.add_dog_profile_picture);
        addDogBtn = view.findViewById(R.id.add_dog_btn);
        nameTxt = view.findViewById(R.id.add_dog_name_txt);
        breedTxt = view.findViewById(R.id.add_dog_breed_txt);
        descriptionTxt = view.findViewById(R.id.add_dog_description_txt);

        genderGroup = view.findViewById(R.id.gender_rb);

        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i){
                    case R.id.dog_gender_female:
                        genderTxt = "Female";
                        break;
                    case R.id.dog_gender_male:
                        genderTxt = "Male";
                        break;
                }
            }
        });

        Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();

        ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null && result.getData().getData() != null) {
                            filePath = result.getData().getData();
//                        Log.d("coba", "onActivityResult: " + filePath.toString().substring(filePath.toString().lastIndexOf(".") + 1));
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                                imageView.setImageBitmap(bitmap);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });


        choosePicBtn.setOnClickListener(view2 -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            resultLauncher.launch(Intent.createChooser(intent,"Select Image from here..."));
        });


            addDogBtn.setOnClickListener(view1 -> {
                String name = nameTxt.getText().toString();
                String breed = breedTxt.getText().toString();
                String description = descriptionTxt.getText().toString();
                Timestamp dob = Timestamp.now();
                Timestamp rescuedDate = Timestamp.now();
                String gender = genderTxt;
                String status = "Unadopted";

                if(DogController.insertDog(getActivity(), name, breed, description, dob, rescuedDate, gender, status, filePath)){
                    Toast.makeText(getActivity(), "Insert Success", Toast.LENGTH_SHORT).show();
                }
            });



        return view;
    }
}