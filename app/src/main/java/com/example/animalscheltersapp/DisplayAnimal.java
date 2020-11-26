package com.example.animalscheltersapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
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
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DisplayAnimal extends AppCompatActivity {

    TextView name,age,breed,sex,description,a,b,c,d,e;
    StorageReference storageReference;
    ImageView img2;
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
        setContentView(R.layout.activity_display_animal);

        //ANDROID COMPONENT
        name=findViewById(R.id.textViewDisplayName);
        age=findViewById(R.id.textViewDisplayAge);
        breed=findViewById(R.id.textViewDisplayBreed);
        sex=findViewById(R.id.textViewDisplaySex);
        description=findViewById(R.id.textViewDisplayDescription);
        img2=findViewById(R.id.imageDisplayAnimal);
        toolbar=findViewById(R.id.toolbarAdminDisplayAnimal);
        nav=findViewById(R.id.navmenuAdminDisplayAnimal);
        drawerLayout=findViewById(R.id.DisplayAnimalLayout);
        a=findViewById(R.id.textView9);
        b=findViewById(R.id.textView10);
        c=findViewById(R.id.textView11);
        d=findViewById(R.id.textView12);
        e=findViewById(R.id.textView13);
        firebaseAuth = FirebaseAuth.getInstance();


        recyclerView=findViewById(R.id.recylewViewDisplayAniaml);
        searchView=findViewById(R.id.searchViewAnimalDisplay);

        ref= FirebaseDatabase.getInstance().getReference().child("Animal");
        //Display drawer
        DrawerMenu();


    }

    private void DrawerMenu()
    {
        drawerMenuHelperAdmin drawerMenuHelperAdmin=new drawerMenuHelperAdmin(nav,actionBarDrawerToggle,toolbar,drawerLayout);
        setSupportActionBar(drawerMenuHelperAdmin.getToolbar());
        drawerMenuHelperAdmin.DrawerMenu(this,firebaseAuth);
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
                        adapterToFindAnimalDisplayAnimal adapterToFindAnimalDisplayAnimal= new adapterToFindAnimalDisplayAnimal(list);
                        recyclerView.setAdapter(adapterToFindAnimalDisplayAnimal);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DisplayAnimal.this);
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
        ArrayList<Animal> Mylist = new ArrayList<>();
        for ( Animal animal: list)
        {
            if (animal.getName().toLowerCase().contains(str.toLowerCase()))
            {
                Mylist.add(animal);
            }
        }
        adapterToFindAnimalDisplayAnimal adapterToFindAnimalDisplayAnimal= new adapterToFindAnimalDisplayAnimal(Mylist);
        recyclerView.setAdapter(adapterToFindAnimalDisplayAnimal);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DisplayAnimal.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
    }



    @Override
    protected void onStop() {
        super.onStop();
    }




    // ADAPTER TO RECYCLE VIEW
    public class adapterToFindAnimalDisplayAnimal extends RecyclerView.Adapter<adapterToFindAnimalDisplayAnimal.MyViewHolder>{

        ArrayList<Animal> list;
        public adapterToFindAnimalDisplayAnimal(ArrayList<Animal> list)
        {
            this.list=list;
        }

        @NonNull
        @Override
        public adapterToFindAnimalDisplayAnimal.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyle_activity,parent,false);
            return new adapterToFindAnimalDisplayAnimal.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull adapterToFindAnimalDisplayAnimal.MyViewHolder holder, final int position) {
            holder.name.setText(list.get(position).getName());
            holder.breed.setText(list.get(position).getBreed());
            holder.age.setText(list.get(position).getAge());

            Glide.with(holder.img.getContext()).load(list.get(position).getUrlPicture()).into(holder.img);


            holder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        a.setVisibility(View.VISIBLE);
                        b.setVisibility(View.VISIBLE);
                        c.setVisibility(View.VISIBLE);
                        d.setVisibility(View.VISIBLE);
                        e.setVisibility(View.VISIBLE);
                        img2.setVisibility(View.VISIBLE);
                        DisplayImageAdapter(list.get(position).getId());
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

        public void DisplayImageAdapter(final String id)
        {
            storageReference = FirebaseStorage.getInstance().getReference("Animal").child(id);
            File file = null;
            try {
                file = File.createTempFile("image", "jpeg");
            } catch (IOException e) {
                e.printStackTrace();
            }
            final File finalFile = file;
            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap= BitmapFactory.decodeFile(finalFile.getAbsolutePath());
                    img2.setImageBitmap(bitmap);
                    // DISPLAY ANIMAL DATA
                    GetDataFirebase tmp=new GetDataFirebase();
                    tmp.DisplayData(id,age,name,breed,description,sex);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }

    }
}