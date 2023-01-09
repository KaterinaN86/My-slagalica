package utility;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class VerifyBrokenLink {

   public int validLinkNumber=0;
   public int invalidLinkNumber=0;
    public boolean verifyLink(String url) {
        try {
            URL urlVar = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlVar.openConnection();
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() >= 400) {
                System.out.println(urlVar + ": is a broken link" + "--- Response code: " +  httpURLConnection.getResponseCode());
                invalidLinkNumber+=1;
                return false;
            } else {
                System.out.println(urlVar + ": is a valid link" +  "--- Response code: " +  httpURLConnection.getResponseCode());
                validLinkNumber+=1;
                return true;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
