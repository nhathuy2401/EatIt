package com.example.eatit;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.eatit.EventBus.CategoryClick;
import com.example.eatit.EventBus.FoodItemClick;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
     private NavController navController ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home );
        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        FloatingActionButton fab = findViewById( R.id.fab );
        fab.setOnClickListener( view -> Snackbar.make( view, "Replace with your own action", Snackbar.LENGTH_LONG )
                .setAction( "Action", null ).show() );
        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        NavigationView navigationView = findViewById( R.id.nav_view );
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_Menu, R.id.nav_food_list,R.id.nav_food_detail )
                .setDrawerLayout( drawer )
                .build();


        navController = Navigation.findNavController( this, R.id.nav_host_fragment );
        NavigationUI.setupActionBarWithNavController( this, navController, mAppBarConfiguration );
        NavigationUI.setupWithNavController( navigationView, navController );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.home, menu );
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController( this, R.id.nav_host_fragment );
        return NavigationUI.navigateUp( navController, mAppBarConfiguration )
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register( this );
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister( this );
        super.onStop();
    }
     @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onCategorySelected(CategoryClick event)
     {
         if(event.isSuccess())
         {
             navController.navigate( R.id.nav_food_list );



             //Toast.makeText( this, "Click to "+event.getCategoryModel().getName(), Toast.LENGTH_SHORT ).show();
         }
     }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onFoodItemClick(FoodItemClick event)
    {
        if(event.isSuccess())
        {
         navController.navigate( R.id.nav_food_detail );



        }
    }



}