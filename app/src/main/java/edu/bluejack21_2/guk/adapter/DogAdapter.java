package edu.bluejack21_2.guk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        View v = LayoutInflater.from(context).inflate(R.layout.row_dog,parent,false);

        return new DogsViewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DogsViewholder holder, int position) {
        Dog dog = dogList.get(position);
        holder.name.setText(dog.getName());
        holder.gender.setText(dog.getGender());
        holder.dob.setText(dog.getDob());
//        holder.breed.setText(dog.getBreed());
    }

    @Override
    public int getItemCount() {
        return dogList.size();
    }


    class DogsViewholder extends RecyclerView.ViewHolder {
        TextView name, gender, dob, breed;
        public DogsViewholder(@NonNull View itemView)
        {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            gender =itemView.findViewById(R.id.gender);
            dob = itemView.findViewById(R.id.age);
            breed = itemView.findViewById(R.id.breed);
        }
    }
}
