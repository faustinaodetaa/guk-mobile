package edu.bluejack21_2.guk;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.Timestamp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import edu.bluejack21_2.guk.controller.DogController;
import edu.bluejack21_2.guk.controller.UserController;
import edu.bluejack21_2.guk.model.Dog;
import edu.bluejack21_2.guk.util.ActivityHelper;
import edu.bluejack21_2.guk.util.Database;

public class AddDogFragment extends Fragment {

    private Spinner spinner;
    private Button addDogBtn;
    private EditText nameTxt, breedTxt, dobTxt, descriptionTxt, rescuedDateTxt;
    private CardView choosePicBtn;
    private ImageView imageView;
    private RadioGroup genderGroup;
    private String genderTxt;
    private DatePicker dobPicker;

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

//        ArrayList<Breed> breedList;
//        breedList = new ArrayList<>();
//        String[] items = new String[]{"1", "2", "three"};
        choosePicBtn = view.findViewById(R.id.add_dog_choose_btn);

        imageView = view.findViewById(R.id.add_dog_profile_picture);
        addDogBtn = view.findViewById(R.id.add_dog_btn);
        nameTxt = view.findViewById(R.id.add_dog_name_txt);
        breedTxt = view.findViewById(R.id.add_dog_breed_txt);
        descriptionTxt = view.findViewById(R.id.add_dog_description_txt);
        dobPicker = view.findViewById(R.id.add_dog_dob);


        genderGroup = view.findViewById(R.id.gender_rb);

        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
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
            resultLauncher.launch(Intent.createChooser(intent, "Select Image from here..."));
        });


        addDogBtn.setOnClickListener(view1 -> {
//            SEEDING
//            String[] names = {"Abby", "Ace", "Addie", "Adele", "Annie", "Apollo", "Aspen", "Bailey", "Beamer", "Bear", "Belle", "Bella", "Birdie", "Bling", "Blue", "Bogey", "Body", "Boomer", "Bowen", "Breeze", "Brie", "Brody", "Buzz", "Callaway", "Casey", "Cash", "Catcher", "Chaos", "Chase", "Chili", "CiCi", "Cody", "Cole", "Comet", "Cooper", "Cruise", "Crush", "Daisy", "Dare", "Dash", "Dawson", "Dazzle", "Demi", "Denali", "Diva", "Dixie", "Echo", "Eli", "Ellie", "Emmy", "Evie", "Finn", "Flash", "Frankie", "Frisco", "Gator", "Georgia", "Ginger", "Grace", "Haley", "Happy", "Harley", "Hattie", "Hope", "Hunter", "Indy", "Jack", "Jamie", "Jax", "Jazz", "Jenna", "Jersey", "Jet", "Jinx", "JoJo", "Josie", "Joy", "Juno", "Karma", "Kenzi", "Kiva", "Kona", "Kyra", "Lacie", "Lark", "Laser", "Latte", "Levi", "Lilly", "Linx", "Logan", "Lucy", "Luke", "Max", "Mia", "Mojo", "Molly", "Murphy", "Nike", "Nova", "Obie", "Ollie", "Peach", "Penny", "Pepper", "Piper", "Prada", "Ranger", "Raven", "Reggie", "Remington", "Riley", "Ripley", "Riot", "River", "Roxie", "Ruby", "Rumor", "Salsa", "Scarlett", "Scout", "Shadow", "Shiloh", "Skye", "Slater", "Sophie", "Spark", "Spencer", "Spirit", "Spring", "Star", "Storm", "Strider", "Summer", "Tally", "Tango", "Tank", "Taylor", "Tease", "Tessa", "Token", "Tori", "Tripp", "Trooper", "Tucker", "Tux", "Whip", "Wyatt", "Zeke", "Zip"};
//            String[] breeds = {"Affenpinscher", "Afghan Hound", "Airedale Terrier", "Akita", "Alaskan Malamute", "American Staffordshire Terrier", "American Water Spaniel", "Australian Cattle Dog", "Australian Shepherd", "Australian Terrier", "Basenji", "Basset Hound", "Beagle", "Bearded Collie", "Bedlington Terrier", "Bernese Mountain Dog", "Bichon Frise", "Black And Tan Coonhound", "Bloodhound", "Border Collie", "Border Terrier", "Borzoi", "Boston Terrier", "Bouvier Des Flandres", "Boxer", "Briard", "Brittany", "Brussels Griffon", "Bull Terrier", "Bulldog", "Bullmastiff", "Cairn Terrier", "Canaan Dog", "Chesapeake Bay Retriever", "Chihuahua", "Chinese Crested", "Chinese Shar-Pei", "Chow Chow", "Clumber Spaniel", "Cocker Spaniel", "Collie", "Curly-Coated Retriever", "Dachshund", "Dalmatian", "Doberman Pinscher", "English Cocker Spaniel", "English Setter", "English Springer Spaniel", "English Toy Spaniel", "Eskimo Dog", "Finnish Spitz", "Flat-Coated Retriever", "Fox Terrier", "Foxhound", "French Bulldog", "German Shepherd", "German Shorthaired Pointer", "German Wirehaired Pointer", "Golden Retriever", "Gordon Setter", "Great Dane", "Greyhound", "Irish Setter", "Irish Water Spaniel", "Irish Wolfhound", "Jack Russell Terrier", "Japanese Spaniel", "Keeshond", "Kerry Blue Terrier", "Komondor", "Kuvasz", "Labrador Retriever", "Lakeland Terrier", "Lhasa Apso", "Maltese", "Manchester Terrier", "Mastiff", "Mexican Hairless", "Newfoundland", "Norwegian Elkhound", "Norwich Terrier", "Otterhound", "Papillon", "Pekingese", "Pointer", "Pomeranian", "Poodle", "Pug", "Puli", "Rhodesian Ridgeback", "Rottweiler", "Saint Bernard", "Saluki", "Samoyed", "Schipperke", "Schnauzer", "Scottish Deerhound", "Scottish Terrier", "Sealyham Terrier", "Shetland Sheepdog", "Shih Tzu", "Siberian Husky", "Silky Terrier", "Skye Terrier", "Staffordshire Bull Terrier", "Soft-Coated Wheaten Terrier", "Sussex Spaniel", "Spitz", "Tibetan Terrier", "Vizsla", "Weimaraner", "Welsh Terrier", "West Highland White Terrier", "Whippet", "Yorkshire Terrier"};
//            String[] genders = {"Male", "Female"};
//            String[] pics = {"https://firebasestorage.googleapis.com/v0/b/guk-mobile.appspot.com/o/images%2Fdog%2Ffb7fe05d-e449-4859-b21f-6c379b1663a4.jpg?alt=media&token=4b99316f-3eb0-4471-80be-7fbcde9bb457", "https://firebasestorage.googleapis.com/v0/b/guk-mobile.appspot.com/o/images%2Fdog%2F5fe28eda-0224-473c-a870-a2c76605d595.jpg?alt=media&token=4b99316f-3eb0-4471-80be-7fbcde9bb457", "https://firebasestorage.googleapis.com/v0/b/guk-mobile.appspot.com/o/images%2Fdog%2Fa398b141-1769-465b-b98d-735db985358e.png?alt=media&token=4d90ee65-b9c0-4338-8a2d-a74af805588c", "https://firebasestorage.googleapis.com/v0/b/guk-mobile.appspot.com/o/images%2Fdog%2F777565dc-cd58-4a1b-a6f4-cb11f40770bc.png?alt=media&token=4b99316f-3eb0-4471-80be-7fbcde9bb457", "https://firebasestorage.googleapis.com/v0/b/guk-mobile.appspot.com/o/images%2Fstory%2F8a48f7cd-1323-4042-be95-70eabc6869f0.jpg?alt=media&token=3b0933de-6b95-4c1b-94ab-8e3869aafc67", "https://firebasestorage.googleapis.com/v0/b/guk-mobile.appspot.com/o/images%2Fstory%2F350256ff-324a-4413-a57a-f86fa3cbc4d2.jpg?alt=media&token=85aec634-4e97-41c8-82de-6b58353aaf67"};
//            for(int i = 0; i < names.length; i++){
//                String name = names[i];
//                String breed = breeds[i % breeds.length];
//                String desc = String.format("Long shining coats, floppy ears, and heart-warming smiles – %s are one of the most popular dog breeds in America and many parts of the world. Their docile, loyal, and eager-to-please personality makes them the perfect companions for everyone, especially families with small children. %s need a new home. %s needs you.", breed, name, name);
//                String gender = genders[i % genders.length];
//                String pic = pics[i % pics.length];
//                Dog dog = new Dog(name, breed, desc, randomDate(2010, 2019), gender, randomDate(2020, 2021), "Unadopted", pic);
//
//                Database.getDB().collection(Dog.COLLECTION_NAME).add(dog.toMap()).addOnSuccessListener(documentReference -> {
////                    Toast.makeText(this.getContext(), "kelar", Toast.LENGTH_SHORT).show();
//                });
//            }
//            SEEDING END


            Date date = getDateFromDatePicker(dobPicker);

            String name = nameTxt.getText().toString();
            String breed = breedTxt.getText().toString();
            String description = descriptionTxt.getText().toString();
            Timestamp dob = new Timestamp(date);
            Timestamp rescuedDate = Timestamp.now();
            String gender = genderTxt;
            String status = "Unadopted";

            if (DogController.insertDog(getActivity(), name, breed, description, dob, rescuedDate, gender, status, filePath)) {
//                    Log.d("dob anjing", "gukguk");
            }
        });

        return view;
    }

    private Random rand = new Random();
    private Timestamp randomDate(int min, int max){
        GregorianCalendar gc = new GregorianCalendar();


        int year = rand.nextInt(max - min) + min;

        gc.set(gc.YEAR, year);

        int dayOfYear = rand.nextInt(gc.DAY_OF_YEAR - 1) + 1;

        gc.set(gc.DAY_OF_YEAR, dayOfYear);

        return new Timestamp(gc.getTime());
    }

    public static java.util.Date getDateFromDatePicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }
}