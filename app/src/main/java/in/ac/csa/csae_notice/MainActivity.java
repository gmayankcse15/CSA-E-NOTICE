package in.ac.csa.csae_notice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    boolean isRunning = true;
    private static final String TAG = "MainActivity" ;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthStateListener ;
    private DatabaseReference mMessageDatabaseReference;
    private ChildEventListener mChildEventListener;
    private static ImageView backgroundOne ;
    private static ImageView backgroundTwo ;


    public static final int RC_SIGN_IN = 1 ;
    Handler mHandler = new Handler();
    private TextView tvNotificationDetails;
  //  private SectionsPagerAdapter mSectionsPagerAdapter ;
    private ViewPager mViewPager ;
    private TabLayout tabLayout ;


    SharedPreferences pref ;
    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

      @Override
    protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);

/*
          backgroundOne = (ImageView) findViewById(R.id.text1);
          backgroundTwo = (ImageView) findViewById(R.id.text2);

          final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
          animator.setRepeatCount(ValueAnimator.INFINITE);
          animator.setInterpolator(new LinearInterpolator());
          animator.setDuration(10000L);
          animator.setRepeatMode(ValueAnimator.REVERSE);
          animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
              @Override
              public void onAnimationUpdate(ValueAnimator animation) {
                  final float progress = (float) animation.getAnimatedValue();
                  final float width = backgroundOne.getWidth();
                  final float translationX = width * progress;
                  backgroundOne.setTranslationX(translationX);
                  backgroundTwo.setTranslationX(translationX - width);

              }
          });
          animator.start();
*/

     //     mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

          // Set up the ViewPager with the sections adapter.
      //    mViewPager = (ViewPager) findViewById(R.id.container);
        //  mViewPager.setAdapter(mSectionsPagerAdapter);


       //   tabLayout = (TabLayout) findViewById(R.id.tabDots);
         // tabLayout.setupWithViewPager(mViewPager, true);
        //  setupTabIcons();


          initControls();
          setNotificationData(getIntent().getExtras());

          pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);

          Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
          mFirebaseAuth = FirebaseAuth.getInstance();
          setSupportActionBar(toolbar);

          mFirebaseDatabase = FirebaseDatabase.getInstance();
          mMessageDatabaseReference = mFirebaseDatabase.getReference().child("NitukENotice").child("AuthUsers").child("CSE");

          setSupportActionBar(toolbar);
          new Thread(new Runnable() {
              @Override
              public void run() {
                  // TODO Auto-generated method stub
                  while (isRunning) {
                      try {
                          Thread.sleep(10000);
                          mHandler.post(new Runnable() {
                              @Override
                              public void run() {
                                  if (!isNetworkAvailable()) {
                                      Toast.makeText(MainActivity.this, "Check your network connection!!!", Toast.LENGTH_SHORT).show();
                                  }
                              }
                          });
                      } catch (Exception e) {
                      }
                  }
              }
          }).start();

          final Intent intent1 = new Intent(this, eNotice.class);

          final Intent intent2  = new Intent(this, register.class);
          FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
          fab.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                  startActivity(intent1);

                  // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                  //       .setAction("Action", null).show();
              }
          });

          FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
          fab2.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                  startActivity(intent2);

                  // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                  //       .setAction("Action", null).show();
              }
          });


          DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
          ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                  this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
          drawer.setDrawerListener(toggle);
          toggle.syncState();

          NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
          navigationView.setNavigationItemSelectedListener(this);


          mAuthStateListener = new FirebaseAuth.AuthStateListener() {
              @Override
              public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                  FirebaseUser user = firebaseAuth.getCurrentUser();
                  if ((user != null)) {
                      Toast.makeText(MainActivity.this, "Welcome to NITUK APP", Toast.LENGTH_SHORT).show();
                     // perform() ;
                      System.out.println("EMAIL " + user.getEmail());
                  } else {
                      startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setIsSmartLockEnabled(false).setProviders(AuthUI.EMAIL_PROVIDER, AuthUI.GOOGLE_PROVIDER).build(), RC_SIGN_IN);


                  }

              }

          };

          perform() ;







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
              FirebaseMessaging.getInstance().unsubscribeFromTopic("academic");
              FirebaseMessaging.getInstance().unsubscribeFromTopic("adventure");
              FirebaseMessaging.getInstance().unsubscribeFromTopic("lostandfound");
              FirebaseMessaging.getInstance().unsubscribeFromTopic("cultural");


              SharedPreferences.Editor ed = pref.edit();
              ed.putBoolean("activity_executed", false);
              ed.commit();
              Intent intent = new Intent(this, BranchPreference.class);
              startActivity(intent);
              finish() ;

          } else {
              System.out.println("Equals") ;
              editor.putString("EMAIL", user.getEmail());
              editor.commit() ;
          }


      }
      else
      {
          System.out.println("Email No") ;
      }

  }

    private void initControls() {
        tvNotificationDetails = (TextView) findViewById(R.id.tvNotificationDetails);
    }

    private void setNotificationData(Bundle extras) {
        System.out.println("Inside !!") ;
        Log.d("MainActivity","Inside setNotificationData") ;
        System.out.println("Extras"+extras) ;
        Log.d("MainActivity","Inside setNotificationData Extras"+extras) ;
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
        if (extras.containsKey("academic")){
            Intent intent = new Intent(this, AcadNotice.class);
            startActivity(intent);

        }
        if (extras.containsKey("adventure")){
            Intent intent = new Intent(this, AdvenNotice.class);
            startActivity(intent);
        }
        if (extras.containsKey("cultural")){
            Intent intent = new Intent(this, CulNotice.class);
            startActivity(intent);
        }
        if (extras.containsKey("lostandfound")){
            Intent intent = new Intent(this, LostandFound.class);
            startActivity(intent);

        }
            //tvNotificationDetails.setText(text);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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



           Intent intent = new Intent(this, GoogleSignInActivity.class) ;
            startActivity(intent);

            finish() ;



        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN) {
            if(resultCode == RESULT_OK) {
           //     Toast.makeText(this, "Signed in!", Toast.LENGTH_SHORT).show() ;

            }else if(resultCode == RESULT_CANCELED){
              //  Toast.makeText(this,"Sign in cancelled", Toast.LENGTH_SHORT).show() ;
                finish() ;
            }
        }
    }


   @Override
    protected void onPause() {
        super.onPause() ;
       // mFirebaseAuth.removeAuthStateListener(mAuthStateListener);

        // mApp.mGlobalVarValue = globalVarValue;

    }


    @Override
    protected void onResume() {
        super.onResume() ;
     //   mFirebaseAuth.addAuthStateListener(mAuthStateListener) ;

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_civil) {
            Intent intent = new Intent(this, CivilNotice.class);
            startActivity(intent) ;

            // Handle the camera action
        }/* else if (id == R.id.nav_computer) {

            Intent intent = new Intent(this, CseNotice.class);
           startActivity(intent) ;

        } else if (id == R.id.nav_electrical) {
            Intent intent = new Intent(this, EeeNotice.class);
            startActivity(intent) ;

        } else if (id == R.id.nav_electronics) {
            Intent intent = new Intent(this, EceNotice.class);
            startActivity(intent) ;

        }*/
        else if(id == R.id.nav_mechanical)
        {
            Intent intent = new Intent(this, MecNotice.class);
            startActivity(intent) ;

        }
        else if(id == R.id.nav_cultural){
            Intent intent = new Intent(this, CulNotice.class);
            startActivity(intent);
        }
        else if(id == R.id.nav_Academic){
            Intent intent = new Intent(this, AcadNotice.class);
            startActivity(intent);

        }
        else if(id == R.id.nav_adventure){
            Intent intent = new Intent(this, AdvenNotice.class);
            startActivity(intent);
        }
        else if(id == R.id.nav_lostandfound){
            Intent intent = new Intent(this, LostandFound.class);
            startActivity(intent);
        }
        else if(id == R.id.nav_notes){
            Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.drawer_layout), "Notes Section Soon to be added", Snackbar.LENGTH_LONG);

            snackbar.show();
        }
        else if (id == R.id.nav_send) {

            Intent intent = new Intent(this, AuthBranchPreference.class) ;
            startActivity(intent);






        } else if (id == R.id.nav_share) {

            Intent i = new Intent(this, contact.class) ;
            startActivity(i) ;


        }
        else if (id == R.id.nav_develop) {

             Intent i = new Intent(this, developer.class) ;
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


/*
    public void authenticated()
    {

        Intent intent = new Intent(this, SendNotification.class) ;
        startActivity(intent);

    }
*/

/*
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return PlaceholderFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "SECTION 1";
            case 1:
                return "SECTION 2";
            case 2:
                return "SECTION 3";
        }
        return null;
    }
}

   */
 /*
       public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);

          //  ImageView imgView = (ImageView) rootView.findViewById(R.id.section_label) ;


            if(getArguments().getInt(ARG_SECTION_NUMBER) == 1)
            {
//                backgroundOne = (ImageView) rootView.findViewById(R.id.text1);
  //              backgroundTwo = (ImageView) rootView.findViewById(R.id.text2);



            }

            else if(getArguments().getInt(ARG_SECTION_NUMBER) == 2)
            {

              //  imgView.setImageResource(R.drawable.networka);

            }
           else if(getArguments().getInt(ARG_SECTION_NUMBER) == 3)
            {

              //  imgView.setImageResource(R.drawable.networka);

            }

            return rootView;
        }
    }


    private void setupTabIcons() {
        tabLayout.getTabAt(0).setText("");
        tabLayout.getTabAt(1).setText("");
        tabLayout.getTabAt(2).setText("");
    }

*/

}
