//Not useful
package in.ac.csa.csae_notice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mayank on 11/6/17.
 */

public class SendNotification extends AppCompatActivity {

    EditText etitle, ebody ;
    Button btn ;
    static String br ;
    private static FirebaseDatabase mFirebaseDatabase;
    private  static DatabaseReference mMessageDatabaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sndnotification);

        Intent intent = getIntent() ;
        String str ;

        br = intent.getStringExtra("branch") ;


        System.out.println("Branch: "+br) ;

        etitle = (EditText) findViewById(R.id.title) ;
        ebody =  (EditText) findViewById(R.id.body) ;

        btn = (Button) findViewById(R.id.sndbtn) ;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String titlestr = etitle.getText().toString() ;
                String bodystr = ebody.getText().toString() ;

                etitle.setText("");
                ebody.setText("");



                sendNotificationToUser(titlestr, bodystr);
                finish() ;
            }
        });



     //   sendNotificationToUser("Mayank", "Hi there Mayank!");



    }


    public static void sendNotificationToUser(String title, final String message) {


        if (br.equals("cse")) {
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mMessageDatabaseReference = mFirebaseDatabase.getReference().child("NitukENotice").child("notificationRequests");


            Map notification = new HashMap<>();
            notification.put("title", title);
            notification.put("message", message);

            mMessageDatabaseReference.push().setValue(notification);

        }
        else if(br.equals("ece"))
        {
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mMessageDatabaseReference = mFirebaseDatabase.getReference().child("NitukENotice").child("notificationRequestsECE");


            Map notification = new HashMap<>();
            notification.put("title", title);
            notification.put("message", message);

            mMessageDatabaseReference.push().setValue(notification);



        }
        else if(br.equals("eee"))
        {
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mMessageDatabaseReference = mFirebaseDatabase.getReference().child("NitukENotice").child("notificationRequestsEEE");


            Map notification = new HashMap<>();
            notification.put("title", title);
            notification.put("message", message);

            mMessageDatabaseReference.push().setValue(notification);



        }
        else if(br.equals("mech"))
        {
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mMessageDatabaseReference = mFirebaseDatabase.getReference().child("NitukENotice").child("notificationRequestsMEC");


            Map notification = new HashMap<>();
            notification.put("title", title);
            notification.put("message", message);

            mMessageDatabaseReference.push().setValue(notification);



        }
        else if(br.equals("civil"))
        {
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mMessageDatabaseReference = mFirebaseDatabase.getReference().child("NitukENotice").child("notificationRequestsCIVIL");


            Map notification = new HashMap<>();
            notification.put("title", title);
            notification.put("message", message);

            mMessageDatabaseReference.push().setValue(notification);



        }

    }
}
