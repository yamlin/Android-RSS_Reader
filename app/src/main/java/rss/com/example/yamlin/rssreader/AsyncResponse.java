package rss.com.example.yamlin.rssreader;

import java.util.ArrayList;

/**
 * Created by yamlin on 5/31/15.
 */
public interface AsyncResponse {
    void processFinish(ArrayList<Item> items);
}
