package rrdl.be4care.Views.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import rrdl.be4care.Controllers.GetDoctorDetail;
import rrdl.be4care.Controllers.GetMyDoctors;
import rrdl.be4care.Models.Doctor;
import rrdl.be4care.R;

public class DoctorDetail extends AppCompatActivity {
    private Doctor doc;
    private TextView title;
    private ListView rv;
    private TextView address, numtel, email, sturct,spec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detail);
        address = findViewById(R.id.adress);
        numtel = findViewById(R.id.numtel);
        email = findViewById(R.id.email);
        sturct = findViewById(R.id.hstruct);
        spec=findViewById(R.id.speciality);
        ImageButton more = findViewById(R.id.morebutton);
        ImageButton dial = findViewById(R.id.tophone);
        Button retour=findViewById(R.id.goback);
        ImageButton map=findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if(address.getText().equals("") ||address.getText().equals(null)){
                  Toast.makeText(DoctorDetail.this, "Adresse non disponible", Toast.LENGTH_SHORT).show();
              }else{
                Intent mapIntent=new Intent(DoctorDetail.this,MapActivity.class);
                mapIntent.putExtra("ADR",address.getText());
                startActivity(mapIntent);
            }
            }
        });
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                return;
            }
        });
        dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                // Send phone number to intent as data
                intent.setData(Uri.parse("tel:" + numtel.getText().toString()));
                // Start the dialer app activity with number
                startActivity(intent);
            }
        });
        ImageButton emailto=findViewById(R.id.toemail);
        emailto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mailer = new Intent(Intent.ACTION_SEND);
                mailer.setType("text/plain");
                mailer.putExtra(Intent.EXTRA_EMAIL,new String[]{(String) email.getText()});
                mailer.putExtra(Intent.EXTRA_SUBJECT, "Be4Care Email");
                mailer.putExtra(Intent.EXTRA_TEXT, "Bonjour "+ title.getText().toString());
                startActivity(Intent.createChooser(mailer, "Send email..."));
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        title=findViewById(R.id.drname);
        Gson gson=new Gson();
        doc=gson.fromJson(getIntent().getStringExtra("REF"),Doctor.class);
        Toast.makeText(this, doc.getFullName()+" from details", Toast.LENGTH_SHORT).show();
        GetDoctorDetail getDoctorDetail=new GetDoctorDetail(this,doc,title,address,numtel,email,sturct,spec);
        getDoctorDetail.getDetails();
    }
}
