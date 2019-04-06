package com.leonick.pumpyourself;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import com.leonick.pumpyourself.fragments.GroupsFragment;
import com.leonick.pumpyourself.fragments.MealFragment;
import com.leonick.pumpyourself.fragments.ProfileFragment;
import com.leonick.pumpyourself.fragments.TrainingsFragments;


public class MainActivity extends AppCompatActivity {

    final Fragment fragment1 = new MealFragment();
    final Fragment fragment2 = new TrainingsFragments();
    final Fragment fragment3 = new GroupsFragment();
    final Fragment fragment4 = new ProfileFragment();

    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;
    Toolbar toolbar;

    TextView tvToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tvToolbarTitle = toolbar.findViewById(R.id.toolbarTextView);
        tvToolbarTitle.setText("Meal");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fm.beginTransaction().add(R.id.main_container,fragment4, "1").hide(fragment4).commit();
        fm.beginTransaction().add(R.id.main_container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.main_container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.main_container,fragment1, "1").commit();

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_meal:
                    fm.beginTransaction().hide(active).show(fragment1).commit();
                    active = fragment1;
                    tvToolbarTitle.setText("Meal");
                    return true;

                case R.id.navigation_trainings:
                    fm.beginTransaction().hide(active).show(fragment2).commit();
                    active = fragment2;
                    tvToolbarTitle.setText("Trainings");
                    return true;

                case R.id.navigation_groups:
                    fm.beginTransaction().hide(active).show(fragment3).commit();
                    active = fragment3;
                    tvToolbarTitle.setText("Groups");
                    return true;

                case R.id.navigation_profile:
                    fm.beginTransaction().hide(active).show(fragment4).commit();
                    active = fragment4;
                    tvToolbarTitle.setText("Profile");
                    return true;
            }
            return false;
        }
    };

}