package rrdl.be4care.Views.Activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import rrdl.be4care.R;
import rrdl.be4care.Views.Fragments.DetailFragments.DoctorListingFragment;
import rrdl.be4care.Views.Fragments.MainUIFragments.DocumentsFragment;
import rrdl.be4care.Views.Fragments.MainUIFragments.ProfileFragment;
import rrdl.be4care.Views.Fragments.MainUIFragments.SearchFragment;
import rrdl.be4care.Views.Fragments.MainUIFragments.ShortcutFragment;
import rrdl.be4care.Views.Fragments.Repertoire;
import rrdl.be4care.Views.Fragments.allDoctors;
import rrdl.be4care.Views.Fragments.profile.PersonalProfileFragment;

public class MainActivity extends AppCompatActivity implements DoctorListingFragment.OnFragmentInteractionListener,PersonalProfileFragment.OnFragmentInteractionListener,ProfileFragment.OnFragmentInteractionListener,
        allDoctors.OnFragmentInteractionListener,SearchFragment.OnFragmentInteractionListener,Repertoire.OnFragmentInteractionListener, ShortcutFragment.OnFragmentInteractionListener, DocumentsFragment.OnFragmentInteractionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        final android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.MainContainer, new DocumentsFragment()).commit();

        SharedPreferences prefs=getSharedPreferences("GLOBAL",MODE_PRIVATE);
        if(prefs==null) {
            prefs.edit().putString("TOKEN", getIntent().getExtras().getString("TOKEN")).commit();
        }
        Toast.makeText(this, prefs.getString("AUTH","") + "from shared prefz", Toast.LENGTH_SHORT).show();

        BottomBar mBottomBar;
        mBottomBar = findViewById(R.id.bottomBar);
        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                switch (tabId) {
                    case R.id.tab_document:
                        fm.beginTransaction().replace(R.id.MainContainer, new DocumentsFragment()).commit();
                        break;
                    case R.id.tab_search:
                        fm.beginTransaction().replace(R.id.MainContainer, new SearchFragment()).commit();
                        break;
                    case R.id.tab_add:
                        Intent intent = new Intent(MainActivity.this, AddDocumentActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.tab_shortcut:
                        fm.beginTransaction().replace(R.id.MainContainer, new ShortcutFragment()).commit();
                        break;
                    case R.id.tab_profile:
                        fm.beginTransaction().replace(R.id.MainContainer, new ProfileFragment()).commit();
                        break;
                }
            }
        });




      /*  SpaceNavigationView spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.showTextOnly();
        spaceNavigationView.addSpaceItem(new SpaceItem("Documents", R.mipmap.file));
        spaceNavigationView.addSpaceItem(new SpaceItem("Recherche", R.mipmap.file));
        spaceNavigationView.setCentreButtonIcon(R.drawable.ic_add_black_24dp);
        spaceNavigationView.addSpaceItem(new SpaceItem("Raccourcis", R.drawable.file));
        spaceNavigationView.addSpaceItem(new SpaceItem("Mon Compte", R.drawable.file));
        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                Intent intent = new Intent(MainActivity.this, AddDocumentActivity.class);
                startActivity(intent);
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                switch (itemIndex) {
                    case 0:
                        Intent intent=new Intent(MainActivity.this,WelcomeActivity.class);
                        startActivity(intent);
                      //  fm.beginTransaction().replace(R.id.MainContainer, new DocumentsFragment()).commit();
                        break;
                    case 1:
                        fm.beginTransaction().replace(R.id.MainContainer, new SearchFragment()).commit();
                        break;
                    case 2:
                        fm.beginTransaction().replace(R.id.MainContainer, new ShortcutFragment()).commit();
                        break;
                    case 3:
                        fm.beginTransaction().replace(R.id.MainContainer, new ProfileFragment()).commit();
                        break;
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {

            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            View decorView=getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

*/
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
/*
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

            super.onSaveInstanceState(outState);
            spaceNavigationView.onSaveInstanceState(outState);
        }*/

}

