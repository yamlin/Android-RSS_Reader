package rss.com.example.yamlin.rssreader;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class WebviewActivity extends ActionBarActivity {

    public final static String TAG = "WebviewActivity";

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        String url = getIntent().getStringExtra("url");
        webView = (WebView) findViewById(R.id.webView);


        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onLoadResource (WebView view, String url) {
                super.onLoadResource(view, url);
                //findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                //Log.w(TAG, "Start load " + url);
            }

            @Override
            public void onPageFinished (WebView view, String url) {
                Log.w(TAG, "Finished load " + url);

                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            }
        });
        webView.loadUrl(url);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_webview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
