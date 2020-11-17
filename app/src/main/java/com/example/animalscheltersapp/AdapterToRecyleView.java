package com.example.animalscheltersapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AdapterToRecyleView extends FirebaseRecyclerAdapter<Animal, AdapterToRecyleView.myView> {


    Context context;
    public AdapterToRecyleView(@NonNull FirebaseRecyclerOptions<Animal> options, Context context)
    {
        super(options);
        this.context=context;

    }
    @Override
    protected void onBindViewHolder(@NonNull myView holder, int position, @NonNull final Animal model) {
        holder.name.setText(model.getName());
        holder.breed.setText(model.getBreed());
        holder.age.setText(model.getAge());

        Glide.with(holder.img.getContext()).load(model.getUrlPicture()).into(holder.img);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(context,AnimalUser.class );
                intent.putExtra("img",model.getUrlPicture());
                intent.putExtra("name",model.getName());
                intent.putExtra("id",model.getId());
                intent.putExtra("breed",model.getBreed());
                intent.putExtra("age",model.getAge());
                intent.putExtra("sex",model.getSex());
                intent.putExtra("description",model.getDescription());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
    }


    @NonNull
    @Override
    public myView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyle_activity,parent,false);
        return new myView(view);
    }

    class myView extends RecyclerView.ViewHolder
    {
        public ImageView img;
        public TextView name,breed,age;
        public myView(@NonNull View itemView) {
            super(itemView);
            img= itemView.findViewById(R.id.imageRecyle);
            name= itemView.findViewById(R.id.nameRecyle);
            breed= itemView.findViewById(R.id.breedRecyle);
            age=itemView.findViewById(R.id.ageRecyle);

        }
    }
}
