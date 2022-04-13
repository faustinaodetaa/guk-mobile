package edu.bluejack21_2.guk.controller;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;

import java.util.UUID;

import edu.bluejack21_2.guk.model.Dog;
import edu.bluejack21_2.guk.model.Donation;
import edu.bluejack21_2.guk.model.User;
import edu.bluejack21_2.guk.util.Database;

public class DonationController {
    public static boolean insertDonation(Context ctx, String bankAccountHolder, String bankAccountNumber, String amountStr, String notes, Uri filePath){
        String errorMsg = "";

        int amount = 0;
        if(bankAccountHolder.isEmpty()){
            errorMsg = "Bank Account Holder name must be filled!";
        } else if(bankAccountNumber.isEmpty()){
            errorMsg = "Bank Account Number must be filled!";
        } else if(amountStr.isEmpty()) {
            errorMsg = "Donation amount must be filled!";
        } else if(filePath == null){
            errorMsg = "Please upload your donation proof!";
        } else {
            try{
                amount = Integer.parseInt(amountStr);
            } catch (Exception e){
                errorMsg = "Donation amount must be numeric!";
            }
        }



        if(!errorMsg.isEmpty()){
            Toast.makeText(ctx, errorMsg, Toast.LENGTH_SHORT).show();
            return false;
        }

        String extension = filePath.toString().substring(filePath.toString().lastIndexOf(".") + 1);
        String fileName = "images/donations/" + UUID.randomUUID().toString() + "." + extension;


        Database.uploadImage(filePath, fileName, ctx, (data, message) -> {
            DocumentReference userRef = Database.getDB().collection(User.COLLECTION_NAME).document(User.CURRENT_USER.getId());
            Donation donation = new Donation(bankAccountHolder, bankAccountNumber, Integer.parseInt(amountStr), notes, data, userRef);
            Database.getDB().collection(Donation.COLLECTION_NAME).add(donation.toMap()).addOnSuccessListener(documentReference -> {
                ((Activity)ctx).finish();
                ((Activity)ctx).overridePendingTransition(0, 0);
                ((Activity)ctx).startActivity(((Activity)ctx).getIntent());
                ((Activity)ctx).overridePendingTransition(0, 0);
                Toast.makeText(ctx, "Thank you for your Donation! Please wait for our admin to check and review your donation.", Toast.LENGTH_LONG).show();

            }).addOnFailureListener(e -> {
                Toast.makeText(ctx, "Error!", Toast.LENGTH_SHORT).show();
            });
        });
        return true;
    }
}
