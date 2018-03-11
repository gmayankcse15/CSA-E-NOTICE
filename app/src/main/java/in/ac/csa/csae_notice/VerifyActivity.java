package in.ac.csa.csae_notice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VerifyActivity extends AppCompatActivity {
    private int a = 1 ;

    private FirebaseAuth mAuth ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifyemail);

        mAuth = FirebaseAuth.getInstance();
    }


  public void sendVerification(View view) {
        // Disable button
//        findViewById(R.id.verify_email_button).setEnabled(false);

        // Send verification email
        // [START send_email_verification]
      Log.d("VerifyActivity", "sendEmailVerification");

        final FirebaseUser user = mAuth.getCurrentUser();
      Log.d("VerifyActivity", "sendEmailVerification"+user.getEmail());


      user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        // Re-enable button
                  //      findViewById(R.id.verify_email_button).setEnabled(true);

                        Log.d("VerifyActivity", "sendEmailVerificationInside");
                        if (task.isSuccessful()) {
                            a = 0 ;
                            Toast.makeText(VerifyActivity.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("VerifyActivity", "sendEmailVerification", task.getException());
                            Toast.makeText(VerifyActivity.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]
    }

   public void SignIn(View view)
    {
        final FirebaseUser user = mAuth.getCurrentUser();

       if(a == 0) {
           mAuth.signOut();
           Intent i = new Intent(this, GoogleSignInActivity.class);
           startActivity(i);
       }
       else
       {
           mAuth.signOut();

           Toast.makeText(VerifyActivity.this,
                   "You are not Verified",
                   Toast.LENGTH_SHORT).show();
           Intent i = new Intent(this, GoogleSignInActivity.class);
           startActivity(i);

       }

        /*
        if(user.isEmailVerified()) {
            Log.d("VerifyActivity", "Verified:");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish() ;

    }
    else {

            Log.d("VerifyActivity", " Not Verified:");

        }
  */

}

}
