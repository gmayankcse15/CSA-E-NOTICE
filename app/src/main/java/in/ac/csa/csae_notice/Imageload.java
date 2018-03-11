package in.ac.csa.csae_notice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by mayank on 21/6/17.
 */

public class Imageload extends AppCompatActivity {


    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessageDatabaseReference;

    int position ;
    String brnch ;

    ProgressBar progressBar ;
    private ChildEventListener mChildEventListener;
    WebView webView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);

        position = getIntent().getIntExtra("note", 0);
         // mFirebaseDatabase = FirebaseDatabase.getInstance();
      //  mMessageDatabaseReference = mFirebaseDatabase.getReference();//.child("NitukENotice").child("College").child("Image").child(""+position).child("url");

        Globalclass mApp = ((Globalclass)getApplicationContext());
        String sec = mApp.getSectionVariable() ;

        if(sec.equals("ACADNotice"))
            brnch = "ACADEMIC";

        if(sec.equals("ADVENotice"))
            brnch = "ADVENTURE";

        if(sec.equals("LOSTNotice"))
            brnch = "LostandFound";

        if(sec.equals("CULTNotice"))
            brnch = "CULTURAL" ;

        if(sec.equals("eNotice"))
            brnch = "College" ;

        if(sec.equals("CSENotice"))
            brnch = "CSE" ;

        if(sec.equals("ECENotice"))
            brnch = "ECE";
        if(sec.equals("EEENotice"))
            brnch="EEE" ;
        if(sec.equals("CIVILNotice"))
            brnch = "CIVIL" ;
        if(sec.equals("MECNotice"))
            brnch = "MEC" ;







         System.out.println("Positon:"+position) ;
        mMessageDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mMessageDatabaseReference.child("NitukENotice").child(brnch).child("Image").child(""+position).child("url").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                   String url = dataSnapshot.getValue().toString();
                      System.out.println("URL: "+url) ;
                      webView.loadUrl(url) ;

                } else {

                    Toast.makeText(Imageload.this, "Database empty!!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                progressBar.setProgress(progress);
                if (progress == 100) {
                    progressBar.setVisibility(View.GONE);

                } else
                    {
                    progressBar.setVisibility(View.VISIBLE);

                }
            }
        });










    }




    }
