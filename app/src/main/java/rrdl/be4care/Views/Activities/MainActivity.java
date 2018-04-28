package rrdl.be4care.Views.Activities;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

import rrdl.be4care.Views.Fragments.MainUIFragments.DocumentsFragment;
import rrdl.be4care.Views.Fragments.MainUIFragments.ProfileFragment;
import rrdl.be4care.Views.Fragments.MainUIFragments.SearchFragment;
import rrdl.be4care.Views.Fragments.MainUIFragments.ShortcutFragment;
import rrdl.be4care.R;

public class MainActivity extends AppCompatActivity implements ProfileFragment.OnFragmentInteractionListener,
        SearchFragment.OnFragmentInteractionListener, ShortcutFragment.OnFragmentInteractionListener, DocumentsFragment.OnFragmentInteractionListener {

    SpaceNavigationView spaceNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);}
        final android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.MainContainer,new SearchFragment()).commit();


   /*     BottomNavigationView mBottomBar;
        mBottomBar = findViewById(R.id.Bottombar);
        mBottomBar.inflateMenu(R.menu.bottom_nav);*/




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

