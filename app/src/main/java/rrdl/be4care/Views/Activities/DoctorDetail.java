package rrdl.be4care.Views.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import rrdl.be4care.Controllers.GetDoctorDetail;
import rrdl.be4care.Models.Doctor;
import rrdl.be4care.R;

public class DoctorDetail extends AppCompatActivity {
    private Doctor doc;
    private TextView title;
    private ListView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detail);
        title=findViewById(R.id.drname);
        rv=findViewById(R.id.datalist);
        Gson gson=new Gson();
        Doctor doc= gson.fromJson(getIntent().getStringExtra("DOC_REF"),Doctor.class);
        GetDoctorDetail service=new GetDoctorDetail(this,doc,title,rv);
        Toast.makeText(this, doc.getFullName(), Toast.LENGTH_SHORT).show();
        service.getDetails();

    }
}
