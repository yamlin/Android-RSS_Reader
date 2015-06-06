package rss.com.example.yamlin.rssreader;

import android.util.Log;
import android.util.Xml;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yamlin on 5/30/15.
 */
public class YahooXmlParser {
    private static final String ns = null;


    public static final String TAG = "YahooXmlParser";


    public ArrayList parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private ArrayList readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList<Item> items = new ArrayList<Item>();

        parser.require(XmlPullParser.START_TAG, ns, "rss");

        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            int eventType = parser.getEventType();
            String tagName = parser.getName();
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (tagName.equalsIgnoreCase("item")) {
                        items.add(readEntry(parser));
                    }
                    break;
            }
        }
        return items;
    }


    // Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
    // to their respective "read" methods for processing. Otherwise, skips the tag.
    private Item readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "item");

        Item item = new Item();
        while (true) {
            if (parser.next() == XmlPullParser.END_TAG && parser.getName().equalsIgnoreCase("item")) {
                break;
            }
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("title")) {
                item.title = readText(parser);
            } else if (name.equals("description")) {
                String desc = readText(parser);

                Pattern p = Pattern.compile("img src=\"(.*?)\"");
                Matcher m = p.matcher(desc);
                if (m.find()) {
                     item.img = m.group(1);
                }

                p = Pattern.compile("</a>(.*?)</p>");
                m = p.matcher(desc);
                if (m.find()) {
                    item.description = m.group(1);
                }
            } else if (name.equals("link")) {
                item.link = readText(parser);
            }
        }
        //Log.w(TAG, item.description);
        return item;
    }
//
    // Processes link tags in the feed.
//    private String readLink(XmlPullParser parser) throws IOException, XmlPullParserException {
//        String link = "";
//
//        return link;
//    }
//
//    // Processes summary tags in the feed.
//    private String readDesc(XmlPullParser parser) throws IOException, XmlPullParserException {
//        parser.require(XmlPullParser.START_TAG, ns, "description");
//        String description = readText(parser);
//        parser.require(XmlPullParser.END_TAG, ns, "description");
//        return description;
//    }
//
    // For the tags title and summary, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

//    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
//        if (parser.getEventType() != XmlPullParser.START_TAG) {
//            throw new IllegalStateException();
//        }
//        int depth = 1;
//        while (depth != 0) {
//            switch (parser.next()) {
//                case XmlPullParser.END_TAG:
//                    depth--;
//                    break;
//                case XmlPullParser.START_TAG:
//                    depth++;
//                    break;
//            }
//        }
//    }
}
