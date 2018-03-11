package in.ac.csa.csae_notice;

/**
 * Created by mayank on 30/5/17.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AuthBranchPreference extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private FirebaseDatabase mFirebaseDatabase;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener ;
    private DatabaseReference mMessageDatabaseReference;
    private ChildEventListener mChildEventListener;

    private int a = 0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authbanchactivity);


        mFirebaseDatabase = FirebaseDatabase.getInstance();

        mFirebaseAuth = FirebaseAuth.getInstance();

        Button genButton = (Button) findViewById(R.id.genButton);
        genButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMessageDatabaseReference = mFirebaseDatabase.getReference().child("NitukENotice").child("AuthUsers").child("ALL");


                final FirebaseUser user = mFirebaseAuth.getCurrentUser();

                System.out.println("User: " + user.getEmail());

                mChildEventListener = new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                        if (dataSnapshot.exists()) {
                            String des = dataSnapshot.getValue().toString();
                            //    System.out.println("Auth User: "+des) ;


                            if ((user.getEmail()).equals(des)) {

                                authenticatedgen();
                                a = 1;

                            } else {
//                                Toast.makeText(AuthBranchPreference.this, "You are not an ADMIN!!!", Toast.LENGTH_SHORT).show();
                            }


                        } else {

                            Toast.makeText(AuthBranchPreference.this, "You are not an ADMIN!!!", Toast.LENGTH_SHORT).show();
                        }

                        notific();
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



                finish();
            }
        });

/*
        Button cseButton = (Button) findViewById(R.id.cseButton);
        cseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMessageDatabaseReference = mFirebaseDatabase.getReference().child("NitukENotice").child("AuthUsers").child("CSE");


                final FirebaseUser user = mFirebaseAuth.getCurrentUser();

                System.out.println("User: " + user.getEmail());

                mChildEventListener = new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                        if (dataSnapshot.exists()) {
                            String des = dataSnapshot.getValue().toString();
                            //    System.out.println("Auth User: "+des) ;


                            if ((user.getEmail()).equals(des)) {
                                a = 1;
                                authenticatedcse();

                            } else {
//                                Toast.makeText(AuthBranchPreference.this, "You are not an ADMIN!!!", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            Toast.makeText(AuthBranchPreference.this, "You are not an ADMIN!!!", Toast.LENGTH_SHORT).show();
                        }

                        notific() ;
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

                finish();
            }
        });


        Button eceButton = (Button) findViewById(R.id.eceButton);
        eceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMessageDatabaseReference = mFirebaseDatabase.getReference().child("NitukENotice").child("AuthUsers").child("ECE");

                final FirebaseUser user = mFirebaseAuth.getCurrentUser();

                System.out.println("User: " + user.getEmail());

                mChildEventListener = new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                        if (dataSnapshot.exists()) {
                            String des = dataSnapshot.getValue().toString();
                            System.out.println("Auth User: " + des);


                            if ((user.getEmail()).equals(des)) {
                                a = 1;
                                authenticatedece();

                            } else {
//                                Toast.makeText(AuthBranchPreference.this, "You are not an ADMIN!!!", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            Toast.makeText(AuthBranchPreference.this, "You are not an ADMIN!!!", Toast.LENGTH_SHORT).show();
                        }

                        notific() ;

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

                finish();


            }
        });

        Button eeeButton = (Button) findViewById(R.id.eeeButton);
        eeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMessageDatabaseReference = mFirebaseDatabase.getReference().child("NitukENotice").child("AuthUsers").child("EEE");


                final FirebaseUser user = mFirebaseAuth.getCurrentUser();

                System.out.println("User: " + user.getEmail());

                mChildEventListener = new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                        if (dataSnapshot.exists()) {
                            String des = dataSnapshot.getValue().toString();
                            System.out.println("Auth User: " + des);


                            if ((user.getEmail()).equals(des)) {
                                a = 1;
                                authenticatedeee();

                            } else {
//                                Toast.makeText(AuthBranchPreference.this, "You are not an ADMIN!!!", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            Toast.makeText(AuthBranchPreference.this, "You are not an ADMIN!!!", Toast.LENGTH_SHORT).show();
                        }

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

                finish();

            }
        });
*/
        Button mechButton = (Button) findViewById(R.id.mechButton);
        mechButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMessageDatabaseReference = mFirebaseDatabase.getReference().child("NitukENotice").child("AuthUsers").child("MEC");

                final FirebaseUser user = mFirebaseAuth.getCurrentUser();

                System.out.println("User: " + user.getEmail());

                mChildEventListener = new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                        if (dataSnapshot.exists()) {
                            String des = dataSnapshot.getValue().toString();
                            System.out.println("Auth User: " + des);


                            if ((user.getEmail()).equals(des)) {
                                a = 1;
                                authenticatedmec();

                            } else {
//                                Toast.makeText(AuthBranchPreference.this, "You are not an ADMIN!!!", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            Toast.makeText(AuthBranchPreference.this, "You are not an ADMIN!!!", Toast.LENGTH_SHORT).show();
                        }

                        notific() ;
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




                finish();


            }
        });


        Button civilButton = (Button) findViewById(R.id.civilButton);
        civilButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMessageDatabaseReference = mFirebaseDatabase.getReference().child("NitukENotice").child("AuthUsers").child("CIVIL");


                final FirebaseUser user = mFirebaseAuth.getCurrentUser();

                System.out.println("User: " + user.getEmail());

                mChildEventListener = new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                        if (dataSnapshot.exists()) {
                            String des = dataSnapshot.getValue().toString();
                            System.out.println("Auth User: " + des);


                            if ((user.getEmail()).equals(des)) {
                                a = 1;
                                authenticatedcivil();

                            } else {
//                                Toast.makeText(AuthBranchPreference.this, "You are not an ADMIN!!!", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            Toast.makeText(AuthBranchPreference.this, "You are not an ADMIN!!!", Toast.LENGTH_SHORT).show();
                        }

                        notific();

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

                finish();


            }
        });

        Button acadButton = (Button) findViewById(R.id.acadButton);
        acadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMessageDatabaseReference = mFirebaseDatabase.getReference().child("NitukENotice").child("AuthUsers").child("ACAD");


                final FirebaseUser user = mFirebaseAuth.getCurrentUser();

                System.out.println("User: " + user.getEmail());

                mChildEventListener = new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                        if (dataSnapshot.exists()) {
                            String des = dataSnapshot.getValue().toString();
                            System.out.println("Auth User: " + des);


                            if ((user.getEmail()).equals(des)) {
                                a = 1;
                                authenticatedacad();

                            } else {
//                                Toast.makeText(AuthBranchPreference.this, "You are not an ADMIN!!!", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            Toast.makeText(AuthBranchPreference.this, "You are not an ADMIN!!!", Toast.LENGTH_SHORT).show();
                        }

                        notific();

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

                finish();


            }
        });

        Button advenButton = (Button) findViewById(R.id.advenButton);
        advenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMessageDatabaseReference = mFirebaseDatabase.getReference().child("NitukENotice").child("AuthUsers").child("ADVEN");


                final FirebaseUser user = mFirebaseAuth.getCurrentUser();

                System.out.println("User: " + user.getEmail());

                mChildEventListener = new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                        if (dataSnapshot.exists()) {
                            String des = dataSnapshot.getValue().toString();
                            System.out.println("Auth User: " + des);


                            if ((user.getEmail()).equals(des)) {
                                a = 1;
                                authenticatedadven();

                            } else {
//                                Toast.makeText(AuthBranchPreference.this, "You are not an ADMIN!!!", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            Toast.makeText(AuthBranchPreference.this, "You are not an ADMIN!!!", Toast.LENGTH_SHORT).show();
                        }

                        notific();

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

                finish();


            }
        });


        Button culturalButton = (Button) findViewById(R.id.culturalButton);
        culturalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMessageDatabaseReference = mFirebaseDatabase.getReference().child("NitukENotice").child("AuthUsers").child("CULT");


                final FirebaseUser user = mFirebaseAuth.getCurrentUser();

                System.out.println("User: " + user.getEmail());

                mChildEventListener = new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                        if (dataSnapshot.exists()) {
                            String des = dataSnapshot.getValue().toString();
                            System.out.println("Auth User: " + des);


                            if ((user.getEmail()).equals(des)) {
                                a = 1;
                                authenticatedcult();

                            } else {
//                                Toast.makeText(AuthBranchPreference.this, "You are not an ADMIN!!!", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            Toast.makeText(AuthBranchPreference.this, "You are not an ADMIN!!!", Toast.LENGTH_SHORT).show();
                        }

                        notific();

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

                finish();


            }
        });


        Button lostandfoundButton = (Button) findViewById(R.id.lostButton);
        lostandfoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMessageDatabaseReference = mFirebaseDatabase.getReference().child("NitukENotice").child("AuthUsers").child("LOST");


                final FirebaseUser user = mFirebaseAuth.getCurrentUser();

                System.out.println("User: " + user.getEmail());

                mChildEventListener = new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                        if (dataSnapshot.exists()) {
                            String des = dataSnapshot.getValue().toString();
                            System.out.println("Auth User: " + des);
                            if ((user.getEmail()).equals(des)) {
                                a = 1;
                                authenticatedlost();

                            } else {
//                                Toast.makeText(AuthBranchPreference.this, "You are not an ADMIN!!!", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(AuthBranchPreference.this, "You are not an ADMIN!!!", Toast.LENGTH_SHORT).show();
                        }

                        notific();

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

                finish();


            }
        });





    }



   public void notific()
   {
      if(a == 0)
       Toast.makeText(AuthBranchPreference.this, "You are not an ADMIN!!!", Toast.LENGTH_SHORT).show();



}
    public void authenticatedgen()
    {




        Intent intent = new Intent(this, SendTextNotification.class) ;
        intent.putExtra("branch", "gen") ;
        startActivity(intent);

    }



    public void authenticatedcse()
    {

        Intent intent = new Intent(this, SendTextNotification.class) ;
        intent.putExtra("branch", "cse") ;
        startActivity(intent);

    }

    public void authenticatedeee()
    {

        Intent intent = new Intent(this, SendTextNotification.class) ;
        intent.putExtra("branch", "eee") ;
        startActivity(intent);

    }

    public void authenticatedece()
    {

        Intent intent = new Intent(this, SendTextNotification.class) ;
        intent.putExtra("branch", "ece") ;
        startActivity(intent);

    }

    public void authenticatedmec()
    {

        Intent intent = new Intent(this, SendTextNotification.class) ;
        intent.putExtra("branch", "mec") ;
        startActivity(intent);

    }
    public void authenticatedcivil()
    {

        Intent intent = new Intent(this, SendTextNotification.class) ;
        intent.putExtra("branch", "civil") ;
        startActivity(intent);

    }


    public void authenticatedacad()
    {

        Intent intent = new Intent(this, SendTextNotification.class) ;
        intent.putExtra("branch", "academic") ;
        startActivity(intent);

    }

    public void authenticatedadven()
    {

        Intent intent = new Intent(this, SendTextNotification.class) ;
        intent.putExtra("branch", "adventure") ;
        startActivity(intent);

    }
    public void authenticatedcult()
    {

        Intent intent = new Intent(this, SendTextNotification.class) ;
        intent.putExtra("branch", "cultural") ;
        startActivity(intent);

    }
    public void authenticatedlost()
    {

        Intent intent = new Intent(this, SendTextNotification.class) ;
        intent.putExtra("branch", "lost") ;
        startActivity(intent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            AuthUI.getInstance().signOut(this);
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onPause() {
        super.onPause() ;

        // mApp.mGlobalVarValue = globalVarValue;

    }
    @Override
    protected void onResume() {
        super.onResume() ;

    }


}
