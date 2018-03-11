package in.ac.csa.csae_notice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by mayank on 29/5/17.
 */

public class notedisplay extends AppCompatActivity {

    int position ;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notedisplay);

       // WebView browser = (WebView) findViewById(R.id.webview);
        position = getIntent().getIntExtra("note", 0);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("NitukENotice").child("pdf").child("" + position).child("link").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String url = dataSnapshot.getValue().toString();
                    WebView browser = (WebView) findViewById(R.id.webview);

                    browser.setWebViewClient(new MyBrowser());
                    browser.getSettings().setLoadsImagesAutomatically(true);
                    browser.getSettings().setJavaScriptEnabled(true);
                    browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                    browser.loadUrl(url);
                    System.out.println("Loaded !!!!" + url) ;


                } else {
                    Toast.makeText(notedisplay.this, "item not found!!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






          }


    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


}
