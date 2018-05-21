package ktmt.rssreader.Data;

import java.util.Date;

/**
 * Created by Myth on 3/27/2018.
 */

public class NewsItem {
    public String title;
    public String link;
    private String imageLink;
    public String des;
    public Date time;
    public int webId;
    public int channelId;

    public NewsItem() {
        title = "";
        des = "";
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
