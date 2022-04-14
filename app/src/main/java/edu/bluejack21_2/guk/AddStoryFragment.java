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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.Timestamp;

import java.io.IOException;

import edu.bluejack21_2.guk.controller.StoryController;


public class AddStoryFragment extends Fragment {

    private CardView choosePicBtn;
    private EditText contentTxt;
    private Button addStoryBtn;
    private ImageView imageView;

    private Uri filePath;

    public AddStoryFragment() {
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
        View view = inflater.inflate(R.layout.fragment_add_story, container, false);

        choosePicBtn = view.findViewById(R.id.add_story_choose_btn);

        imageView = view.findViewById(R.id.add_story_profile_picture);

        addStoryBtn = view.findViewById(R.id.add_story_btn);

        contentTxt = view.findViewById(R.id.add_story_content_txt);

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
            resultLauncher.launch(Intent.createChooser(intent,"Select Image from here..."));
        });

        addStoryBtn.setOnClickListener(view1 -> {
            Timestamp createdAt = Timestamp.now();
            String content = contentTxt.getText().toString();

            StoryController.insertStory(getActivity(), content, filePath, createdAt);
        });



        return view;
    }
}