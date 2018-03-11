package in.ac.csa.csae_notice.Ece;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import in.ac.csa.csae_notice.Globalclass;
import in.ac.csa.csae_notice.MessageAdapter;
import in.ac.csa.csae_notice.NoticeMessage;
import in.ac.csa.csae_notice.R;

import static android.graphics.Color.BLACK;

/**
 * Created by mayank on 17/5/17.
 */

public class EceNotice extends AppCompatActivity {
    int position;
    TextView textView;
    private FirebaseDatabase mFirebaseDatabase;
    // WebView webview = (WebView) findViewById(R.id.webview);
    //  webview.getSettings().setJavaScriptEnabled(true);

    private FirebaseAuth mFirebaseAuth;
    private static final String TAG = "EceNotice";
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference mMessageDatabaseReference;
    private ListView mMessageListView;
    private ChildEventListener mChildEventListener;
    private NoticeMessage mNoticeMessage;
    int len ;
    private ImageView imgview ;

    private MessageAdapter mMessageAdapter;
    public static final int RC_SIGN_IN = 1;
    // private DatabaseReference mDatabase;
    // WebView webview ;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enotices);
        // webview = new WebView(this);
        //setContentView(webview);
        ((CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout)).setTitle("LITERARY-NOTICE SECTION") ;
        ((CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout)).setCollapsedTitleTextColor(BLACK);
        imgview = (ImageView) findViewById(R.id.picture) ;
        imgview.setImageResource(R.drawable.literary);


        mMessageListView = (ListView) findViewById(R.id.messageListView);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessageDatabaseReference = mFirebaseDatabase.getReference().child("NitukENotice").child("ECE").child("ECENotice");
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView = (TextView) findViewById(R.id.messageTextView);
        // position = getIntent().getIntExtra("position", 0);
        final List<NoticeMessage> noticeMessages = new ArrayList<NoticeMessage>();
        mMessageAdapter = new MessageAdapter(this, R.layout.item_message, noticeMessages);
        mMessageListView.setAdapter(mMessageAdapter);
        //mDatabase = FirebaseDatabase.getInstance().getReference();



        //        mMessageDatabaseReference.child("NitukENotice").addValueEventListener(new ValueEventListener() {

        SharedPreferences sharedPreferences = getSharedPreferences("BRANCHPREF", Context.MODE_PRIVATE);

        String Field = sharedPreferences.getString("branch", "default");



   // if(Field.equals("ece")) {
        Log.d("EceNotice","Condition True") ;
        System.out.println("Condition True") ;
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
                    Toast.makeText(EceNotice.this, "Database empty!!!", Toast.LENGTH_SHORT).show();
                }
                Globalclass mApp = ((Globalclass)getApplicationContext());
                mApp.setGlobalVariable(noticeMessages.size());
                mApp.setSectionVariable("ECENotice");


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

    /*}
    else
    {
        Toast.makeText(EceNotice.this, "Not Authorized!!!", Toast.LENGTH_SHORT).show();
    }
*/
    /*
        mMessageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TO MAKE CHANGES
                len = noticeMessages.size() ;

                Intent intent = new Intent(EceNotice.this, ecenoticedetail.class);
                intent.putExtra("note", len-position-1);
                //openPdf(position) ;
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
                    Toast.makeText(EceNotice.this, "Welcome to E-NOTICE SECTION", Toast.LENGTH_SHORT).show();
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
          //  AuthUI.getInstance().signOut(this);
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


















