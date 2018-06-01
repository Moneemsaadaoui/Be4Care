package rrdl.be4care.Views.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonObject;

import es.dmoral.toasty.Toasty;
import rrdl.be4care.Models.Document;
import rrdl.be4care.R;

public class DocInfoActivity extends AppCompatActivity {
    private EditText date, note, type, local;
    private AutoCompleteTextView structure, doctor;
    Button Valid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Document document = new Document();
        setContentView(R.layout.activity_doc_info);
        date = findViewById(R.id.Date);
        note = findViewById(R.id.Notes);
        type = findViewById(R.id.Doctype);
        local = findViewById(R.id.Location);
        structure = findViewById(R.id.StructSnt);
        doctor = findViewById(R.id.ProfSnt);
        Valid = findViewById(R.id.validateinfo);
        if (getIntent().getStringExtra("ocr") != null) {
            note.setText(getIntent().getStringExtra("ocr"));
        }
        Valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (
                        !date.getText().equals("") &&
                        !note.getText().equals("") &&
                        !type.getText().equals("") &&
                        !local.getText().equals("") &&
                        !structure.getText().equals("") &&
                        !doctor.getText().equals("")) {
                    document.setDate(date.getText().toString());
                    document.setNote(note.getText().toString());
                    document.setType(type.getText().toString());
                    document.setPlace(local.getText().toString());
                    document.setHStructure(structure.getText().toString());
                    document.setDr(doctor.getText().toString());
                    document.setUrl(getIntent().getStringExtra("url"));
                    document.setStar(false);

                }else{
                    Toasty.error(getBaseContext(),"Remplisser tout les champs avant d'Enregistrer").show();}

            }

        });
    }
}
