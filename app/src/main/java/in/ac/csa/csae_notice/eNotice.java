package in.ac.csa.csae_notice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.BLACK;

/**
 * Created by mayank on 17/5/17.
 */

public class eNotice extends AppCompatActivity {
    int position;
    TextView textView;
    ImageView imageView ;
    private FirebaseDatabase mFirebaseDatabase;
    // WebView webview = (WebView) findViewById(R.id.webview);
    //  webview.getSettings().setJavaScriptEnabled(true);

    private FirebaseAuth mFirebaseAuth;
    private static final String TAG = "eNotice";
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference mMessageDatabaseReference;
    private ListView mMessageListView;
    private ChildEventListener mChildEventListener;
    private NoticeMessage mNoticeMessage;
    private ImageView imgview ;
    private MessageAdapter mMessageAdapter;
    public static final int RC_SIGN_IN = 1;
    int len ;
    // private DatabaseReference mDatabase;
    // WebView webview ;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enotices);
        // webview = new WebView(this);
        //setContentView(webview);
        ((CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout)).setTitle("E-NOTICE SECTION") ;
        ((CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout)).setCollapsedTitleTextColor(BLACK);


        mMessageListView = (ListView) findViewById(R.id.messageListView);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessageDatabaseReference = mFirebaseDatabase.getReference().child("NitukENotice").child("College").child("ENotice");
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView = (TextView) findViewById(R.id.messageTextView);
        imageView = (ImageView) findViewById(R.id.messageImageView) ;
        // position = getIntent().getIntExtra("position", 0);
        final List<NoticeMessage> noticeMessages = new ArrayList<NoticeMessage>();

        mMessageAdapter = new MessageAdapter(this, R.layout.item_message, noticeMessages);
        mMessageListView.setAdapter(mMessageAdapter);
        //mDatabase = FirebaseDatabase.getInstance().getReference();



        //        mMessageDatabaseReference.child("NitukENotice").addValueEventListener(new ValueEventListener() {


        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                if (dataSnapshot.exists()) {
                    // String des = dataSnapshot.getValue().toString();
                    //textView.setText(des);
                    NoticeMessage mnoticeMessage = dataSnapshot.getValue(NoticeMessage.class);

                    noticeMessages.add(0, mnoticeMessage);
                    mMessageAdapter.notifyDataSetChanged();

                    //mMessageAdapter.add(mnoticeMessage);
                    progressBar.setVisibility(View.INVISIBLE);



                } else {
                    Toast.makeText(eNotice.this, "Database empty!!!", Toast.LENGTH_SHORT).show();
                }
                Globalclass mApp = ((Globalclass)getApplicationContext());
                mApp.setGlobalVariable(noticeMessages.size());
                mApp.setSectionVariable("eNotice");
            }


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mMessageDatabaseReference.addChildEventListener(mChildEventListener);



       // System.out.println("Length 2:"+mApp.getGlobalVariable()) ;





/*

        mMessageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TO MAKE CHANGES

                System.out.println("Adapter View: "+view.getRootView()) ;




                System.out.println("Adapter view: "+view) ;
                     len = noticeMessages.size();
                    Intent intent = new Intent(eNotice.this, noticedetail.class);
                    intent.putExtra("note", len - position - 1);
                    //openPdf(position) ;
                    Log.d(TAG, "len position: " + len);

                    Log.d(TAG, "Intent position: " + position);
                    System.out.println("Intent postion" + position);
                    Log.d(TAG, "Intent id: " + id);
                    startActivity(intent);
                          }
        });
        */


        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if ((user != null)) {
                    Toast.makeText(eNotice.this, "Welcome to E-NOTICE SECTION", Toast.LENGTH_SHORT).show();
                } else {
                    startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setIsSmartLockEnabled(false).setProviders(AuthUI.EMAIL_PROVIDER, AuthUI.GOOGLE_PROVIDER).build(), RC_SIGN_IN);
                }

            }

        };
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action1_settings) {
            //AuthUI.getInstance().signOut(this);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                //                Toast.makeText(this, "Signed in!", Toast.LENGTH_SHORT).show() ;

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Sign in cancelled", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);

        // mApp.mGlobalVarValue = globalVarValue;

    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);

    }

}

















   /* public void ClickMessage(View view)

    {

        Intent intent = new Intent(this, noticedetail.class);
        startActivity(intent) ;

    }
*//*
   public void openPdf(int pos)
   {
       mDatabase.child("NitukENotice").child("pdf").child("" + pos).child("link").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               if (dataSnapshot.exists()) {
                   String url = dataSnapshot.getValue().toString();
                  // Uri uri = Uri.parse(url);
                   //Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                   //startActivity(intent);
                   //Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                   //intent.setDataAndType(Uri.parse(url), "application/pdf");
                   //startActivity(Intent.createChooser(intent, "Choose an Application:"));
                   webview.loadUrl(url);

               } else {
                   Toast.makeText(eNotice.this, "item not found!!!", Toast.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });

   }
*/


