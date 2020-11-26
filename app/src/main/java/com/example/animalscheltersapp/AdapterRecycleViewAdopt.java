package com.example.animalscheltersapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdapterRecycleViewAdopt extends FirebaseRecyclerAdapter<AdoptModel, AdapterRecycleViewAdopt.myView> {

    DatabaseReference reference;
    Context context;
    public AdapterRecycleViewAdopt(@NonNull FirebaseRecyclerOptions<AdoptModel> options, Context context)
    {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull AdapterRecycleViewAdopt.myView holder, int position, @NonNull AdoptModel model) {

        holder.idForm.setText(model.idAdoptForm);
        GetAnimal(holder,model);
        GetUser(holder,model);
        holder.date.setText(model.date);
        holder.idForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Admin_adoptForm_display.class );
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @NonNull
    @Override
    public AdapterRecycleViewAdopt.myView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyle_activity_adopt,parent,false);
        return new AdapterRecycleViewAdopt.myView(view);
    }

    class myView extends RecyclerView.ViewHolder
    {
        public TextView name,surname,idForm,animalName,date;
        public myView(@NonNull View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.nameAdoptAdmin);
            surname= itemView.findViewById(R.id.surnameAdoptAdmin);
            animalName=itemView.findViewById(R.id.animalNameAdoptAdmin);
            idForm=itemView.findViewById(R.id.recycleAdoptIdAdopt);
            date=itemView.findViewById(R.id.recycleAdoptDate);
        }
    }


    public void GetAnimal(final AdapterRecycleViewAdopt.myView holder, AdoptModel model)
    {
        final ValueEventListener getData = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Animal animal=snapshot.getValue(Animal.class);
                holder.animalName.setText(animal.getName());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        getRef("Animal",model.idAnimal).addValueEventListener(getData);

    }
    private DatabaseReference getRef(String tmp,String id)
    {
        return reference= FirebaseDatabase.getInstance().getReference(tmp).child(id);
    }

    public void GetUser(final AdapterRecycleViewAdopt.myView holder, AdoptModel model)
    {
        final ValueEventListener getData = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                holder.name.setText(user.getName());
                holder.surname.setText(user.getSurname());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        ;
        getRef("User",model.idUser).addValueEventListener(getData);

    }


}
