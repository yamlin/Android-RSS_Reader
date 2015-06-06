package rss.com.example.yamlin.rssreader;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements AsyncResponse {
    public static final String TAG = "MainActivity";

    private ListView listView;

    private List<String> rssList;

    private int currPostion;

    private List<String> category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.rssList);

        rssList = new ArrayList<>();
        rssList.add("https://tw.news.yahoo.com/rss/");
        rssList.add("https://tw.news.yahoo.com/rss/entertainment");
        rssList.add("https://tw.news.yahoo.com/rss/society");
        rssList.add("https://tw.news.yahoo.com/rss/technology");
        rssList.add("https://tw.news.yahoo.com/rss/sports");

        category = new ArrayList<String>();
        category.add("即時新聞");
        category.add("娛樂新聞");
        category.add("社會新聞");
        category.add("科技新聞");
        category.add("體育新聞");

        listView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, category));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.w(TAG, "" + position);

                new DownloadRssTask(MainActivity.this).execute(rssList.get(position));

                currPostion = position;
            }
        });
       //
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void processFinish(ArrayList<Item> items) {

        Intent intent = new Intent(MainActivity.this, RssListActivity.class);
        intent.putParcelableArrayListExtra("items", items);

        intent.putExtra("barTitle", category.get(currPostion));
        startActivityForResult(intent, 0);


    }
}
