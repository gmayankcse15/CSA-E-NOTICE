package in.ac.csa.csae_notice;

/**
 * Created by mayank on 21/5/17.
 */


public class NoticeMessage {

    private String text;
   // private String name;
    //private String photoUrl;

    public NoticeMessage() {
    }

    public NoticeMessage(String text) {
        this.text = text;
      //  this.name = name;
       // this.photoUrl = photoUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
/*
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }


*/
}
