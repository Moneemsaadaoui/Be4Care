package rrdl.be4care.Views.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rrdl.be4care.Controllers.GetDoctorDetail;
import rrdl.be4care.Controllers.GetstruckDetals;
import rrdl.be4care.Models.Doctor;
import rrdl.be4care.Models.Structure;
import rrdl.be4care.R;
import rrdl.be4care.Utils.ApiService;

public class StruckDetailActivity extends AppCompatActivity {
    private Structure doc;
    private TextView title, spec;
    private ListView rv;
    private EditText address, numtel, email, sturct;
    Button validate;
    ImageButton morebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("GLOBAL", Context.MODE_PRIVATE);

        setContentView(rrdl.be4care.R.layout.activity_struck_detail);
        morebtn = findViewById(R.id.morebutton);
        validate = findViewById(R.id.validation);
        address = findViewById(R.id.adress);
        numtel = findViewById(R.id.numtel);
        email = findViewById(R.id.email);
        ImageButton emailto = findViewById(R.id.toemail);
        ImageButton map = findViewById(R.id.map);
        ImageButton dial = findViewById(R.id.tophone);
        title = findViewById(R.id.struckname);

        address.setEnabled(false);
        numtel.setEnabled(false);
        email.setEnabled(false);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (address.getText().equals("") || address.getText().equals(null)) {
                    Toast.makeText(StruckDetailActivity.this, "Adresse non disponible", Toast.LENGTH_SHORT).show();
                } else {
                    Intent mapIntent = new Intent(StruckDetailActivity.this, MapActivity.class);
                    mapIntent.putExtra("ADR", address.getText());
                    startActivity(mapIntent);
                }
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

                final Dialog dialog = new Dialog(StruckDetailActivity.this, R.style.NewDialog);
                dialog.requestWindowFeature(StruckDetailActivity.this.getWindow().FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.document_option);
                dialog.getWindow().setBackgroundDrawableResource(R.color.space_transparent);
                dialog.show();
                Button modif = dialog.findViewById(R.id.modify);
                Button favori = dialog.findViewById(R.id.favori);
                Button delete = dialog.findViewById(R.id.delete);
                modif.setVisibility(View.GONE);

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
                        Call<Structure> favourite = apiservice.favoritestruck(prefs.getString("AUTH", ""), doc.getId(), obj);
                        favourite.enqueue(new Callback<Structure>() {
                            @Override
                            public void onResponse(Call<Structure> call, Response<Structure> response) {
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
                            public void onFailure(Call<Structure> call, Throwable t) {
                                Toasty.error(getApplicationContext(), "Echec de l'operation").show();
                                dialog.dismiss();
                            }
                        });
                    }
                });

            }
        });
        Gson gson = new Gson();
        doc = gson.fromJson(getIntent().getStringExtra("STRREF"), Structure.class);
        Toast.makeText(StruckDetailActivity.this, doc.getFullName() + " from details", Toast.LENGTH_SHORT).show();
        GetstruckDetals getDoctorDetail = new GetstruckDetals(this, doc, title, address, numtel, email);
        getDoctorDetail.getDetails();
    }
}
