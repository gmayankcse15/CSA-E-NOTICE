package in.ac.csa.csae_notice;

import android.app.Application;

/**
 * Created by mayank on 20/6/17.
 */

public class Globalclass extends Application {

    private int eNlen ;
    private String section ;

    public int getGlobalVariable()
    {
        return eNlen ;
    }
    public void setGlobalVariable(int len)
    {
        eNlen =  len ;
    }
    public String getSectionVariable()
    {
        return section ;
    }
    public void setSectionVariable(String sec)
    {
       section =  sec ;
    }



}
