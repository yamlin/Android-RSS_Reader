package rss.com.example.yamlin.rssreader;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yamlin on 5/31/15.
 */
public class Item implements Parcelable {

    public String title;
    public String link;
    public String description;
    public String img;

    public Item() {}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(link);
        dest.writeString(description);
        dest.writeString(img);
    }

    public static final Parcelable.Creator<Item> CREATOR
            = new Parcelable.Creator<Item>() {
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    private Item(Parcel in) {
        title = in.readString();
        link = in.readString();
        description = in.readString();
        img = in.readString();
    }

}
