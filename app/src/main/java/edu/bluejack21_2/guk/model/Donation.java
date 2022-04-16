package edu.bluejack21_2.guk.model;

import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;

import edu.bluejack21_2.guk.util.Crypt;

public class Donation extends History{
    public static String COLLECTION_NAME = "donations";

    private String bankAccountHolder, bankAccountNumber, notes, proofPic;
    private int amount;

    public Donation(){

    }

    public Donation(String bankAccountHolder, String bankAccountNumber, int amount, String notes, String proofPic, DocumentReference user) {
        super(user);
        this.bankAccountHolder = bankAccountHolder;
        this.bankAccountNumber = bankAccountNumber;
        this.amount = amount;
        this.notes = notes;
        this.proofPic = proofPic;
    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> donation = new HashMap<>();
        donation.put("amount", amount);
        donation.put("bankAccountHolder", bankAccountHolder);
        donation.put("bankAccountNumber", bankAccountNumber);
        donation.put("notes", notes);
        donation.put("proofPic", proofPic);
        donation.put("user", user);
        donation.put("status", status);

        return donation;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getBankAccountHolder() {
        return bankAccountHolder;
    }

    public void setBankAccountHolder(String bankAccountHolder) {
        this.bankAccountHolder = bankAccountHolder;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getProofPic() {
        return proofPic;
    }

    public void setProofPic(String proofPic) {
        this.proofPic = proofPic;
    }

}
