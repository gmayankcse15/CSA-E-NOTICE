package in.ac.csa.csae_notice;

/**
 * Created by mayank on 30/5/17.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

import in.ac.csa.csae_notice.Academic.AcadNotice;
import in.ac.csa.csae_notice.Adventure.AdvenNotice;
import in.ac.csa.csae_notice.Civil.CivilNotice;
import in.ac.csa.csae_notice.Cse.CseNotice;
import in.ac.csa.csae_notice.Cultural.CulNotice;
import in.ac.csa.csae_notice.Ece.EceNotice;
import in.ac.csa.csae_notice.Eee.EeeNotice;
import in.ac.csa.csae_notice.LostFound.LostandFound;
import in.ac.csa.csae_notice.Mech.MecNotice;

public class BranchPreference extends AppCompatActivity {
    SharedPreferences sharedPreferences ;
    private static final String TAG = "MainActivity";
    private FirebaseAuth mFirebaseAuth;
    boolean isRunning = true;
    Handler mHandler = new Handler();
    SharedPreferences.Editor editor ;
    public static final int RC_SIGN_IN = 1 ;
    SharedPreferences pref ;
    private FirebaseAuth.AuthStateListener mAuthStateListener ;

    Bundle extras ;
    int a = 0 ;
    Intent intent ;
    private TextView tvNotificationDetails;

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.branchradiooptions);

        initControls();
       setNotificationData(getIntent().getExtras());






        mFirebaseAuth = FirebaseAuth.getInstance();
        pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);


        if(isNetworkAvailable()) {
            if (pref.getBoolean("activity_executed", false)) {


                    intent = new Intent(this, MainActivity.class);


                startActivity(intent);
                finish();

            }
            else {
                SharedPreferences.Editor ed = pref.edit();
                ed.putBoolean("activity_executed", true);
                ed.commit();
            }

        }else
        {
            Toast.makeText(BranchPreference.this, "Check your network connection!!!", Toast.LENGTH_SHORT).show();
            finish() ;
        }




        intent = new Intent(this, MainActivity.class) ;





        sharedPreferences = getSharedPreferences("BRANCHPREF", Context.MODE_PRIVATE);
         editor = sharedPreferences.edit() ;




//        final CheckBox cseRadioButton = (CheckBox) findViewById(R.id.radio_cse) ;
  //      final CheckBox eceRadioButton = (CheckBox) findViewById(R.id.radio_ece) ;
    //    final CheckBox eeeRadioButton = (CheckBox) findViewById(R.id.radio_eee) ;
        final CheckBox mechRadioButton = (CheckBox) findViewById(R.id.radio_mech) ;
        final CheckBox civilRadioButton = (CheckBox) findViewById(R.id.radio_civil) ;
        final CheckBox academicRadioButton = (CheckBox) findViewById(R.id.radio_acad);
        final CheckBox adventureRadioButton = (CheckBox) findViewById(R.id.radio_adven);
        final CheckBox culturalRadioButton = (CheckBox) findViewById(R.id.radio_cult);
        final CheckBox LostandFoundRadioButton = (CheckBox) findViewById(R.id.radio_lostandfound) ;

        Button submit = (Button) findViewById(R.id.submitreport) ;


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean checkedcse , checkedece, checkedeee, checkedmech, checkedcivil , checkedacad, checkedadven, checkedcult, checkedlost;

         //       checkedcse = cseRadioButton.isChecked() ;
          //      checkedece = eceRadioButton.isChecked() ;
            //    checkedeee = eeeRadioButton.isChecked() ;
                checkedmech = mechRadioButton.isChecked() ;
                checkedcivil = civilRadioButton.isChecked() ;
                checkedacad = academicRadioButton.isChecked() ;
                checkedadven = adventureRadioButton.isChecked();
                checkedcult = culturalRadioButton.isChecked();
                checkedlost = LostandFoundRadioButton.isChecked() ;

                if(checkedacad)
                {
                  FirebaseMessaging.getInstance().subscribeToTopic("academic");
                    editor.putString("branch","academic") ;
                    editor.commit() ;

                }
                if(checkedadven)
                {
                    FirebaseMessaging.getInstance().subscribeToTopic("adventure");
                    editor.putString("branch","adventure");
                    editor.commit();

                }
                if(checkedcult)
                {
                    FirebaseMessaging.getInstance().subscribeToTopic("cultural");
                    editor.putString("branch","cultural") ;
                    editor.commit() ;

                }
                if(checkedlost) {
                    FirebaseMessaging.getInstance().subscribeToTopic("lostandfound");
                    editor.putString("branch", "lostandfound");
                    editor.commit();
                }
/*                if(checkedcse)
                {
                    // [START subscribe_topics]
                    FirebaseMessaging.getInstance().subscribeToTopic("cse");
                    FirebaseMessaging.getInstance().subscribeToTopic("all");
                    //FirebaseMessaging.getInstance().subscribeToTopic("designing");
                    //FirebaseMessaging.getInstance().subscribeToTopic("klick");
                    //FirebaseMessaging.getInstance().subscribeToTopic("speakers");

                    // [END subscribe_topics]
                    editor.putString("branch","cse") ;
                    editor.commit() ;

               //     startActivity(intent) ;


                }
   */             if(checkedcivil)
                {
                    // [START subscribe_topics]
                    FirebaseMessaging.getInstance().subscribeToTopic("civil");
                    FirebaseMessaging.getInstance().subscribeToTopic("all");

                    // [END subscribe_topics]
                    editor.putString("branch","civil") ;
                    editor.commit() ;
                 //   startActivity(intent) ;


                }
/*                if(checkedece)
                {
                    // [START subscribe_topics]
                    FirebaseMessaging.getInstance().subscribeToTopic("ece");
                    FirebaseMessaging.getInstance().subscribeToTopic("all");

                    // [END subscribe_topics]
                    editor.putString("branch","ece") ;
                    editor.commit() ;
//                    startActivity(intent) ;


                }
*/                if(checkedmech)
                {

                    // [START subscribe_topics]
                    FirebaseMessaging.getInstance().subscribeToTopic("mech");
                    FirebaseMessaging.getInstance().subscribeToTopic("all");

                    // [END subscribe_topics]
                    editor.putString("branch","mech") ;
                    editor.commit() ;
//                    startActivity(intent) ;

                }
          /*      if(checkedeee)
                {

                    // [START subscribe_topics]
                    FirebaseMessaging.getInstance().subscribeToTopic("eee");
                    FirebaseMessaging.getInstance().subscribeToTopic("all");

                    // [END subscribe_topics]
                    editor.putString("branch","eee") ;
                    editor.commit() ;
//                    startActivity(intent) ;


                }*/

                startActivity(intent) ;
                finish() ;


            }
        });


        /*
        Button cseButton = (Button) findViewById(R.id.cseButton);
        cseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // [START subscribe_topics]
                FirebaseMessaging.getInstance().subscribeToTopic("cse");
                // [END subscribe_topics]
                editor.putString("branch","cse") ;
                editor.commit() ;

                startActivity(intent) ;
                finish();
                // Log and toast
                //   String msg = getString(R.string.msg_subscribed);
                //  Log.d(TAG, msg);
                //  Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });



        Button eceButton = (Button) findViewById(R.id.eceButton);
        eceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // [START subscribe_topics]
                FirebaseMessaging.getInstance().subscribeToTopic("ece");
                // [END subscribe_topics]
                editor.putString("branch","ece") ;
                editor.commit() ;
                startActivity(intent) ;
                finish() ;

                // Log and toast
                //   String msg = getString(R.string.msg_subscribed);
                //  Log.d(TAG, msg);
                //  Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

        Button eeeButton = (Button) findViewById(R.id.eeeButton);
        eeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // [START subscribe_topics]
                FirebaseMessaging.getInstance().subscribeToTopic("eee");
                // [END subscribe_topics]
                editor.putString("branch","eee") ;
                editor.commit() ;
                startActivity(intent) ;
                finish() ;

                // Log and toast
                //   String msg = getString(R.string.msg_subscribed);
                //  Log.d(TAG, msg);
                //  Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

        Button mechButton = (Button) findViewById(R.id.mechButton);
        mechButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // [START subscribe_topics]
                FirebaseMessaging.getInstance().subscribeToTopic("mech");
                // [END subscribe_topics]
                editor.putString("branch","mech") ;
                editor.commit() ;
                startActivity(intent) ;
                finish() ;

                // Log and toast
                //   String msg = getString(R.string.msg_subscribed);
                //  Log.d(TAG, msg);
                //  Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });


        Button civilButton = (Button) findViewById(R.id.civilButton);
        civilButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // [START subscribe_topics]
                FirebaseMessaging.getInstance().subscribeToTopic("civil");
                // [END subscribe_topics]
                editor.putString("branch","civil") ;
                editor.commit() ;
                startActivity(intent) ;
                finish() ;

                // Log and toast
                //   String msg = getString(R.string.msg_subscribed);
                //  Log.d(TAG, msg);
                //  Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

*/
        mAuthStateListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser() ;
                if((user != null)) {
                   // Toast.makeText(BranchPreference.this, "Select Your Bra", Toast.LENGTH_SHORT).show();
            //     perform() ;
                }
                else
                {
                    startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setIsSmartLockEnabled(false).setProviders(AuthUI.EMAIL_PROVIDER,AuthUI.GOOGLE_PROVIDER).build(),RC_SIGN_IN);



                }

            }

        } ;


    }
    private void perform()
    {


        FirebaseUser user = mFirebaseAuth.getInstance().getCurrentUser();

        if (user != null)
        {             System.out.println("Email" + user.getEmail());

            SharedPreferences sharedPreferences = getSharedPreferences("activity_selection", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedPreferences.edit();


            String email = sharedPreferences.getString("EMAIL", "default");

            System.out.println("Email 2 "+email) ;
            if (!(email.equals(user.getEmail()))) {
                System.out.println("Not Equal") ;
                editor.putString("EMAIL", user.getEmail());
                editor.commit() ;
                FirebaseMessaging.getInstance().unsubscribeFromTopic("cse");
                FirebaseMessaging.getInstance().unsubscribeFromTopic("ece");
                FirebaseMessaging.getInstance().unsubscribeFromTopic("eee");
                FirebaseMessaging.getInstance().unsubscribeFromTopic("mech");
                FirebaseMessaging.getInstance().unsubscribeFromTopic("civil");

                SharedPreferences.Editor ed = pref.edit();
                ed.putBoolean("activity_executed", false);
                ed.commit();

            } else {
                System.out.println("Equals") ;
                editor.putString("EMAIL", user.getEmail());
                editor.commit() ;

            }
           Intent intent = new Intent(this, BranchPreference.class);
            startActivity(intent);
        }
        else
        {
            System.out.println("Email No") ;
        }

    }

     public void activityselector()
    {
        if(isNetworkAvailable()) {
            if (pref.getBoolean("activity_executed", false)) {

                if(a == 0)
                    intent = new Intent(this, MainActivity.class);
                else
                    intent = new Intent(this, eNotice.class);


                startActivity(intent);
                finish();

            }
            else {
                SharedPreferences.Editor ed = pref.edit();
                ed.putBoolean("activity_executed", true);
                ed.commit();
            }

        }else
        {
            Toast.makeText(BranchPreference.this, "Check your network connection!!!", Toast.LENGTH_SHORT).show();
            finish() ;
        }

    }


    private void initControls() {
        tvNotificationDetails = (TextView) findViewById(R.id.tvNotificationDetails);
    }

    private void setNotificationData(Bundle extras) {
        System.out.println("Inside !!") ;
        System.out.println("Extras"+extras) ;
        if (extras == null)
            return;
        StringBuilder text = new StringBuilder("");
        text.append("Message Details:");
        text.append("\n");
        text.append("\n");
        if (extras.containsKey("all")) {
            Intent intent = new Intent(this, eNotice.class);
            startActivity(intent);
//            text.append("Title: ");
            //          text.append(extras.get("title"));
        }
        text.append("\n");
        if (extras.containsKey("cse")) {
            Intent intent = new Intent(this, CseNotice.class);
            startActivity(intent);

            //        text.append("Message: ");
            //      text.append(extras.get("message"));
        }
        if (extras.containsKey("eee")) {
            Intent intent = new Intent(this, EeeNotice.class);
            startActivity(intent);
        }
        if (extras.containsKey("mec")) {
            Intent intent = new Intent(this, MecNotice.class);
            startActivity(intent);
        }
        if (extras.containsKey("ece")) {
            Intent intent = new Intent(this, EceNotice.class);
            startActivity(intent);
        }
        if (extras.containsKey("civil")) {
            Intent intent = new Intent(this, CivilNotice.class);
            startActivity(intent);
        }
        if (extras.containsKey("academic")) {
            Intent intent = new Intent(this, AcadNotice.class);
            startActivity(intent);
        }
        if (extras.containsKey("cultural")) {
            Intent intent = new Intent(this, CulNotice.class);
            startActivity(intent);
        }
        if (extras.containsKey("adventure")) {
            Intent intent = new Intent(this, AdvenNotice.class);
            startActivity(intent);
        }
        if (extras.containsKey("lostandfound")) {
            Intent intent = new Intent(this, LostandFound.class);
            startActivity(intent);
        }


        //tvNotificationDetails.setText(text);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN) {
            if(resultCode == RESULT_OK) {
                Toast.makeText(this, "Signed in!", Toast.LENGTH_SHORT).show() ;

            }else if(resultCode == RESULT_CANCELED){
                Toast.makeText(this,"Sign in cancelled", Toast.LENGTH_SHORT).show() ;
                finish() ;
            }
        }
    }


    @Override
    protected void onPause() {
        super.onPause() ;
       mFirebaseAuth.removeAuthStateListener(mAuthStateListener);

        // mApp.mGlobalVarValue = globalVarValue;

    }
    @Override
    protected void onResume() {
        super.onResume() ;
        mFirebaseAuth.addAuthStateListener(mAuthStateListener) ;

    }


}
