package ktmt.rssreader.Data;

import android.util.Log;

import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Myth on 3/26/2018.
 */

public class RSSReceiver {

    public static InputSource getRssStream(String link)
    {
        InputStream inputStream = null;
        InputSource is = new InputSource();
        try {
            URL url = new URL(link);
            URLConnection conn = url.openConnection();
            inputStream = conn.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            is.setCharacterStream(new StringReader(out.toString()));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        is.setEncoding("UTF-8");
        return is;
    }
}
