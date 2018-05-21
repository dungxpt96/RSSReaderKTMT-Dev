package ktmt.rssreader.Asysntask;

import android.os.AsyncTask;
import org.xml.sax.InputSource;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import ktmt.rssreader.Data.DataManager;
import ktmt.rssreader.Data.LocalData;
import ktmt.rssreader.Data.NewsItem;
import ktmt.rssreader.Data.RSS24Parser;
import ktmt.rssreader.Data.RSSReceiver;
import ktmt.rssreader.Data.RSSVNParser;

import static ktmt.rssreader.Data.Link.ID_24H;
import static ktmt.rssreader.Data.Link.ID_VNXPRESS;

public class GetRssListAsyn extends AsyncTask<String, Void , Void> {

    private List<NewsItem> newsItems = new ArrayList<>();
    private int webID;
    private int channelID;
    CallBackAsyn delegate;

    public GetRssListAsyn(int webID, int channelID, CallBackAsyn delegate) {
        this.webID = webID;
        this.channelID = channelID;
        this.delegate = delegate;
    }

    @Override
    protected Void doInBackground(String... url) {
        InputSource stream = RSSReceiver.getRssStream(url[0]);
        try {
            SAXParserFactory fac = SAXParserFactory.newInstance();
            SAXParser parser = fac.newSAXParser();
            if(webID == ID_VNXPRESS) {
                RSSVNParser rssParser = new RSSVNParser();
                parser.parse(stream, rssParser);

                newsItems = rssParser.getNewsList();
            }
            else if(webID == ID_24H)
            {
                RSS24Parser rssParser = new RSS24Parser();
                parser.parse(stream, rssParser);
                newsItems = rssParser.getNewsList();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        DataManager.UpdateNews(webID, channelID, new ArrayList<>(newsItems));
        LocalData.listNewItems[webID][channelID].setNewsItems((ArrayList) newsItems);
        if(delegate != null)
        {
            delegate.doneLoadData();
        }
    }
}
