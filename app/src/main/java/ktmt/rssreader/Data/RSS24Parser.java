package ktmt.rssreader.Data;

/**
 * Created by Myth on 5/4/2018.
 */

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Myth on 3/27/2018.
 */

public class RSS24Parser extends DefaultHandler {
    String tabName, descript;
    boolean startParse;
    boolean parseTitle, parseDes, parseDate, parseLink;
    private ArrayList<NewsItem> newsList = new ArrayList<>();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        tabName = qName;
        if (qName.equalsIgnoreCase("item")) {
            startParse = true;
            newsList.add(new NewsItem());
            descript = "";
            parseDate = true;
            parseDes = true;
            parseLink = true;
            parseTitle = true;
        } else if (qName.equalsIgnoreCase("img") && startParse) {
            newsList.get(newsList.size() - 1).setImageLink(attributes.getValue(attributes.getIndex("src")));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (!startParse)
            return;
        if (tabName.equalsIgnoreCase("title")) {
            String title = new String(ch, start, length);
            try {
                newsList.get(newsList.size() - 1).title = newsList.get(newsList.size() - 1).title + title;
                newsList.get(newsList.size() - 1).title = newsList.get(newsList.size() - 1).title.replace("&#34;","\"");
                newsList.get(newsList.size() - 1).title = newsList.get(newsList.size() - 1).title.replace("#34;","\"");
                Log.e("characters: ","" );
            } catch (Exception e) {
                Log.e("Error", "");
            }
        } else if (tabName.equalsIgnoreCase("img")) {
            String des = new String(ch, start, length);
            try {
                des = des.replace("&#34;","\"");
                des = des.replace("#34;","\"");
                newsList.get(newsList.size() - 1).des = newsList.get(newsList.size() - 1).des + des;
            } catch (Exception e) {
                Log.e("Error", "");
            }
        }
        else if(tabName.equalsIgnoreCase("description"))
        {
            descript = descript + new String(ch, start, length);
            try {
                newsList.get(newsList.size() - 1).des = descript.substring(descript.indexOf("<br />") + 6);
                newsList.get(newsList.size() - 1).setImageLink(descript.substring(descript.indexOf("src=") + 5, descript.indexOf("alt=") - 2));
            }
            catch (Exception e)
            {
                Log.e("Error: ", e + "");
                Log.e("Error: ", descript);
            }
        }
        else if (tabName.equalsIgnoreCase("pubdate") && parseDate) {
            String dateString = new String(ch, start, length);
            SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
            try {
                newsList.get(newsList.size() - 1).time = format.parse(dateString);
            } catch (Exception e) {
                Log.e("", "" + e);
            }
            parseDate = false;
        } else if (tabName.equalsIgnoreCase("link") && parseLink) {
            String lnk = new String(ch, start, length);
            newsList.get(newsList.size() - 1).link = lnk;
            parseLink = false;
        }
    }

    public ArrayList<NewsItem> getNewsList() {
        return newsList;
    }
}
