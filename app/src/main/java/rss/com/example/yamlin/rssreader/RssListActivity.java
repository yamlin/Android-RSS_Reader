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


public class RssListActivity extends ActionBarActivity {

    public final static String TAG = "RssListActivity";

    private ListView listView;

    private ArrayList<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss_list);
        listView = (ListView) findViewById(R.id.rssListView);

        getSupportActionBar().setTitle(getIntent().getStringExtra("barTitle") + "列表");


        items = getIntent().getParcelableArrayListExtra("items");

        ArrayList<String> list = new ArrayList<>(items.size());
        for (Item i: items) {
            list.add(i.title);
        }

        listView.setAdapter(new ItemsAdapter(this, R.layout.singleitem, items));


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RssListActivity.this, WebviewActivity.class);
                intent.putExtra("url", items.get(position).link);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rss_list, menu);

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
