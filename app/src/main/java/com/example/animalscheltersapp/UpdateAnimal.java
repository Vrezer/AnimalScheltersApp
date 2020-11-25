package com.example.animalscheltersapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

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

public class UpdateAnimal extends AppCompatActivity {


    //VARIABLES

    EditText name,age,breed,sex,description,id;
    Button button2;
    StorageReference storageReference;
    ImageView img;
    NavigationView nav;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    FirebaseAuth firebaseAuth;
    SearchView searchView;
    ArrayList<Animal> list;
    RecyclerView recyclerView;
    DatabaseReference ref;
    String id_tmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_animal);

        //ANDROID COMPONENT

         name=findViewById(R.id.nameUpdateAnimal);
         age=findViewById(R.id.ageUpdateAnimal);
         breed=findViewById(R.id.breedUpdateAnimal);
         sex=findViewById(R.id.sexUpdateAnimal);
         description=findViewById(R.id.descriptionUpdateAnimal);
         toolbar=findViewById(R.id.toolbarAdminUpdateAnimal);
         nav=findViewById(R.id.navmenuAdminUpdateAnimal);
         drawerLayout=findViewById(R.id.UpdateAnimalLayout);
         recyclerView=findViewById(R.id.recylewViewUpdateAnimal);
         searchView=findViewById(R.id.searchViewUpdateAnimal);

         ref= FirebaseDatabase.getInstance().getReference().child("Animal");
         firebaseAuth = FirebaseAuth.getInstance();

         button2=findViewById(R.id.updateAnimalButton2);
         img =findViewById(R.id.imageUpdateAnimal);

         //Display drawer
         DrawerMenu();

         //Update Animal
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateData(id_tmp);
                makeToast("Dane zaktualizowano!");
            }
        });

        /*

        //setAnimalPicture  in future

        img.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select image"),PICK_IMAGE);
            }
        });
        */

    }


    //Function

    private void DrawerMenu()
    {
        drawerMenuHelperAdmin drawerMenuHelperAdmin=new drawerMenuHelperAdmin(nav,actionBarDrawerToggle,toolbar,drawerLayout);
        setSupportActionBar(drawerMenuHelperAdmin.getToolbar());
        drawerMenuHelperAdmin.DrawerMenu(this,firebaseAuth);
    }


    private void UpdateData(String tmp)
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Animal").child(tmp);
        ref.child("name").setValue(name.getText().toString());
        ref.child("age").setValue(age.getText().toString());
        ref.child("breed").setValue(breed.getText().toString());
        ref.child("description").setValue(description.getText().toString());
    }

    private void makeToast(String message)
    {
        Toast.makeText(UpdateAnimal.this, message, Toast.LENGTH_SHORT).show();
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
                        adapterToFindAnimalUpdateAnimal adapterToFindAnimalUpdateAnimal= new adapterToFindAnimalUpdateAnimal(list);
                        recyclerView.setAdapter(adapterToFindAnimalUpdateAnimal);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UpdateAnimal.this);
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
        adapterToFindAnimalUpdateAnimal adapterToFindAnimalUpdateAnimal= new adapterToFindAnimalUpdateAnimal(Mylist);
        recyclerView.setAdapter(adapterToFindAnimalUpdateAnimal);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UpdateAnimal.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
    }


    @Override
    protected void onStop() {
        super.onStop();
    }


    // ADAPTER TO RECYCLE VIEW
    public class adapterToFindAnimalUpdateAnimal extends RecyclerView.Adapter<adapterToFindAnimalUpdateAnimal.MyViewHolder>{

        ArrayList<Animal> list;
        public adapterToFindAnimalUpdateAnimal(ArrayList<Animal> list)
        {
            this.list=list;
        }

        @NonNull
        @Override
        public adapterToFindAnimalUpdateAnimal.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyle_activity,parent,false);
            return new adapterToFindAnimalUpdateAnimal.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull adapterToFindAnimalUpdateAnimal.MyViewHolder holder, final int position) {
            holder.name.setText(list.get(position).getName());
            holder.breed.setText(list.get(position).getBreed());
            holder.age.setText(list.get(position).getAge());

            Glide.with(holder.img.getContext()).load(list.get(position).getUrlPicture()).into(holder.img);


            holder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    id_tmp=list.get(position).getId();
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
                    img.setImageBitmap(bitmap);
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