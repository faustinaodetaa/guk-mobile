package edu.bluejack21_2.guk.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import edu.bluejack21_2.guk.R;
import edu.bluejack21_2.guk.listener.FinishListener;

public class Database {

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static FirebaseStorage storage = FirebaseStorage.getInstance();
//    private static

    public static FirebaseFirestore getDB(){
        return db;
    }

    public static FirebaseStorage getStorage(){
        return storage;
    }

    public static void showImage(String url, Activity ctx, ImageView imageView){
        Glide.with(ctx)
                .load(url)
//                .centerCrop()
                .placeholder(imageView.getDrawable())
                .into(imageView);

//        StorageReference ref = Database.getStorage().getReference(reference);
//        ref.getDownloadUrl().addOnSuccessListener(uri -> {
//            Log.d("coba", "showImage: " + uri.toString());
//            Glide.with(ctx)
//                    .load(uri.toString())
//                    .centerCrop()
//                    .placeholder(imageView.getDrawable())
//                    .into(imageView);
//        });

//        ref.getBytes(1024 * 1024 * 10).addOnSuccessListener(bytes -> {
//            Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//            DisplayMetrics dm = new DisplayMetrics();
//
//            ctx.getWindowManager().getDefaultDisplay().getMetrics(dm);
//
//            imageView.setMinimumHeight(dm.heightPixels);
//            imageView.setMinimumWidth(dm.widthPixels);
//            imageView.setImageBitmap(bm);
//        }).addOnFailureListener(e -> {});
    }

    public static void uploadImage(Uri filePath, String fileName, Context ctx, FinishListener<String> listener) {
        StorageReference storageReference = storage.getReference();
        if (filePath != null) {
            ProgressDialog progressDialog = new ProgressDialog(ctx);
            progressDialog.setTitle(ctx.getString(R.string.uploading));
            progressDialog.show();

            StorageReference ref = storageReference.child(fileName);

            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    ref.getDownloadUrl().addOnSuccessListener(uri -> {
                                        if(listener != null)
                                            listener.onFinish(uri.toString(), null);
                                    }).addOnFailureListener(e -> {
                                        e.printStackTrace();
                                    });
                                    progressDialog.dismiss();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(ctx,ctx.getString(R.string.upload_failed) + " " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(ctx.getString(R.string.uploaded) + " " + (int) progress + "%");
                                }
                            });
        }
    }
}
