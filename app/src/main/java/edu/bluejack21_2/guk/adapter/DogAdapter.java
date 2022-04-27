package edu.bluejack21_2.guk.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

//import com.squareup.picasso.Picasso;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import edu.bluejack21_2.guk.DogDetailActivity;
import edu.bluejack21_2.guk.R;
import edu.bluejack21_2.guk.controller.DogController;
import edu.bluejack21_2.guk.model.Dog;
import edu.bluejack21_2.guk.model.User;
import edu.bluejack21_2.guk.util.Database;

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

        Date dob = dog.getDob().toDate();
        String mon = dob.toString().substring(4,7);
        String dd = dob.toString().substring(8,10);
        String yyyy = dob.toString().substring(30,34);
        Integer age = Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(yyyy);
        Log.d("age", age.toString());

        Log.d("bday", mon+dd+yyyy);
        Log.d("bday", dob.toString());
        holder.name.setText(dog.getName());
        holder.genderIcon.setImageResource(dog.getGender().equals("Male") ? R.drawable.gender_male : R.drawable.gender_female);

        String yearsold = context.getString(R.string.years_old);
        holder.dob.setText(age + " " + yearsold);

        holder.breed.setText(dog.getBreed());

        holder.dogCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DogDetailActivity.class);
                Log.d("coba intent", "onClick: " + dog.getName());
                intent.putExtra("dog", dog);
                context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) view.getContext(), holder.picture, "image-trans").toBundle());
            }
        });


        Date today = Calendar.getInstance().getTime();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -3);
        Date threeDaysAgo = cal.getTime();
        Date rescuedDate = dog.getRescuedDate().toDate();
//        Log.d("datecompare", "onBindViewHolder: "  + threeDaysAgo.toString() + " -> " + today.toString());
        if(rescuedDate.getTime() <= today.getTime() && threeDaysAgo.getTime() <= rescuedDate.getTime()){
            holder.recentTag.setVisibility(View.VISIBLE);
        } else{
            holder.recentTag.setVisibility(View.GONE);

        }

//        try {
//            holder.breed.setText(dog.getBreed().getName());
//        } catch (Exception e){
//
//        }


//        Glide.with(context).load("https://firebasestorage.googleapis.com/v0/b/guk-mobile.appspot.com/o/images%2Fdog%2F61057a86-10fc-4125-8873-3cdbe92b1523.jpg?alt=media&token=5e6cc727-1148-45b6-b0e0-bbbda309e7ae").centerCrop().placeholder(holder.picture.getDrawable()).into(holder.picture);
        Database.showImage(dog.getPicture(), ((Activity)context), holder.picture);
    }

    @Override
    public int getItemCount() {
        return dogList.size();
    }


    class DogsViewholder extends RecyclerView.ViewHolder {
        TextView name, dob, breed;
        ImageView picture, genderIcon;
        CardView dogCard;
        LinearLayout recentTag;
        public DogsViewholder(@NonNull View itemView)
        {
            super(itemView);

            name = itemView.findViewById(R.id.home_dog_name);
            dob = itemView.findViewById(R.id.home_dog_age);
            breed = itemView.findViewById(R.id.home_dog_breed);
            picture = itemView.findViewById(R.id.home_dog_image);
            genderIcon = itemView.findViewById(R.id.home_gender_icon);
            dogCard = itemView.findViewById(R.id.dog_card);
            recentTag = itemView.findViewById(R.id.dog_recent_tag);


        }
    }
}
