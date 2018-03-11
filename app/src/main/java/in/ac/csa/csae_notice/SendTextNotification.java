package in.ac.csa.csae_notice;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mayank on 24/6/17.
 */

public class SendTextNotification extends AppCompatActivity {


    private FirebaseStorage mFirebaseStorage;
    private StorageReference mNoticePhotosStorageReference;
    private Button photopickerbtn;
    private static DatabaseReference mMessageDatabaseReference1, mMessageDatabaseReference2;
    private static FirebaseDatabase mFirebaseDatabase1, mFirebaseDatabase2;
    EditText etitle, ebody;
    Button btn;
    static String br;
    private static FirebaseDatabase mFirebaseDatabase;
    private static DatabaseReference mMessageDatabaseReference;
    private static String kcse, kece, keee, kmec, kcivil, kgen, kacad, kadven, kcult, klost;
    private static int KCSE, KECE, KEEE, KMEC, KCIVIL, KGEN, KACAD, KADVEN, KCULT, KLOST;
    ProgressBar progressBar;
    static String imgurl ;
    private static int a = 0 ;

    static private ChildEventListener mChildEventListener;


    //static Map notif = new HashMap<>();

    private static final int RC_PHOTO_PICKER = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendtext);


        progressBar = (ProgressBar) findViewById(R.id.progressBar4);
        progressBar.setVisibility(View.INVISIBLE);
        Intent intent = getIntent();
        String str;

        br = intent.getStringExtra("branch");


        System.out.println("Branch: " + br);

        Log.d("SendTextNotification", "Branch:" + br);

        etitle = (EditText) findViewById(R.id.title);
        ebody = (EditText) findViewById(R.id.body);

        btn = (Button) findViewById(R.id.sndbtn);
        btn.setVisibility(View.INVISIBLE);
        //btn.setClickable(false);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String titlestr = etitle.getText().toString();
                String bodystr = ebody.getText().toString();

                etitle.setText("");
                ebody.setText("");


                sendNotificationToUser(titlestr, bodystr);
                finish();
            }
        });


        mFirebaseStorage = FirebaseStorage.getInstance();
        mFirebaseDatabase1 = FirebaseDatabase.getInstance();


        if(br.equals("gen"))
            mNoticePhotosStorageReference=mFirebaseStorage.getReference().child("GENNoticePics");
        else if(br.equals("cse"))
        mNoticePhotosStorageReference = mFirebaseStorage.getReference().child("CSENoticePics");
        else if(br.equals("ece"))
            mNoticePhotosStorageReference = mFirebaseStorage.getReference().child("ECENoticePics");

        else if(br.equals("eee"))
            mNoticePhotosStorageReference = mFirebaseStorage.getReference().child("EEENoticePics");

        else if(br.equals("mec"))
            mNoticePhotosStorageReference = mFirebaseStorage.getReference().child("MECNoticePics");

        else if(br.equals("civil"))
            mNoticePhotosStorageReference = mFirebaseStorage.getReference().child("CIVILNoticePics");

        else if(br.equals("academic"))
            mNoticePhotosStorageReference = mFirebaseStorage.getReference().child("ACADNoticePics");

        else if(br.equals("adventure"))
            mNoticePhotosStorageReference = mFirebaseStorage.getReference().child("ADVENNoticePics");

        else if(br.equals("cultural"))
            mNoticePhotosStorageReference = mFirebaseStorage.getReference().child("CULTNoticePics");

        else if(br.equals("lost"))
            mNoticePhotosStorageReference = mFirebaseStorage.getReference().child("LOSTNoticePics");


        photopickerbtn = (Button) findViewById(R.id.upload);

        photopickerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);

            }


        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            StorageReference photoRef =
                    mNoticePhotosStorageReference.child(selectedImageUri.getLastPathSegment());


            progressBar.setVisibility(View.VISIBLE);
            photoRef.putFile(selectedImageUri).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {

                    System.out.println("Failed...");
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    // Log.d("Downloading... ", "Download") ;
                    // Set the download URL to the message box, so that the user can send it to the database


                    imgurl = downloadUrl.toString() ;
                    //a = 1 ;
                    progressBar.setVisibility(View.INVISIBLE);
                   btn.setVisibility(View.VISIBLE);

                   // btn.setClickable(true);
                    //   mMessageDatabaseReference1 = mFirebaseDatabase1.getReference().child("NitukENotice").child("Images");


//                    mMessageDatabaseReference1.push().setValue(notif) ;


                }
            });


        }


    }

    public static void sendNotificationToUser(final String title, final String message) {

        if(br.equals("gen")){

            Log.d("SendTextNotification", "Branch GEN");
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mMessageDatabaseReference = mFirebaseDatabase.getReference().child("NitukENotice").child("PrimeKeys").child("GEN");
            mMessageDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    kgen = dataSnapshot.getValue().toString();
                    Log.d("SendTextNotification", "Value of Key" + kgen);
                    KGEN = Integer.parseInt(kgen);
                    ++KGEN;
                    mMessageDatabaseReference.setValue(""+KGEN);
                    KGEN = 0;

                    Gen_Message(title, message) ;


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            Log.d("SendTextNotification", "Value of Key!" + kgen);


        }
       else if (br.equals("cse")) {


            Log.d("SendTextNotification", "Branch CSE");
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mMessageDatabaseReference = mFirebaseDatabase.getReference().child("NitukENotice").child("PrimeKeys").child("CSE");
            mMessageDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    kcse = dataSnapshot.getValue().toString();
                    Log.d("SendTextNotification", "Value of Key" + kcse);
                    KCSE = Integer.parseInt(kcse);
                    ++KCSE;
                    mMessageDatabaseReference.setValue(""+KCSE);
                    KCSE = 0;

                    Cse_Message(title, message) ;


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            Log.d("SendTextNotification", "Value of Key!" + kcse);


        } else if (br.equals("ece")) {


            Log.d("SendTextNotification", "Branch ECE");
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mMessageDatabaseReference = mFirebaseDatabase.getReference().child("NitukENotice").child("PrimeKeys").child("ECE");
            mMessageDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    kece = dataSnapshot.getValue().toString();
                    Log.d("SendTextNotification", "Value of Key" + kece);
                    KECE = Integer.parseInt(kece);
                    ++KECE;
                    mMessageDatabaseReference.setValue(""+KECE);
                    KECE = 0;

                  Ece_Message(title, message) ;


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            Log.d("SendTextNotification", "Value of Key!" + kece);




        }
        else if(br.equals("eee"))
        {
            Log.d("SendTextNotification", "Branch EEE");
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mMessageDatabaseReference = mFirebaseDatabase.getReference().child("NitukENotice").child("PrimeKeys").child("EEE");
            mMessageDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    keee = dataSnapshot.getValue().toString();
                    Log.d("SendTextNotification", "Value of Key" + keee);
                    KEEE = Integer.parseInt(keee);
                    ++KEEE;
                    mMessageDatabaseReference.setValue(""+KEEE);
                    KEEE = 0;

                    Eee_Message(title, message) ;


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            Log.d("SendTextNotification", "Value of Key!" + keee);



        }
        else if(br.equals("mec"))
        {
            Log.d("SendTextNotification", "Branch MEC");
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mMessageDatabaseReference = mFirebaseDatabase.getReference().child("NitukENotice").child("PrimeKeys").child("MEC");
            mMessageDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    kmec = dataSnapshot.getValue().toString();
                    Log.d("SendTextNotification", "Value of Key" + kmec);
                    KMEC = Integer.parseInt(kmec);
                    ++KMEC;
                    mMessageDatabaseReference.setValue(""+KMEC);
                    KMEC = 0;

                    Mec_Message(title, message) ;


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            Log.d("SendTextNotification", "Value of Key!" + kmec);



        }
        else if(br.equals("civil"))
        {
            Log.d("SendTextNotification", "Branch CIVIL");
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mMessageDatabaseReference = mFirebaseDatabase.getReference().child("NitukENotice").child("PrimeKeys").child("CIVIL");
            mMessageDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    kcivil = dataSnapshot.getValue().toString();
                    Log.d("SendTextNotification", "Value of Key" + kcivil);
                    KCIVIL = Integer.parseInt(kcivil);
                    ++KCIVIL;
                    mMessageDatabaseReference.setValue(""+KCIVIL);
                    KCIVIL = 0;

                    Civil_Message(title, message) ;


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            Log.d("SendTextNotification", "Value of Key!" + kcivil);



        }
        else if(br.equals("academic"))
        {
            Log.d("SendTextNotification", "Branch ACADEMIC");
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mMessageDatabaseReference = mFirebaseDatabase.getReference().child("NitukENotice").child("PrimeKeys").child("ACAD");
            mMessageDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    kacad = dataSnapshot.getValue().toString();
                    Log.d("SendTextNotification", "Value of Key" + kacad);
                    KACAD = Integer.parseInt(kacad);
                    ++KACAD;
                    mMessageDatabaseReference.setValue(""+KACAD);
                    KACAD = 0;

                    Acad_Message(title, message) ;


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            Log.d("SendTextNotification", "Value of Key!" + kacad);



        }
        else if(br.equals("adventure"))
        {
            Log.d("SendTextNotification", "Branch ADVENTURE");
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mMessageDatabaseReference = mFirebaseDatabase.getReference().child("NitukENotice").child("PrimeKeys").child("ADVEN");
            mMessageDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    kadven = dataSnapshot.getValue().toString();
                    Log.d("SendTextNotification", "Value of Key" + kadven);
                    KADVEN = Integer.parseInt(kadven);
                    ++KADVEN;
                    mMessageDatabaseReference.setValue(""+KADVEN);
                    KADVEN = 0;

                    Adven_Message(title, message) ;


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            Log.d("SendTextNotification", "Value of Key!" + kadven);



        }
        else if(br.equals("cultural"))
        {
            Log.d("SendTextNotification", "Branch CULTURAL");
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mMessageDatabaseReference = mFirebaseDatabase.getReference().child("NitukENotice").child("PrimeKeys").child("CULT");
            mMessageDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    kcult = dataSnapshot.getValue().toString();
                    Log.d("SendTextNotification", "Value of Key" + kcult);
                    KCULT = Integer.parseInt(kcult);
                    ++KCULT ;
                    mMessageDatabaseReference.setValue(""+KCULT);
                    KCULT = 0;

                    Cult_Message(title, message) ;


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            Log.d("SendTextNotification", "Value of Key!" + kcult);



        }
        else if(br.equals("lost"))
        {
            Log.d("SendTextNotification", "Branch LOST");
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mMessageDatabaseReference = mFirebaseDatabase.getReference().child("NitukENotice").child("PrimeKeys").child("LOST");
            mMessageDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    klost = dataSnapshot.getValue().toString();
                    Log.d("SendTextNotification", "Value of Key" + klost);
                    KLOST = Integer.parseInt(klost);
                    ++KLOST;
                    mMessageDatabaseReference.setValue(""+KLOST);
                    KLOST = 0;

                    Lost_Message(title, message) ;


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            Log.d("SendTextNotification", "Value of Key!" + klost);



        }


        }
    public static void Gen_Message(String title, String message) {


        mMessageDatabaseReference1 = mFirebaseDatabase1.getReference().child("NitukENotice").child("College");

        mMessageDatabaseReference1.child("ENotice").child(kgen).child("text").setValue(message);

        mMessageDatabaseReference2 = mFirebaseDatabase1.getReference().child("NitukENotice").child("College");

        Log.d("SendTextNotification", "Notification" + imgurl);

        mMessageDatabaseReference2.child("Image").child(kgen).child("url").setValue(imgurl);


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessageDatabaseReference = mFirebaseDatabase.getReference().child("NitukENotice").child("notificationRequestsGEN");


        Map notification = new HashMap<>();
        notification.put("title", title);
        notification.put("message", message);

        mMessageDatabaseReference.push().setValue(notification);


    }

        public static void Ece_Message(String title, String message) {


            mMessageDatabaseReference1 = mFirebaseDatabase1.getReference().child("NitukENotice").child("ECE");

            mMessageDatabaseReference1.child("ECENotice").child(kece).child("text").setValue(message);

            mMessageDatabaseReference2 = mFirebaseDatabase1.getReference().child("NitukENotice").child("ECE");

            Log.d("SendTextNotification", "Notification" + imgurl);

            mMessageDatabaseReference2.child("Image").child(kece).child("url").setValue(imgurl);


            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mMessageDatabaseReference = mFirebaseDatabase.getReference().child("NitukENotice").child("notificationRequestsECE");


            Map notification = new HashMap<>();
            notification.put("title", title);
            notification.put("message", message);

            mMessageDatabaseReference.push().setValue(notification);


        }
    public static void Cse_Message(String title, String message) {


        mMessageDatabaseReference1 = mFirebaseDatabase1.getReference().child("NitukENotice").child("CSE");

        mMessageDatabaseReference1.child("CSENotice").child(kcse).child("text").setValue(message);

        mMessageDatabaseReference2 = mFirebaseDatabase1.getReference().child("NitukENotice").child("CSE");

        Log.d("SendTextNotification", "Notification" + imgurl);

        mMessageDatabaseReference2.child("Image").child(kcse).child("url").setValue(imgurl);


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessageDatabaseReference = mFirebaseDatabase.getReference().child("NitukENotice").child("notificationRequestsCSE");


        Map notification = new HashMap<>();
        notification.put("title", title);
        notification.put("message", message);

        mMessageDatabaseReference.push().setValue(notification);


    }
    public static void Eee_Message(String title, String message) {


        mMessageDatabaseReference1 = mFirebaseDatabase1.getReference().child("NitukENotice").child("EEE");

        mMessageDatabaseReference1.child("EEENotice").child(keee).child("text").setValue(message);

        mMessageDatabaseReference2 = mFirebaseDatabase1.getReference().child("NitukENotice").child("EEE");

        Log.d("SendTextNotification", "Notification" + imgurl);

        mMessageDatabaseReference2.child("Image").child(keee).child("url").setValue(imgurl);


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessageDatabaseReference = mFirebaseDatabase.getReference().child("NitukENotice").child("notificationRequestsEEE");


        Map notification = new HashMap<>();
        notification.put("title", title);
        notification.put("message", message);

        mMessageDatabaseReference.push().setValue(notification);


    }
    public static void Mec_Message(String title, String message) {


        mMessageDatabaseReference1 = mFirebaseDatabase1.getReference().child("NitukENotice").child("MEC");

        mMessageDatabaseReference1.child("MECNotice").child(kmec).child("text").setValue(message);

        mMessageDatabaseReference2 = mFirebaseDatabase1.getReference().child("NitukENotice").child("MEC");

        Log.d("SendTextNotification", "Notification" + imgurl);

        mMessageDatabaseReference2.child("Image").child(kmec).child("url").setValue(imgurl);


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessageDatabaseReference = mFirebaseDatabase.getReference().child("NitukENotice").child("notificationRequestsMEC");


        Map notification = new HashMap<>();
        notification.put("title", title);
        notification.put("message", message);

        mMessageDatabaseReference.push().setValue(notification);


    }
    public static void Civil_Message(String title, String message) {


        mMessageDatabaseReference1 = mFirebaseDatabase1.getReference().child("NitukENotice").child("CIVIL");

        mMessageDatabaseReference1.child("CIVILNotice").child(kcivil).child("text").setValue(message);

        mMessageDatabaseReference2 = mFirebaseDatabase1.getReference().child("NitukENotice").child("CIVIL");

        Log.d("SendTextNotification", "Notification" + imgurl);

        mMessageDatabaseReference2.child("Image").child(kcivil).child("url").setValue(imgurl);


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessageDatabaseReference = mFirebaseDatabase.getReference().child("NitukENotice").child("notificationRequestsCIVIL");


        Map notification = new HashMap<>();
        notification.put("title", title);
        notification.put("message", message);

        mMessageDatabaseReference.push().setValue(notification);


    }
    public static void Acad_Message(String title, String message) {


        mMessageDatabaseReference1 = mFirebaseDatabase1.getReference().child("NitukENotice").child("ACADEMIC");

        mMessageDatabaseReference1.child("ACADNotice").child(kacad).child("text").setValue(message);

        mMessageDatabaseReference2 = mFirebaseDatabase1.getReference().child("NitukENotice").child("ACADEMIC");

        Log.d("SendTextNotification", "Notification" + imgurl);

        mMessageDatabaseReference2.child("Image").child(kacad).child("url").setValue(imgurl);


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessageDatabaseReference = mFirebaseDatabase.getReference().child("NitukENotice").child("notificationRequestsACAD");


        Map notification = new HashMap<>();
        notification.put("title", title);
        notification.put("message", message);

        mMessageDatabaseReference.push().setValue(notification);


    }
    public static void Adven_Message(String title, String message) {


        mMessageDatabaseReference1 = mFirebaseDatabase1.getReference().child("NitukENotice").child("ADVENTURE");

        mMessageDatabaseReference1.child("ADVENotice").child(kadven).child("text").setValue(message);

        mMessageDatabaseReference2 = mFirebaseDatabase1.getReference().child("NitukENotice").child("ADVENTURE");

        Log.d("SendTextNotification", "Notification" + imgurl);

        mMessageDatabaseReference2.child("Image").child(kadven).child("url").setValue(imgurl);


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessageDatabaseReference = mFirebaseDatabase.getReference().child("NitukENotice").child("notificationRequestsADVEN");


        Map notification = new HashMap<>();
        notification.put("title", title);
        notification.put("message", message);

        mMessageDatabaseReference.push().setValue(notification);


    }
    public static void Cult_Message(String title, String message) {


        mMessageDatabaseReference1 = mFirebaseDatabase1.getReference().child("NitukENotice").child("CULTURAL");

        mMessageDatabaseReference1.child("CULTNotice").child(kcult).child("text").setValue(message);

        mMessageDatabaseReference2 = mFirebaseDatabase1.getReference().child("NitukENotice").child("CULTURAL");

        Log.d("SendTextNotification", "Notification" + imgurl);

        mMessageDatabaseReference2.child("Image").child(kcult).child("url").setValue(imgurl);


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessageDatabaseReference = mFirebaseDatabase.getReference().child("NitukENotice").child("notificationRequestsCULT");


        Map notification = new HashMap<>();
        notification.put("title", title);
        notification.put("message", message);

        mMessageDatabaseReference.push().setValue(notification);


    }
    public static void Lost_Message(String title, String message) {


        mMessageDatabaseReference1 = mFirebaseDatabase1.getReference().child("NitukENotice").child("LostandFound");

        mMessageDatabaseReference1.child("LOSTNotice").child(klost).child("text").setValue(message);

        mMessageDatabaseReference2 = mFirebaseDatabase1.getReference().child("NitukENotice").child("LostandFound");

        Log.d("SendTextNotification", "Notification" + imgurl);

        mMessageDatabaseReference2.child("Image").child(klost).child("url").setValue(imgurl);


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessageDatabaseReference = mFirebaseDatabase.getReference().child("NitukENotice").child("notificationRequestsLOST");


        Map notification = new HashMap<>();
        notification.put("title", title);
        notification.put("message", message);

        mMessageDatabaseReference.push().setValue(notification);


    }

}



