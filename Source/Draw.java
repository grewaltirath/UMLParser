package umlparser.umlparser;

import java.io.*;
import java.net.*;

public class Draw {

    public static Boolean image(String content, String dir) {

        try {
            String ref = "https://yuml.me/diagram/plain/class/" + content
                    + ".png"; //link to generate diagram
            URL u = new URL(ref);
            HttpURLConnection h = (HttpURLConnection) u.openConnection();
            h.setRequestMethod("GET"); //making a HTTP GET Request
            h.setRequestProperty("Accept", "application/json");

            if (h.getResponseCode() != 200) {
                throw new RuntimeException(
                        "HTTP Response Code: " + h.getResponseCode());
            }
            OutputStream op = new FileOutputStream(new File(dir));
            int read = 0;
            byte[] b = new byte[1024];

            while ((read = h.getInputStream().read(b)) != -1) {
                op.write(b, 0, read);
            }
            op.close();
            h.disconnect();
        } 
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}
