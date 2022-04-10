package edu.bluejack21_2.guk.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.squareup.picasso.Picasso;

import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import edu.bluejack21_2.guk.R;
import edu.bluejack21_2.guk.model.Dog;
import util.Database;

public class DogAdapter extends RecyclerView.Adapter<DogAdapter.DogsViewholder>{

    Context context;


    private ArrayList<Dog> dogList;

    public DogAdapter(Context context, ArrayList<Dog> dogList)
    {
        this.context = context;
        this.dogList = dogList;
    }

    @NonNull
    @Override
    public DogsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.dog_template,parent,false);

        return new DogsViewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DogsViewholder holder, int position) {

        Dog dog = dogList.get(position);

        String mon = dog.getDob().toDate().toString().substring(4,7);
        String dd = dog.getDob().toDate().toString().substring(8,10);
        String yyyy = dog.getDob().toDate().toString().substring(30,34);
        Integer age = 2022 - Integer.parseInt(yyyy);
        Log.d("age", age.toString());

        Log.d("bday", mon+dd+yyyy);
        Log.d("bday", dog.getDob().toDate().toString());
        holder.name.setText(dog.getName());
        holder.genderIcon.setImageResource(dog.getGender().equals("Male") ? R.drawable.gender_male : R.drawable.gender_female);

        holder.dob.setText(age + " years old");

        try {
            holder.breed.setText(dog.getBreed().getName());
        } catch (Exception e){

        }

        StorageReference ref = Database.getStorage().getReference(dog.getPicture());
        ref.getBytes(1024 * 1024 * 10).addOnSuccessListener(bytes -> {
            Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            DisplayMetrics dm = new DisplayMetrics();

            ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);

            holder.picture.setMinimumHeight(dm.heightPixels);
            holder.picture.setMinimumWidth(dm.widthPixels);
            holder.picture.setImageBitmap(bm);
        }).addOnFailureListener(e -> {});
    }

    @Override
    public int getItemCount() {
        return dogList.size();
    }


    class DogsViewholder extends RecyclerView.ViewHolder {
        TextView name, dob, breed;
        ImageView picture, genderIcon;
        public DogsViewholder(@NonNull View itemView)
        {
            super(itemView);

            name = itemView.findViewById(R.id.home_dog_name);
            dob = itemView.findViewById(R.id.home_dog_age);
            breed = itemView.findViewById(R.id.home_dog_breed);
            picture = itemView.findViewById(R.id.home_dog_image);
            genderIcon = itemView.findViewById(R.id.home_gender_icon);
        }
    }
}
