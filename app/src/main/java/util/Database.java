package util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import edu.bluejack21_2.guk.RegisterActivity;

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

    public static void showImage(String reference, Activity ctx, ImageView imageView){
        StorageReference ref = Database.getStorage().getReference(reference);
        ref.getBytes(1024 * 1024 * 10).addOnSuccessListener(bytes -> {
            Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            DisplayMetrics dm = new DisplayMetrics();

            ctx.getWindowManager().getDefaultDisplay().getMetrics(dm);

            imageView.setMinimumHeight(dm.heightPixels);
            imageView.setMinimumWidth(dm.widthPixels);
            imageView.setImageBitmap(bm);
        }).addOnFailureListener(e -> {});
    }

    public static void uploadImage(Uri filePath, String fileName, Context ctx) {
        StorageReference storageReference = storage.getReference();
        if (filePath != null) {
            ProgressDialog progressDialog = new ProgressDialog(ctx);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child(fileName);

            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    progressDialog.dismiss();
//                                    Toast.makeText(ctx,"Image Uploaded!!", Toast.LENGTH_SHORT).show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(ctx,"Image upload Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                                }
                            });
        }
    }
}
