package rrdl.be4care.Views.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import rrdl.be4care.R;
import rrdl.be4care.Views.Fragments.PersonalProfileFragment;

public class ProfileActivity extends AppCompatActivity implements PersonalProfileFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        android.support.v4.app.FragmentManager fm=getSupportFragmentManager();
        fm.beginTransaction().add(R.id.profilecontainer,new PersonalProfileFragment()).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
