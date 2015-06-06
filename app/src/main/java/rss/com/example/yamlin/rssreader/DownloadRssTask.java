package rss.com.example.yamlin.rssreader;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;



/**
 * Created by yamlin on 5/30/15.
 */
public class DownloadRssTask extends AsyncTask<String, Integer, ArrayList<Item>> {

    public static final String TAG = "DownloadRssTask";

    private ArrayList<Item> items;

    private AsyncResponse delgate;

    private HostnameVerifier hostnameVerifier = new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            HostnameVerifier hv =
                    HttpsURLConnection.getDefaultHostnameVerifier();

            //return true;
            return hv.verify(hostname, session);
        }
    };

    public DownloadRssTask(AsyncResponse asyncResponse) {
        delgate = asyncResponse;
    }

    @Override
    protected void onPreExecute() {
        items = new ArrayList<>();
    }


    @Override
    protected ArrayList<Item> doInBackground(String... urls) {
        for (String u: urls) {
            HttpsURLConnection conn = null;
            try {
                URL url = new URL(u);
                conn = (HttpsURLConnection) url.openConnection();
                conn.setHostnameVerifier(hostnameVerifier);

                YahooXmlParser parser = new YahooXmlParser();

                items =  parser.parse(conn.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }

        }
        return items;
    }

    @Override
    protected void onPostExecute (ArrayList<Item> result){
        delgate.processFinish(result);
    }
}
