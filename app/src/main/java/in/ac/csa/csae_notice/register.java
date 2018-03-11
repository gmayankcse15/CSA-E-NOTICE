package in.ac.csa.csae_notice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mgupta on 2/10/2018.
 */

public class register  extends AppCompatActivity {

    FirebaseDatabase mfirebaseDatabase ;
    DatabaseReference mMessageReference ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mfirebaseDatabase = FirebaseDatabase.getInstance() ;
        mMessageReference = mfirebaseDatabase.getReference().child("NitukENotice").child("Register") ;

        final EditText name = (EditText) findViewById(R.id.register_name) ;
        final EditText id = (EditText) findViewById(R.id.register_id) ;
        final EditText roll = (EditText) findViewById(R.id.register_roll) ;
        Button reg_button = (Button) findViewById(R.id.register_button) ;

        final Intent intent = new Intent(this, MainActivity.class ) ;

        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reg_name = name.getText().toString();
                String reg_id = id.getText().toString() ;
                String reg_roll = roll.getText().toString() ;
                SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd hh:mm") ;
                Date date = new Date() ;
                String dat = date_format.format(date) ;


                Map notification = new HashMap<>();
                notification.put("Name", reg_name);
                notification.put("Roll", reg_roll);
                notification.put("Id", reg_id) ;
                notification.put("Date", dat) ;

                mMessageReference.push().setValue(notification);
                Toast.makeText(register.this, "Successfully Registered" , Toast.LENGTH_SHORT).show();

                startActivity(intent);


            }
        });



    }

}
