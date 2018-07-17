package rrdl.be4care.Views.Activities;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rrdl.be4care.Controllers.GetDoctorDetail;
import rrdl.be4care.Controllers.GetMyDoctors;
import rrdl.be4care.Models.Doctor;
import rrdl.be4care.Models.Document;
import rrdl.be4care.Models.User;
import rrdl.be4care.R;
import rrdl.be4care.Utils.ApiService;

public class DoctorDetail extends AppCompatActivity {
    private Doctor doc;
    private TextView title, spec;
    private ListView rv;
    private EditText address, numtel, email, sturct;
    Button validate;
    ImageButton morebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("GLOBAL", Context.MODE_PRIVATE);

        setContentView(R.layout.activity_doctor_detail);
        morebtn = findViewById(R.id.morebutton);
        validate = findViewById(R.id.validation);
        address = findViewById(R.id.adress);
        numtel = findViewById(R.id.numtel);
        email = findViewById(R.id.email);
        sturct = findViewById(R.id.hstruct);
        spec = findViewById(R.id.speciality);
        address.setEnabled(false);
        numtel.setEnabled(false);
        email.setEnabled(false);
        sturct.setEnabled(false);
        ImageButton more = findViewById(R.id.morebutton);
        ImageButton dial = findViewById(R.id.tophone);
        Button retour = findViewById(R.id.goback);
        ImageButton map = findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (address.getText().equals("") || address.getText().equals(null)) {
                    Toast.makeText(DoctorDetail.this, "Adresse non disponible", Toast.LENGTH_SHORT).show();
                } else {
                    Intent mapIntent = new Intent(DoctorDetail.this, MapActivity.class);
                    mapIntent.putExtra("ADR", address.getText());
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
        morebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        ImageButton emailto = findViewById(R.id.toemail);
        emailto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mailer = new Intent(Intent.ACTION_SEND);
                mailer.setType("text/plain");
                mailer.putExtra(Intent.EXTRA_EMAIL, new String[]{(String) email.getText().toString()});
                mailer.putExtra(Intent.EXTRA_SUBJECT, "Be4Care Email");
                mailer.putExtra(Intent.EXTRA_TEXT, "Bonjour " + title.getText().toString());
                startActivity(Intent.createChooser(mailer, "Send email..."));
            }
        });
        morebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(DoctorDetail.this, R.style.NewDialog);
                dialog.requestWindowFeature(DoctorDetail.this.getWindow().FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.document_option);
                dialog.getWindow().setBackgroundDrawableResource(R.color.space_transparent);
                dialog.show();
                Button modif = dialog.findViewById(R.id.modify);
                Button favori = dialog.findViewById(R.id.favori);
                Button delete = dialog.findViewById(R.id.delete);
                if (doc.getStar()) {
                    favori.setText("Demarquer comme Raccourcis");
                }
                favori.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://peaceful-forest-76384.herokuapp.com/")
                                .addConverterFactory(GsonConverterFactory.create());
                        Retrofit retrofit = builder.build();
                        ApiService apiservice = retrofit.create(ApiService.class);

                        JsonObject obj = new JsonObject();
                        if (doc.getStar()) {
                            obj.addProperty("star", false);
                        } else {
                            obj.addProperty("star", true);
                        }
                        Call<Doctor> favourite = apiservice.favoritedoctor(prefs.getString("AUTH", ""), doc.getId(), obj);
                        favourite.enqueue(new Callback<Doctor>() {
                            @Override
                            public void onResponse(Call<Doctor> call, Response<Doctor> response) {
                                if (response.isSuccessful()) {
                                    if (doc.getStar())
                                        favori.setText("Demarquer comme Raccourcis");
                                } else {
                                    favori.setText("marquer comme Raccourcis");
                                }
                                dialog.dismiss();
                                Log.e("TAG", response.toString());
                                Toasty.success(getApplicationContext(), "Operation Terminée").show();
                            }

                            @Override
                            public void onFailure(Call<Doctor> call, Throwable t) {
                                Toasty.error(getApplicationContext(), "Echec de l'operation").show();
                                dialog.dismiss();
                            }
                        });
                    }
                });


                delete.setOnClickListener(new View.OnClickListener()

                {
                    @Override
                    public void onClick(View view) {
                        final ProgressDialog dialogloads = ProgressDialog.show(DoctorDetail.this, "",
                                "Chargement...", true);
                        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://peaceful-forest-76384.herokuapp.com/")
                                .addConverterFactory(GsonConverterFactory.create());
                        Retrofit retrofit = builder.build();
                        ApiService apiservice = retrofit.create(ApiService.class);
                        Call<JsonObject> del = apiservice.delete_doctor(prefs.getString("AUTH", ""), doc.getId());
                        del.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                if (response.isSuccessful()) {
                                    Toasty.success(DoctorDetail.this, "Operation terminée").show();
                                }
                                dialogloads.dismiss();
                                dialog.dismiss();
                                DoctorDetail.this.finish();
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                dialog.dismiss();
                                dialogloads.dismiss();
                                Toasty.error(DoctorDetail.this, "Echec de l'operation").show();
                            }
                        });
                    }
                });
                modif.setOnClickListener(new View.OnClickListener()

                {
                    @Override
                    public void onClick(View view) {
                        address.setEnabled(true);
                        numtel.setEnabled(true);
                        email.setEnabled(true);
                        sturct.setEnabled(true);
                        validate.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
        validate.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                final ProgressDialog dialogloads = ProgressDialog.show(DoctorDetail.this, "",
                        "Chargement...", true);
                address.setEnabled(false);
                numtel.setEnabled(false);
                email.setEnabled(false);
                sturct.setEnabled(false);
                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl("https://peaceful-forest-76384.herokuapp.com/")
                        .addConverterFactory(GsonConverterFactory.create());

                Retrofit retrofit = builder.build();
                ApiService apiservice = retrofit.create(ApiService.class);
                Call<List<Doctor>> get = apiservice.getalldoctors(prefs.getString("AUTH", ""));

            }
        });
        title =

                findViewById(R.id.drname);

        Gson gson = new Gson();
        doc = gson.fromJson(getIntent().getStringExtra("REF"), Doctor.class);
        Toast.makeText(DoctorDetail.this, doc.getFullName() + " from details", Toast.LENGTH_SHORT).show();

        GetDoctorDetail getDoctorDetail = new GetDoctorDetail(DoctorDetail.this, doc, title, address, numtel, email, sturct, spec);
        getDoctorDetail.getDetails();
    }
}
