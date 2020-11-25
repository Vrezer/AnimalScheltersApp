package com.example.animalscheltersapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class DisplayUser extends AppCompatActivity {

    TextView name,age,surname,sex,mail,phone;
    ImageView add,remove;
    NavigationView nav;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    String idTmp;
    DrawerLayout drawerLayout;
    FirebaseAuth firebaseAuth;
    SearchView searchView;
    ArrayList<User> list;
    RecyclerView recyclerView;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_user);

        //ANDROID COMPONENT
        name = findViewById(R.id.textViewDisplayNameUser);
        age = findViewById(R.id.textViewDisplayAgeUser);
        surname =findViewById(R.id.textViewDisplaySurnameUser);
        sex=findViewById(R.id.textViewDisplaySexUser);
        mail=findViewById(R.id.textViewDisplayEmailUser);
        toolbar=findViewById(R.id.toolbarAdminDisplayUser);
        nav=findViewById(R.id.navmenuAdminDisplayUser);
        drawerLayout=findViewById(R.id.DisplayUserLayout);
        recyclerView=findViewById(R.id.recylewViewDisplayUser);
        searchView=findViewById(R.id.searchViewDisplayUser);
        add=findViewById(R.id.recycleUserAdminTrue);
        phone=findViewById(R.id.textViewDisplayPhoneUser);
        remove=findViewById(R.id.recycleUserAdminFalse);

        ref= FirebaseDatabase.getInstance().getReference().child("User");

        firebaseAuth = FirebaseAuth.getInstance();

        //GET PERMISSION ADMIN
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessAdministration(true,idTmp);
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessAdministration(false,idTmp);
            }
        });
        //Display drawer
        DrawerMenu();
    }



    private void DrawerMenu()
    {
        drawerMenuHelperAdmin drawerMenuHelperAdmin=new drawerMenuHelperAdmin(nav,actionBarDrawerToggle,toolbar,drawerLayout);
        setSupportActionBar(drawerMenuHelperAdmin.getToolbar());
        drawerMenuHelperAdmin.DrawerMenu(this,firebaseAuth);
    }


    public void DisplayData(String tmp) {

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User").child(tmp);
            final ValueEventListener getData = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    User user = snapshot.getValue(User.class);
                    if(snapshot.exists()) {
                        name.setText(user.getName());
                        surname.setText(user.getSurname());
                        mail.setText(user.getEmail());
                        age.setText(user.getAge());
                        phone.setText(user.getNumber());
                        if (user.getSex().equals("M"))
                            sex.setText("Meżczyzna");
                        else
                            sex.setText("Kobieta");
                    }
                    }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            };
                ref.addValueEventListener(getData);
    }


    private void accessAdministration(final boolean flag,String tmp)
    {
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User").child(tmp);
                        if (flag) {
                            ref.child("admin").setValue(true);
                            makeToast("Nadano Administratora!  ");
                        } else {
                            ref.child("admin").setValue(false);
                            makeToast("Zabrano Administratora!  ");
                        }
        }

    private void makeToast(String message)
    {
        Toast.makeText(DisplayUser.this, message, Toast.LENGTH_SHORT).show();
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
                            list.add(ds.getValue(User.class));
                        }
                        adapterToFindUser adapterToFindUser= new adapterToFindUser(list);
                        recyclerView.setAdapter(adapterToFindUser);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DisplayUser.this);
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
        ArrayList<User> userList = new ArrayList<>();
        for ( User user: list)
        {
            if (user.getSurname().toLowerCase().contains(str.toLowerCase()))
            {
                userList.add(user);
            }
        }
        adapterToFindUser adapterToFindUser= new adapterToFindUser(userList);
        recyclerView.setAdapter(adapterToFindUser);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DisplayUser.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
    }


    @Override
    protected void onStop() {
        super.onStop();
    }


    // ADAPTER TO RECYCLE VIEW
    public class adapterToFindUser extends RecyclerView.Adapter<adapterToFindUser.MyViewHolder> {

        ArrayList<User> list;

        public adapterToFindUser(ArrayList<User> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public adapterToFindUser.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyle_activity_user, parent, false);
            return new adapterToFindUser.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull adapterToFindUser.MyViewHolder holder, final int position) {

            holder.nameUser.setText(list.get(position).getName());
            holder.surnameUser.setText(list.get(position).getSurname());

            holder.surnameUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SetVisible();
                    DisplayData(list.get(position).getIdUser());
                    idTmp=list.get(position).getIdUser();
                }
            });
            holder.nameUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SetVisible();
                    DisplayData(list.get(position).getIdUser());
                    idTmp=list.get(position).getIdUser();
                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView nameUser, surnameUser;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                nameUser=itemView.findViewById(R.id.recycleUserNameUser);
                surnameUser=itemView.findViewById(R.id.recycleUserSurnameUser);
            }
        }
        public void SetVisible()
        {
            TextView a,b,c,d,e,f,g;
            ImageView i,j;
            a=findViewById(R.id.textView20);
            b=findViewById(R.id.textView21);
            c=findViewById(R.id.textView22);
            d=findViewById(R.id.textView23);
            e=findViewById(R.id.textView24);
            g=findViewById(R.id.textView6);
            i=findViewById(R.id.recycleUserAdminTrue);
            j=findViewById(R.id.recycleUserAdminFalse);
            f=findViewById(R.id.recycleUserNameUser2);
            a.setVisibility(View.VISIBLE); c.setVisibility(View.VISIBLE);d.setVisibility(View.VISIBLE);e.setVisibility(View.VISIBLE);
            b.setVisibility(View.VISIBLE);f.setVisibility(View.VISIBLE);i.setVisibility(View.VISIBLE);j.setVisibility(View.VISIBLE);
            g.setVisibility(View.VISIBLE);
        }

    }
}