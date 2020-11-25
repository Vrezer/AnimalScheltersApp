package com.example.animalscheltersapp;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class DeleteAnimal extends AppCompatActivity {

    StorageReference storageReference;
    NavigationView nav;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    FirebaseAuth firebaseAuth;

    SearchView searchView;
    ArrayList<Animal> list;
    RecyclerView recyclerView;
    DatabaseReference ref;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_animal);

        //ANDROID COMPONENT
        toolbar=findViewById(R.id.toolbarAdminDeleteAnimal);
        nav=findViewById(R.id.navmenuAdminDeleteAnimal);
        drawerLayout=findViewById(R.id.DeleteAnimalLayout);
        firebaseAuth = FirebaseAuth.getInstance();

        recyclerView=findViewById(R.id.recylewViewDelte);
        searchView=findViewById(R.id.searchView);

        ref=FirebaseDatabase.getInstance().getReference().child("Animal");
        //Display drawer
        DrawerMenu();


    }

    private void DrawerMenu()
    {
        drawerMenuHelperAdmin drawerMenuHelperAdmin=new drawerMenuHelperAdmin(nav,actionBarDrawerToggle,toolbar,drawerLayout);
        setSupportActionBar(drawerMenuHelperAdmin.getToolbar());
        drawerMenuHelperAdmin.DrawerMenu(this,firebaseAuth);
    }


    public void DeleteAnimalProfile(final String tmp, final Context context)
    {
        new AlertDialog.Builder(context)
                .setTitle("Uwaga! ")
                .setMessage("Czy na pewno chcesz usunąć tego zwierzaka?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

        storageReference = FirebaseStorage.getInstance().getReference("Animal").child(tmp);
        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                DeleteAnimalFun(tmp);
                makeToast("Usunięto Zwierzaka.");
                context.startActivity(new Intent(DeleteAnimal.this, AdminActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });

                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }


    public void DeleteAnimalFun(String tmp)
    {
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Animal").child(tmp);
                        ref.removeValue();
    }

    private void makeToast(String message)
    {
        Toast.makeText(DeleteAnimal.this, message, Toast.LENGTH_SHORT).show();
    }





    //FUNCTION TO RECYLEVIEW
    @Override
    protected void onStart() {
        super.onStart();
        if(ref !=null)
        {
            ref.addValueEventListener(new ValueEventListener() {
                @Override

                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists())
                    {
                        list=new ArrayList<>();
                        for(DataSnapshot ds : dataSnapshot.getChildren())
                        {
                            list.add(ds.getValue(Animal.class));
                        }
                        adapterToFindAnimalDelete adapterToFindAnimalDelete= new adapterToFindAnimalDelete(list);
                        recyclerView.setAdapter(adapterToFindAnimalDelete);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DeleteAnimal.this);
                        linearLayoutManager.setReverseLayout(true);
                        linearLayoutManager.setStackFromEnd(true);
                        recyclerView.setLayoutManager(linearLayoutManager);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        if(searchView !=null)
        {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return true;
                }
            });
        }
    }

    private void search(String str)
    {
        ArrayList<Animal> Mylist = new ArrayList<Animal>();
        for ( Animal animal: list)
        {
            if (animal.getName().toLowerCase().contains(str.toLowerCase()))
            {
                Mylist.add(animal);
            }
        }
        adapterToFindAnimalDelete adapterToFindAnimalDelete= new adapterToFindAnimalDelete(Mylist);
        recyclerView.setAdapter(adapterToFindAnimalDelete);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DeleteAnimal.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


    }



    @Override
    protected void onStop() {
        super.onStop();
    }

 // ADAPTER TO RECYCLE VIEW
    public class adapterToFindAnimalDelete extends RecyclerView.Adapter<adapterToFindAnimalDelete.MyViewHolder>{

        ArrayList<Animal> list;
        public adapterToFindAnimalDelete(ArrayList<Animal> list)
        {
            this.list=list;
        }

        @NonNull
        @Override
        public adapterToFindAnimalDelete.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyle_activity,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull adapterToFindAnimalDelete.MyViewHolder holder, final int position) {
            holder.name.setText(list.get(position).getName());
            holder.breed.setText(list.get(position).getBreed());
            holder.age.setText(list.get(position).getAge());

            Glide.with(holder.img.getContext()).load(list.get(position).getUrlPicture()).into(holder.img);
            holder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DeleteAnimal deleteAnimal=new DeleteAnimal();
                    deleteAnimal.DeleteAnimalProfile(list.get(position).getId(),DeleteAnimal.this);
                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{

            public ImageView img;
            public TextView name,breed,age;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                img= itemView.findViewById(R.id.imageRecyle);
                name= itemView.findViewById(R.id.nameRecyle);
                breed= itemView.findViewById(R.id.breedRecyle);
                age=itemView.findViewById(R.id.ageRecyle);
            }
        }

    }
}
