 package com.example.yallah_project.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yallah_project.R;
import com.example.yallah_project.Fragment.BookedActivitiesFragment;
import com.example.yallah_project.Fragment.HomeFragment;
import com.example.yallah_project.Fragment.MyRoomsFragment;
import com.example.yallah_project.database.SharedPrefer;
import com.example.yallah_project.model.User;
import com.example.yallah_project.viewmodel.UserViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

 public class nav_layout_all extends AppCompatActivity {

    FloatingActionButton fab;
    DrawerLayout drawerLayout;

    BottomNavigationView bottomNavigationView;
    NavigationView navigationView;
     private SharedPrefer sharedPrefer ;
     private UserViewModel userViewModel ;
     private Toolbar toolbar ;

     private FragmentContainerView fragmentContainerView  ;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_layout_all);
        fragmentContainerView = findViewById(R.id.fragmentContainerView);
         toolbar = findViewById(R.id.toolbar);
         bottomNavigationView = findViewById(R.id.bottomNavigationView )  ;
         fab = findViewById(R.id.fab);
         drawerLayout = findViewById(R.id.drawer_layout);
         navigationView = findViewById(R.id.nav_view);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        CircleImageView profileImageView = headerView.findViewById(R.id.userProfile);
        TextView header_name = headerView.findViewById(R.id.header_name);
        TextView header_email = headerView.findViewById(R.id.header_email);
        sharedPrefer  = new SharedPrefer() ;
        String UserEmail  = sharedPrefer.getUserData(this,"email") ;
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class) ;
        final User[] this_user = new User[1];
        LiveData<User> user = null;
        user = userViewModel.getUserByEmail(UserEmail) ;
        user.observe( this , user1 -> {
            if(user1!= null) {
            this_user[0] = user1 ;
            Log.i("user", user1.toString());

            byte[] imageBytes = user1.getProfilePicture();
            Log.i("imageBytes", imageBytes.toString());
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                profileImageView.setImageBitmap(bitmap);
                header_name.setText(user1.getName());
                header_email.setText(user1.getEmail());
            }
        });

         if (savedInstanceState == null) {
             //replaceFragment(new HomeFragment());
         Log.i("saberamine", "savedInstance is null");
             navigationView.setCheckedItem(R.id.nav_home);
         }
         Log.v("FragmentManager", "Your log message here");

       setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation view item clicks here.
                int id = item.getItemId();

                Log.d("NavItemSelected", "Item ID: " + id);

                if (id == R.id.nav_home) {
                    Log.i("saberamine", "home clicked");

                    replaceFragment(new HomeFragment());
                } else if (id == R.id.nav_BookedActivites) {
                    Log.i("saberamine", "booked is clicked");

                    replaceFragment(new BookedActivitiesFragment());
                } else if (id == R.id.nav_rooms) {
                    Log.i("saberamine", "rooms is clicked");

                    replaceFragment(new MyRoomsFragment());
                } else if (id == R.id.nav_profile) {
                    Log.i("saberamine", "profile is clicked");

                    Intent intent = new Intent(nav_layout_all.this, ProfileActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_logout) {
                Intent intent = new Intent(nav_layout_all.this, LoginActivity.class);
                startActivity(intent);
                finish();
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


        replaceFragment(new HomeFragment());

        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                Log.i("saberamine", "home butom is clicked");

                fragmentTransaction.replace(R.id.fragmentContainerView, new HomeFragment() , null ).setReorderingAllowed(true).addToBackStack(null).commitAllowingStateLoss();
            } else if (itemId == R.id.BookedActivites) {
                Log.i("saberamine", "booked butom is clicked");

                fragmentTransaction.replace(R.id.fragmentContainerView,  new BookedActivitiesFragment() , null ).setReorderingAllowed(true).addToBackStack(null).commitAllowingStateLoss();
            } else if (itemId == R.id.rooms) {
                Log.i("saberamine", "room butom is clicked");

                fragmentTransaction.replace(R.id.fragmentContainerView, new MyRoomsFragment()  , null ).setReorderingAllowed(true).addToBackStack(null).commitAllowingStateLoss();
            } else if (itemId == R.id.profile) {
                Log.i("saberamine", "profile butom is clicked");

                Intent intent  = new Intent(nav_layout_all.this, ProfileActivity.class);
                intent.putExtra("user" , this_user[0]);
                startActivity(intent);
            }

            return true;
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomDialog();
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        Log.i("saberamine", "inside ");

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment , null ).setReorderingAllowed(true).addToBackStack(null).commitAllowingStateLoss() ;
    }

    private void showBottomDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout);

        LinearLayout videoLayout = dialog.findViewById(R.id.layoutVideo);
        LinearLayout shortsLayout = dialog.findViewById(R.id.layoutShorts);
        LinearLayout liveLayout = dialog.findViewById(R.id.layoutLive);
        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);

        videoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                Toast.makeText(nav_layout_all.this,"Upload a Video is clicked",Toast.LENGTH_SHORT).show();

            }
        });

        shortsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                Toast.makeText(nav_layout_all.this,"Create a short is Clicked",Toast.LENGTH_SHORT).show();

            }
        });

        liveLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                Toast.makeText(nav_layout_all.this,"Go live is Clicked",Toast.LENGTH_SHORT).show();

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialongAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

}