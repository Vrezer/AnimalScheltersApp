package com.example.animalscheltersapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AdapterToRecyleView extends FirebaseRecyclerAdapter<Animal, AdapterToRecyleView.myView> {


    public AdapterToRecyleView(@NonNull FirebaseRecyclerOptions<Animal> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myView holder, int position, @NonNull Animal model) {
        holder.name.setText("Imię: "+model.getName());
        holder.breed.setText("Rasa: "+model.getBreed());
        holder.age.setText("Wiek: "+model.getAge());
        Glide.with(holder.img.getContext()).load(model.getUrlPicture()).into(holder.img);
    }

    @NonNull
    @Override
    public myView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyle_activity,parent,false);
        return new myView(view);
    }

    class myView extends RecyclerView.ViewHolder
    {
        ImageView img;
        TextView name,breed,age;
        public myView(@NonNull View itemView) {
            super(itemView);
            img= itemView.findViewById(R.id.imageRecyle);
            name= itemView.findViewById(R.id.nameRecyle);
            breed= itemView.findViewById(R.id.breedRecyle);
            age=itemView.findViewById(R.id.ageRecyle);

        }
    }
}
