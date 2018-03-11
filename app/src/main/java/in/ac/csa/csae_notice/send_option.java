
//Not useful
package in.ac.csa.csae_notice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by mayank on 23/6/17.
 */

public class send_option extends AppCompatActivity

{

    Button btn,  btn1;
    static Intent inten, intent , intent1;
    String br ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendoption);

        inten = getIntent() ;

        br = inten.getStringExtra("branch") ;



        btn = (Button) findViewById(R.id.notonly) ;
        intent = new Intent(this, SendNotification.class) ;
        intent.putExtra("branch",br) ;
        intent1 = new Intent(this, SendTextNotification.class) ;
        intent1.putExtra("branch",br) ;
        btn1 = (Button) findViewById(R.id.txtnot) ;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                startActivity(intent);



                finish() ;
            }
        });


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                startActivity(intent1);



                finish() ;
            }
        });





    }


}