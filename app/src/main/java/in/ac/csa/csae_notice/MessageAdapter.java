package in.ac.csa.csae_notice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import in.ac.csa.csae_notice.Civil.civilnoticedetail;
import in.ac.csa.csae_notice.Cse.csenoticedetail;
import in.ac.csa.csae_notice.Ece.ecenoticedetail;
import in.ac.csa.csae_notice.Eee.eeenoticedetail;
import in.ac.csa.csae_notice.Mech.mecnoticedetail;

import static com.facebook.FacebookSdk.getApplicationContext;


public class MessageAdapter extends ArrayAdapter<NoticeMessage> {






 //   private ListView mMessageListView;
    int len ;
    public MessageAdapter(Context context, int resource, List<NoticeMessage> objects) {
        super(context, resource, objects);
    }




    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_message, parent, false);
        }




       // ImageView photoImageView = (ImageView) convertView.findViewById(R.id.photoImageView);
        TextView messageTextView = (TextView) convertView.findViewById(R.id.messageTextView);
        ImageView messageImageView = (ImageView) convertView.findViewById(R.id.messageImageView);
       // TextView authorTextView = (TextView) convertView.findViewById(R.id.nameTextView);

        NoticeMessage message = getItem(position);

        messageImageView.setVisibility(View.VISIBLE);


        /*
        boolean isPhoto = message.getPhotoUrl() != null;
        if (isPhoto) {
            messageTextView.setVisibility(View.GONE);
            photoImageView.setVisibility(View.VISIBLE);
            Glide.with(photoImageView.getContext())
                    .load(message.getPhotoUrl())
                    .into(photoImageView);
        } else
         {
        */




        messageTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Globalclass mApp = ((Globalclass)getApplicationContext());
                    int len = mApp.getGlobalVariable();
                    String sec = mApp.getSectionVariable() ;

                    //System.out.println("MessageTextView set") ;
                    System.out.println("Position "+position) ;
                    //System.out.println("Length: "+enlen) ;

                    if(sec.equals("ACADNotice"))
                    {
                    System.out.println("Request Send");
                        Intent intent = new Intent(getContext(), Imageload.class);
                        intent.putExtra("note", len - position -1);
                        getContext().startActivity(intent);
                    }
                    if(sec.equals("ADVENotice"))
                    {
                        System.out.println("Request Send");
                        Intent intent = new Intent(getContext(), Imageload.class);
                        intent.putExtra("note", len - position - 1);
                        getContext().startActivity(intent);
                    }
                    if(sec.equals("CULTNotice"))
                    {
                        System.out.println("Request Send");
                        Intent intent = new Intent(getContext(), Imageload.class);
                        intent.putExtra("note", len - position - 1);
                        getContext().startActivity(intent);
                    }
                    if(sec.equals("LOSTNotice"))
                    {
                        System.out.println("Request Send");
                        Intent intent = new Intent(getContext(), Imageload.class);
                        intent.putExtra("note", len - position - 1);
                        getContext().startActivity(intent);
                    }


                    if(sec.equals("eNotice"))
                    {

                        System.out.println("Request Send") ;
                        Intent intent = new Intent(getContext(), Imageload.class);
                        intent.putExtra("note", len - position - 1);
                     //   intent.putExtra("brnch", sec);
                        getContext().startActivity(intent);

                    }
                    if(sec.equals("CSENotice"))
                    {

                        System.out.println("Request Send") ;
                        Intent intent = new Intent(getContext(), Imageload.class);
                        intent.putExtra("note", len - position - 1);
                       // intent.putExtra("brnch", sec);
                        getContext().startActivity(intent);

                    }

                    if(sec.equals("ECENotice"))
                    {

                        System.out.println("Request Send") ;
                        Intent intent = new Intent(getContext(), Imageload.class);
                        intent.putExtra("note", len - position - 1);
                        //intent.putExtra("brnch", sec);
                        getContext().startActivity(intent);

                    }

                    if(sec.equals("EEENotice"))
                    {

                        System.out.println("Request Send") ;
                        Intent intent = new Intent(getContext(), Imageload.class);
                        intent.putExtra("note", len - position - 1);
                       // intent.putExtra("brnch", sec);
                        getContext().startActivity(intent);

                    }

                    if(sec.equals("MECNotice"))
                    {

                        System.out.println("Request Send") ;
                        Intent intent = new Intent(getContext(), Imageload.class);
                        intent.putExtra("note", len - position - 1);
                       // intent.putExtra("brnch", sec);
                        getContext().startActivity(intent);

                    }

                    if(sec.equals("CIVILNotice"))
                    {

                        System.out.println("Request Send") ;
                        Intent intent = new Intent(getContext(), Imageload.class);
                        intent.putExtra("note", len - position - 1);
                       // intent.putExtra("brnch", sec);
                        getContext().startActivity(intent);

                    }



                }
            });



        messageImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("MessageImageView set") ;
                System.out.println("Position "+position) ;
                Globalclass mApp = ((Globalclass)getApplicationContext());
                int len = mApp.getGlobalVariable();
                String sec = mApp.getSectionVariable() ;


                if(sec.equals("eNotice")) {
                    Intent intent = new Intent(getContext(), noticedetail.class);

                    intent.putExtra("note", len - position - 1);
                    getContext().startActivity(intent);
                }
               if(sec.equals("CSENotice"))
               {

                   Intent intent = new Intent(getContext(), csenoticedetail.class);

                   intent.putExtra("note", len - position - 1);
                   getContext().startActivity(intent);
               }
                if(sec.equals("ECENotice"))
                {

                    Intent intent = new Intent(getContext(), ecenoticedetail.class);

                    intent.putExtra("note", len - position - 1);
                    getContext().startActivity(intent);
                }

                if(sec.equals("EEENotice"))
                {

                    Intent intent = new Intent(getContext(), eeenoticedetail.class);

                    intent.putExtra("note", len - position - 1);
                    getContext().startActivity(intent);
                }


                if(sec.equals("MECNotice"))
                {

                    Intent intent = new Intent(getContext(), mecnoticedetail.class);

                    intent.putExtra("note", len - position - 1);
                    getContext().startActivity(intent);
                }


                if(sec.equals("CIVILNotice"))
                {

                    Intent intent = new Intent(getContext(), civilnoticedetail.class);

                    intent.putExtra("note", len - position - 1);
                    getContext().startActivity(intent);
                }





            }
        });


          messageTextView.setVisibility(View.VISIBLE);
           // photoImageView.setVisibility(View.GONE);
            messageTextView.setText(message.getText());





        //}
       // authorTextView.setText(message.getName());

        return convertView;
    }



}
