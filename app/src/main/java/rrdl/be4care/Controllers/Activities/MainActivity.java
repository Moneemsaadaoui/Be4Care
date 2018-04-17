package rrdl.be4care.Controllers.Activities;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

import rrdl.be4care.Controllers.Fragments.DocumentsFragment;
import rrdl.be4care.Controllers.Fragments.ProfileFragment;
import rrdl.be4care.Controllers.Fragments.SearchFragment;
import rrdl.be4care.Controllers.Fragments.ShortcutFragment;
import rrdl.be4care.R;

public class MainActivity extends AppCompatActivity implements ProfileFragment.OnFragmentInteractionListener,
        SearchFragment.OnFragmentInteractionListener, ShortcutFragment.OnFragmentInteractionListener, DocumentsFragment.OnFragmentInteractionListener {

    SpaceNavigationView spaceNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Intent intent=new Intent(MainActivity.this,Main2Activity.class);
        startActivity(intent);*/
        final android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        SpaceNavigationView spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
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
                Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                startActivity(intent);
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                switch (itemIndex) {
                    case 0:
                        fm.beginTransaction().replace(R.id.MainContainer, new DocumentsFragment()).commit();
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
    public void onFragmentInteraction(Uri uri) {

    }
}

