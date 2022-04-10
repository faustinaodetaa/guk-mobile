package edu.bluejack21_2.guk.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import edu.bluejack21_2.guk.R;
import edu.bluejack21_2.guk.model.Dog;

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
        holder.gender.setText(dog.getGender());
        holder.dob.setText(age + " years old");
//        try{
            holder.breed.setText(dog.getBreed().getName());
//        }catch(Exception e){
//        }
//        Glide.with(context).load(dog.getPicture()).into(holder.picture);
        Picasso.get().load(dog.getPicture()).into(holder.picture);
    }

    @Override
    public int getItemCount() {
        return dogList.size();
    }


    class DogsViewholder extends RecyclerView.ViewHolder {
        TextView name, gender, dob, breed;
        ImageView picture;
        public DogsViewholder(@NonNull View itemView)
        {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            gender =itemView.findViewById(R.id.gender);
            dob = itemView.findViewById(R.id.age);
            breed = itemView.findViewById(R.id.breed);
            picture = itemView.findViewById(R.id.imageView);
        }
    }
}
