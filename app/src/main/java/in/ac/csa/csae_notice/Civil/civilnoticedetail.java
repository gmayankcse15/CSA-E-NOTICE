package in.ac.csa.csae_notice.Civil;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import in.ac.csa.csae_notice.R;

/**
 * Created by mayank on 21/5/17.
 */

public class civilnoticedetail extends AppCompatActivity {
    TextView textView;
    int position;
    boolean isclick = false;
    View v;
   int  MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE  ;
    Button register;
    private DatabaseReference mDatabase;
    ProgressDialog mProgressDialog;
   int x = 1 ;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noticedetail_activity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        textView = (TextView) findViewById(R.id.description);
        position = getIntent().getIntExtra("note", 0);

// instantiate it within the onCreate method
        mProgressDialog = new ProgressDialog(civilnoticedetail.this);
        mProgressDialog.setMessage("A message");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("NitukENotice").child("CIVIL").child("DetailCIVIL").child("" + position).child("descr").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String des = dataSnapshot.getValue().toString();
                    textView.setText(des);
                } else {
                    Toast.makeText(civilnoticedetail.this, "Database empty!!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    public void downloadPDF(View view) {
        v = view;
        isclick = true;
        register = (Button) view.findViewById(R.id.btndownload);
        // MainActivity.vibe.vibrate(15);
        mDatabase.child("NitukENotice").child("CIVIL").child("CIVILpdf").child("" + position).child("link").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    register.setText("Downloading......");
                 //   register.setEnabled(false);
                    String url = dataSnapshot.getValue().toString();
                    if (isclick) {

                        // Here, thisActivity is the current activity
                        if (ContextCompat.checkSelfPermission(civilnoticedetail.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {

                            // Should we show an explanation?
                            if (ActivityCompat.shouldShowRequestPermissionRationale(civilnoticedetail.this,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                                // Show an explanation to the user *asynchronously* -- don't block
                                // this thread waiting for the user's response! After the user
                                // sees the explanation, try again to request the permission.

                            } else {

                                // No explanation needed, we can request the permission.

                                ActivityCompat.requestPermissions(civilnoticedetail.this,
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                                // app-defined int constant. The callback method gets the

                                // result of the request.


                            }
                        }
                        int permissionCheck = ContextCompat.checkSelfPermission(civilnoticedetail.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE);


                            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                                final DownloadTask downloadTask = new DownloadTask(civilnoticedetail.this);
                                downloadTask.execute(url, "Notice" + position + ".pdf");

                                mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        downloadTask.cancel(true);
                                    }
                                });
                                //  new DownloadFile().execute(url, "event" + position + ".pdf");

                                // isclick = false;

                            }
                        }
                    


                } else {
                    Toast.makeText(civilnoticedetail.this, "item not found!!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void viewPDF() {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File folder = new File(extStorageDirectory, "NitukENotice");
        File pdfFile = new File(folder, "Notice" + position + ".pdf");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(pdfFile), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }


    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            String fileName = sUrl[1];
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "NitukENotice");
            if (!folder.exists()) {
                folder.mkdir();
            }
            File pdfFile = new File(folder, fileName);
            try {
                pdfFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
                output = new FileOutputStream(pdfFile);

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            mProgressDialog.dismiss();
            if (result != null)
                Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
            else {
                Snackbar snackbar = Snackbar
                        .make(v, "Download successfull!!!", Snackbar.LENGTH_LONG)
                        .setAction("OPEN", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                viewPDF();
                            }
                        });
                Toast.makeText(civilnoticedetail.this, "you can check your downloaded pdf in file manager also!!!!", Toast.LENGTH_SHORT).show();
                snackbar.setActionTextColor(Color.WHITE);
                snackbar.show();
            }
        }


    }


}
/*


    public void viewPDF() {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File folder = new File(extStorageDirectory, "NitukENotice");
        File pdfFile = new File(folder, "Detail" + position + ".pdf");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(pdfFile), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    class DownloadFile extends AsyncTask<String, String, Void> {
        private static final int MEGABYTE = 1024 * 1024;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(noticedetail.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setIndeterminate(false);
            progressDialog.setMax(100);
            progressDialog.setMessage("Downloading.....");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.hide();
            Snackbar snackbar = Snackbar
                    .make(v, "Download successfull!!!", Snackbar.LENGTH_LONG)
                    .setAction("OPEN", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            viewPDF();
                        }
                    });
            Toast.makeText(noticedetail.this, "you can check your downloaded pdf in file manager also!!!!", Toast.LENGTH_SHORT).show();
            snackbar.setActionTextColor(Color.WHITE);
            snackbar.show();
            register.setEnabled(true);
            register.setText("Download PDF");
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(Integer.parseInt(values[0]));
        }

        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];
            String fileName = strings[1];
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "NitukENotice");
            if (!folder.exists()) {
                folder.mkdir();
            }
            File pdfFile = new File(folder, fileName);
            try {
                pdfFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                URL url = new URL(fileUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                FileOutputStream fileOutputStream = new FileOutputStream(pdfFile);
                int totalSize = urlConnection.getContentLength();
                long total = 0;
                byte[] buffer = new byte[MEGABYTE];
                int bufferLength = 0;
                while ((bufferLength = inputStream.read(buffer)) > 0) {
                    total += bufferLength;
                    fileOutputStream.write(buffer, 0, bufferLength);
                    publishProgress("" + (int) ((total * 100) / totalSize));
                }
                fileOutputStream.close();
                fileOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}


*/
