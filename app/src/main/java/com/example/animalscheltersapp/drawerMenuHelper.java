package com.example.animalscheltersapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class drawerMenuHelper {

    NavigationView nav;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    DrawerLayout drawerLayout;

    public Toolbar getToolbar() {
        return toolbar;
    }



    public drawerMenuHelper(NavigationView nav, ActionBarDrawerToggle actionBarDrawerToggle, Toolbar toolbar, DrawerLayout drawerLayout) {
        this.nav = nav;
        this.actionBarDrawerToggle = actionBarDrawerToggle;
        this.toolbar = toolbar;
        this.drawerLayout = drawerLayout;
    }
    public void DrawerMenu(final Activity activity, final FirebaseAuth firebaseAuth)
    {
        actionBarDrawerToggle=new ActionBarDrawerToggle(activity,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home :
                        intentActivity(activity,UserActivity.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.aboutUs :
                        intentActivity(activity,AboutUs.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.contact :
                        intentActivity(activity, Contact.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.settings :
                        intentActivity(activity, testing_find.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.logOut :
                        firebaseAuth.signOut();
                        makeToast("Wylogowanie...",activity);
                        intentActivity(activity,MainActivity.class);
                        break;
                }
                return true;
            }
        });

    }

    public void makeToast(String message,Activity activity) {
        Toast.makeText(activity,message,Toast.LENGTH_SHORT).show();}

    private static void intentActivity(Activity activity, Class aclass)
    {
        Intent intent = new Intent(activity,aclass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

}
