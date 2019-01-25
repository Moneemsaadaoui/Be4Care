package rrdl.be4care.Views.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.Calendar;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rrdl.be4care.Models.Doctor;
import rrdl.be4care.Models.Document;
import rrdl.be4care.Models.ProcessableDocument;
import rrdl.be4care.R;
import rrdl.be4care.Utils.ApiService;
import rrdl.be4care.Utils.RoomDB;

public class DocInfoActivity extends AppCompatActivity {
    private EditText date, note, type, local;
    private AutoCompleteTextView structure, doctor;
    Button Valid, cancel, retour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_info);

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("GLOBAL", Context.MODE_PRIVATE);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://peaceful-forest-76384.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        ApiService apiservice = retrofit.create(ApiService.class);
        date = findViewById(R.id.Date);
        note = findViewById(R.id.Notes);
        type = findViewById(R.id.Doctype);
        local = findViewById(R.id.Location);
        structure = findViewById(R.id.StructSnt);
        doctor = findViewById(R.id.ProfSnt);
        Valid = findViewById(R.id.validateinfo);
        cancel = findViewById(R.id.infocancel);
        retour = findViewById(R.id.infoback);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DocInfoActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (getIntent().getStringExtra("ocr") != null) {
            note.setText(getIntent().getStringExtra("ocr"));
        }

        Valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog dialog = ProgressDialog.show(DocInfoActivity.this, "",
                        "Enregistrement...", true);
                Toasty.success(getBaseContext(), "Document ajouter avec success").show();

                ProcessableDocument document = new ProcessableDocument();
                document.setDate(Calendar.getInstance().getTime().toString());
                document.setNote(note.getText().toString());
                document.setType(type.getText().toString());
                document.setPlace(local.getText().toString());
                document.setHStructure(structure.getText().toString());
                document.setDr(doctor.getText().toString());
                document.setUrl(getIntent().getStringExtra("url"));
                document.setStar(false);
                Call<ProcessableDocument> postdocument = apiservice.postDocument(prefs.getString("AUTH", ""), document);
                postdocument.enqueue(new Callback<ProcessableDocument>() {
                    @Override
                    public void onResponse(Call<ProcessableDocument> call, Response<ProcessableDocument> response) {
                        Toasty.success(getApplicationContext(), "Document ajouté avec success").show();
                        dialog.dismiss();
                        Log.e("TAG", response.toString());

                    }

                    @Override
                    public void onFailure(Call<ProcessableDocument> call, Throwable t) {
                        Toasty.error(getApplicationContext(), "Echec de l'operation").show();
                        dialog.dismiss();
                    }
                });
            }

            public boolean checkfields() {
                return (date.getText().equals("") ||
                        note.getText().equals("") ||
                        type.getText().equals("") ||
                        local.getText().equals("") ||
                        structure.getText().equals("") ||
                        doctor.getText().equals(""));
            }
        });
    }
}
