package edu.bluejack21_2.guk.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

//import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import edu.bluejack21_2.guk.DogDetailActivity;
import edu.bluejack21_2.guk.R;
import edu.bluejack21_2.guk.controller.DogController;
import edu.bluejack21_2.guk.model.Dog;
import edu.bluejack21_2.guk.model.User;
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
        holder.breed.setText(dog.getBreed());
        holder.deleteDogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DogController.deleteDog(
                        dog.getId());

            }
        });

        holder.dogCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getAc(new Intent(this, MainActivity.class));
                Intent intent = new Intent(context, DogDetailActivity.class);
                intent.putExtra("dog", dog);
                context.startActivity(intent);
            }
        });


//        try {
//            holder.breed.setText(dog.getBreed().getName());
//        } catch (Exception e){
//
//        }



        Database.showImage(dog.getPicture(), ((Activity)context), holder.picture);
    }

    @Override
    public int getItemCount() {
        return dogList.size();
    }


    class DogsViewholder extends RecyclerView.ViewHolder {
        TextView name, dob, breed;
        ImageView picture, genderIcon;
        Button deleteDogBtn;
        CardView dogCard;
        public DogsViewholder(@NonNull View itemView)
        {
            super(itemView);

            name = itemView.findViewById(R.id.home_dog_name);
            dob = itemView.findViewById(R.id.home_dog_age);
            breed = itemView.findViewById(R.id.home_dog_breed);
            picture = itemView.findViewById(R.id.home_dog_image);
            genderIcon = itemView.findViewById(R.id.home_gender_icon);
            deleteDogBtn = itemView.findViewById(R.id.delete_dog_btn);
            dogCard = itemView.findViewById(R.id.dog_card);

            if(User.CURRENT_USER.getRole().equals("admin")){
                deleteDogBtn.setVisibility(View.VISIBLE);
            }else{
                deleteDogBtn.setVisibility(View.GONE);
            }

        }
    }
}
