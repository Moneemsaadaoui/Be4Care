package rrdl.be4care.Views.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.anupcowkur.reservoir.Reservoir;
import com.google.gson.Gson;

import java.io.IOException;

import rrdl.be4care.Controllers.GetDocumentDetails;
import rrdl.be4care.Models.Document;
import rrdl.be4care.R;
import rrdl.be4care.Utils.DocumentDetailAdapter;

public class DocumentDetails extends AppCompatActivity {
    private Document doc;
    private ImageView preview;
    private TextView title;
    private ListView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_details);
        preview=findViewById(R.id.preview);
        title=findViewById(R.id.drname);
        rv=findViewById(R.id.datalist);
        Gson gson=new Gson();
        Document doc= gson.fromJson(getIntent().getStringExtra("DOC_REF"),Document.class);
        GetDocumentDetails service=new GetDocumentDetails(this,doc,preview,title,rv);
        Toast.makeText(this, doc.getDr(), Toast.LENGTH_SHORT).show();
        service.getDetails();
    }
}
