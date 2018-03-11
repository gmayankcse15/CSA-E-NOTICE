package in.ac.csa.csae_notice;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import in.ac.csa.csae_notice.Academic.AcadNotice;
import in.ac.csa.csae_notice.Adventure.AdvenNotice;
import in.ac.csa.csae_notice.Civil.CivilNotice;
import in.ac.csa.csae_notice.Cse.CseNotice;
import in.ac.csa.csae_notice.Cultural.CulNotice;
import in.ac.csa.csae_notice.Ece.EceNotice;
import in.ac.csa.csae_notice.Eee.EeeNotice;
import in.ac.csa.csae_notice.LostFound.LostandFound;
import in.ac.csa.csae_notice.Mech.MecNotice;


public class GoogleSignInActivity extends BaseActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
    Bundle extras ;
    private EditText mEmailField;
    private EditText mPasswordField;

    private GoogleApiClient mGoogleApiClient;
  //  private TextView mStatusTextView;
  //  private TextView mDetailTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google);


        mEmailField = (EditText) findViewById(R.id.field_email);
        mPasswordField = (EditText) findViewById(R.id.field_password);

        // Buttons
        findViewById(R.id.email_sign_in_button).setOnClickListener(this);
        findViewById(R.id.email_create_account_button).setOnClickListener(this);


        //  setNotificationData(getIntent().getExtras());


        extras = getIntent().getExtras() ;
        Log.d("GoogleSignInActivity","Inside Google!!!"+extras) ;


        findViewById(R.id.sign_in_button).setOnClickListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mAuth = FirebaseAuth.getInstance();
        }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());

                            String s = task.getException()+"" ;
                            String s1[] = s.split("\n") ;
                            String s2[] = s1[0].split(":") ;
                            System.out.println(s2[1]) ;

                            Toast.makeText(GoogleSignInActivity.this, "Authentication failed.\n"+s2[1],
                                    Toast.LENGTH_SHORT).show();

                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(GoogleSignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                          }
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }



    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    //    System.out.println("Successful1") ;
       if (requestCode == RC_SIGN_IN) {
      //     System.out.println("Successful2") ;
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.d(TAG, "handleSignInResult:"+ result.isSuccess()) ;

          if(result.isSuccess()){

              System.out.println("Successful3") ;
              GoogleSignInAccount account = result.getSignInAccount();
              firebaseAuthWithGoogle(account);

          }

           else
               {
                   Log.d(TAG, "handleSignInResult:"+ result.isSuccess()) ;
                   updateUI(null);
               }
           }
        }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        showProgressDialog();


        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(GoogleSignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();


                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_google]

    // [START signin]
    private void signIn() {
        System.out.println("Activity Started") ;
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

     }
    // [END signin]

    public void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        updateUI(null);
                    }
                });
    }


    private void updateUI(FirebaseUser user){

        if(user != null) {

            if (user.isEmailVerified()) {

                Log.d(TAG, "Verified:");
                //Intent intent = new Intent(this, MainActivity.class);
                //startActivity(intent);
                //finish();


                if (extras != null) {

                    if (extras.containsKey("all")) {
                        Intent intent = new Intent(this, eNotice.class);
                        startActivity(intent);
                        finish();
                    } else if (extras.containsKey("cse")) {
                        Intent intent = new Intent(this, CseNotice.class);
                        startActivity(intent);
                        finish();

                        //        text.append("Message: ");
                        //      text.append(extras.get("message"));
                    } else if (extras.containsKey("eee")) {
                        Intent intent = new Intent(this, EeeNotice.class);
                        startActivity(intent);
                        finish();
                    } else if (extras.containsKey("mec")) {
                        Intent intent = new Intent(this, MecNotice.class);
                        startActivity(intent);
                        finish();
                    } else if (extras.containsKey("ece")) {
                        Log.d("GoogleSignInActivity", "Inside ECE");
                        Intent intent = new Intent(this, EceNotice.class);
                        startActivity(intent);
                        finish();
                    } else if (extras.containsKey("civil")) {
                        Intent intent = new Intent(this, CivilNotice.class);
                        startActivity(intent);
                        finish();
                    } else if(extras.containsKey("academic")){
                        Intent intent = new Intent(this, AcadNotice.class);
                        startActivity(intent);
                        finish();

                    } else if(extras.containsKey("adventure")) {
                        Intent intent = new Intent(this, AdvenNotice.class);
                        startActivity(intent);
                        finish();
                    } else if(extras.containsKey("cultural")){
                        Intent intent = new Intent(this, CulNotice.class);
                        startActivity(intent);
                        finish() ;
                    } else if(extras.containsKey("lost")){
                        Intent intent = new Intent(this, LostandFound.class);
                        startActivity(intent);
                        finish();
                    }
                    else if (extras.containsKey("branch")) {

                        if ((extras.get("branch").equals("cse"))) {
                            Intent intent = new Intent(this, CseNotice.class);
                            startActivity(intent);
                            finish();

                            //        text.append("Message: ");
                            //      text.append(extras.get("message"));
                        } else if ((extras.get("branch").equals("eee"))) {
                            Intent intent = new Intent(this, EeeNotice.class);
                            startActivity(intent);
                            finish();
                        } else if ((extras.get("branch").equals("mech"))) {
                            Intent intent = new Intent(this, MecNotice.class);
                            startActivity(intent);
                            finish();
                        } else if ((extras.get("branch").equals("ece"))) {
                            Log.d("GoogleSignInActivity", "Inside ECE");
                            Intent intent = new Intent(this, EceNotice.class);
                            startActivity(intent);
                            finish();
                        } else if ((extras.get("branch").equals("civil"))) {
                            Intent intent = new Intent(this, CivilNotice.class);
                            startActivity(intent);
                            finish();
                        } else if(extras.get("branch").equals("academic")){
                            Intent intent = new Intent(this, AcadNotice.class);
                            startActivity(intent);
                            finish();

                        } else if(extras.get("branch").equals("adventure")) {
                            Intent intent = new Intent(this, AdvenNotice.class);
                            startActivity(intent);
                            finish();
                        } else if(extras.get("branch").equals("cultural")){
                            Intent intent = new Intent(this, CulNotice.class);
                            startActivity(intent);
                            finish() ;
                        } else if(extras.get("branch").equals("lost")){
                            Intent intent = new Intent(this, LostandFound.class);
                            startActivity(intent);
                            finish();
                        }


                    } else {
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    }


                } else {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);

                    finish();
                }
            } else {

                Intent i = new Intent(this, VerifyActivity.class);
                startActivity(i);
                finish();

            }
        }
        else
            {

            }



    }



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.email_create_account_button) {
            createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
        } else if (i == R.id.email_sign_in_button) {
            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
        }
        if(i == R.id.sign_in_button){
            System.out.println("Google get clicked") ;
            signIn();
        }
    }
}



