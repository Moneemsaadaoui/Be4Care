package rrdl.be4care.Views.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import rrdl.be4care.Controllers.GetDocumentDetails;
import rrdl.be4care.Models.Document;
import rrdl.be4care.R;

public class DocumentDetails extends AppCompatActivity {
    private Document doc;
    private ImageView preview;
    private TextView title;
    private ListView rv;
    private TextView type,date,profs,structs,lieu,note;
    private ImageButton favicon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_details);
        preview=findViewById(R.id.preview);
        title=findViewById(R.id.drname);
        Gson gson=new Gson();
        favicon=findViewById(R.id.favicondocu);
        type=findViewById(R.id.type);
        date=findViewById(R.id.date);
        profs=findViewById(R.id.profsante);
        structs=findViewById(R.id.Hstruct);
        lieu=findViewById(R.id.place);
        note=findViewById(R.id.notes);
        Document doc= gson.fromJson(getIntent().getStringExtra("DOC_REF"),Document.class);
        GetDocumentDetails service=new GetDocumentDetails(this,doc,preview,title,
                type,date,profs,structs,lieu,note,favicon);
        service.getDetails();
    }
}
