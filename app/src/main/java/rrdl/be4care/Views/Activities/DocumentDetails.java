package rrdl.be4care.Views.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import rrdl.be4care.Controllers.GetDocumentDetails;
import rrdl.be4care.Models.Document;
import rrdl.be4care.R;
import rrdl.be4care.Utils.ApiService;
import rrdl.be4care.Utils.RoomDAO;
import rrdl.be4care.Utils.RoomDB;

public class DocumentDetails extends AppCompatActivity {
    private Document doc;
    private ImageView preview;
    private TextView title;
    private ListView rv;
    private TextView type, date, profs, structs, lieu, note;
    private ImageButton favicon;
    private Button morebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_details);
        morebtn = findViewById(R.id.morebtn);
        preview = findViewById(R.id.preview);
        title = findViewById(R.id.drname);
        Gson gson = new Gson();
        type = findViewById(R.id.type);
        date = findViewById(R.id.date);
        profs = findViewById(R.id.profsante);
        structs = findViewById(R.id.Hstruct);
        lieu = findViewById(R.id.place);
        note = findViewById(R.id.notes);
        Document doc = gson.fromJson(getIntent().getStringExtra("DOC_REF"), Document.class);
        GetDocumentDetails service = new GetDocumentDetails(this, doc, preview, title,
                type, date, profs, structs, lieu, note);
        service.getDetails();

        morebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getSharedPreferences("GLOBAL", Context.MODE_PRIVATE);
                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl("https://peaceful-forest-76384.herokuapp.com/")
                        .addConverterFactory(GsonConverterFactory.create());
                Retrofit retrofit = builder.build();
                ApiService apiservice = retrofit.create(ApiService.class);

                final Dialog dialog = new Dialog(DocumentDetails.this, R.style.NewDialog);
                dialog.requestWindowFeature(DocumentDetails.this.getWindow().FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.document_option);
                dialog.getWindow().setBackgroundDrawableResource(R.color.space_transparent);
                dialog.show();
                Button edit = dialog.findViewById(R.id.edit);
                Button delete = dialog.findViewById(R.id.delete);
                Button cancel = dialog.findViewById(R.id.cancel);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final ProgressDialog dialog = ProgressDialog.show(DocumentDetails.this, "",
                                "Chargement...", true);
                        Call<JsonObject>delete=apiservice.delete_document(prefs.getString("AUTH",""),
                                gson.fromJson(getIntent().getStringExtra("DOC_REF"), Document.class).getId());
                        delete.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                if(response.isSuccessful()){
                                    Toasty.success(DocumentDetails.this,"Document Supprimé avec success").show();
                                    dialog.dismiss();
                                   RoomDB db= RoomDB.getINSTANCE(DocumentDetails.this);
                                   db.Dao().nukeDocument();
                                    DocumentDetails.this.finish();
                                }else{                            Toasty.error(DocumentDetails.this,"Echec de l'operation").show();
                                dialog.dismiss();}
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {
                            Toasty.error(DocumentDetails.this,"Echec de l'operation").show();
                            dialog.dismiss();
                            }
                        });
                    }
                });


            }
        });
    }
}
