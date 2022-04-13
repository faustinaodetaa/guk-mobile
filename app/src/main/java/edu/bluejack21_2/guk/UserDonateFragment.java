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
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import edu.bluejack21_2.guk.controller.DonationController;

public class UserDonateFragment extends Fragment {

    private TextView message;
    private EditText bankHolderTxt, bankNumberTxt, donationAmountTxt, notesTxt;
    private CardView choosePicBtn;
    private ImageView imageView;
    private Button donateBtn;
    private Uri filePath;

    public UserDonateFragment() {
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
        View view = inflater.inflate(R.layout.fragment_user_donate, container, false);

        message = view.findViewById(R.id.donate_prompt_message);
        String txt = "What a privilege to be here on the planet to contribute your unique donation to these homeless dogs.<br/>You can donate to: <b>604 123 1234</b> (BCA)";
        message.setText(Html.fromHtml(txt));

        bankHolderTxt = view.findViewById(R.id.donate_name_txt);
        bankNumberTxt = view.findViewById(R.id.donate_number_txt);
        donationAmountTxt = view.findViewById(R.id.donate_amount_txt);
        notesTxt = view.findViewById(R.id.donate_notes_txt);
        choosePicBtn = view.findViewById(R.id.donate_choose_picture_btn);
        imageView = view.findViewById(R.id.donate_picture);
        donateBtn = view.findViewById(R.id.donate_btn);

        ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null && result.getData().getData() != null) {
                filePath = result.getData().getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        choosePicBtn.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            resultLauncher.launch(Intent.createChooser(intent,"Select Image from here..."));
        });

        donateBtn.setOnClickListener(v -> {
            String bankAccountHolder = bankHolderTxt.getText().toString();
            String bankAccountNumber = bankNumberTxt.getText().toString();
            String donationAmount = donationAmountTxt.getText().toString();
            String notes = notesTxt.getText().toString();

            if(DonationController.insertDonation(getActivity(), bankAccountHolder, bankAccountNumber, donationAmount, notes, filePath)){

            }
        });

        return view;

    }
}