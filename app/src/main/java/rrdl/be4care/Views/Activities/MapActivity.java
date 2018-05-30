package rrdl.be4care.Views.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import rrdl.be4care.R;

public class MapActivity extends AppCompatActivity {
    private String baseurl="https://www.google.com/maps/search/";
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        webView=findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadUrl(baseurl+getIntent().getStringExtra("ADR"));

    }
}
