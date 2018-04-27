package rrdl.be4care.Views.Activities;

import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import rrdl.be4care.Views.Fragments.AddDoc.InfoFragment;
import rrdl.be4care.Views.Fragments.AddDoc.PickPictureFragment;
import rrdl.be4care.R;

public class AddDocumentActivity extends AppCompatActivity implements InfoFragment.OnFragmentInteractionListener,PickPictureFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_document);
      /*      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);}*/
        android.support.v4.app.FragmentManager fm=getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.FragmentContainer,new PickPictureFragment()).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
