package edu.bluejack21_2.guk.model;

import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;

import edu.bluejack21_2.guk.util.Crypt;

public class Donation {
    public static String COLLECTION_NAME = "donations";

    private String id, bankAccountHolder, bankAccountNumber, notes, proofPic;
    private int status = 0; // 0 -> pending, 1 -> approved, 2 -> rejected
    private int amount;
    private DocumentReference user;

    public Donation() {
    }

    public Donation(String bankAccountHolder, String bankAccountNumber, int amount, String notes, String proofPic, DocumentReference user) {
        this.bankAccountHolder = bankAccountHolder;
        this.bankAccountNumber = bankAccountNumber;
        this.amount = amount;
        this.notes = notes;
        this.proofPic = proofPic;
        this.user = user;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public DocumentReference getUser() {
        return user;
    }

    public void setUser(DocumentReference user) {
        this.user = user;
    }
}
