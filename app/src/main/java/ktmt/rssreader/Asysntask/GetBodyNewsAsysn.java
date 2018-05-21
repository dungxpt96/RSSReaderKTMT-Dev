package ktmt.rssreader.Asysntask;

import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import static ktmt.rssreader.Data.Link.ID_24H;
import static ktmt.rssreader.Data.Link.ID_VNXPRESS;

public class GetBodyNewsAsysn extends AsyncTask<String, Void, String> {

    private WebView webView;
    private String[] listCssQueryVn = new String[]{
            "div.col_noidung",
            "article.content_detail.fck_detail.width_common",
            "figure.ob-os.ob",
            "article.content_detail.fck_detail.width_common.block_ads_connect"
    };

    public GetBodyNewsAsysn(WebView webView)
    {
        this.webView = webView;

    }

    @Override
    protected String doInBackground(String... strings) {
        int webId = Integer.parseInt(strings[1]);
        String data = "";
        try {
            Document document = (Document) Jsoup.connect(strings[0]).method(Connection.Method.GET)
                    .userAgent("Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.110 Safari/537.36")
                    .maxBodySize(0).timeout(0).get();
            if (document != null) {
                switch (webId) {
                    case ID_VNXPRESS:
                        data = getBodyVnxpress(document);
                        break;
                    case ID_24H:
                        data = getBody24h(document);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    private String getBody24h(Document document) {
        Elements sub;
        sub = document.select("article.nwsHt");
        if (sub == null) {
            sub = document.select("article.clLtImg");
        }
        return sub.get(0).toString();
    }

    private String getBodyVnxpress(Document document) {
        Element element = getElement(document);
        String time = document.select("header.clearfix").toString();
        String title = document.select("h1").get(0).toString();
        String description = document.select("h2.description").toString();
        return title + description + time + element.toString();
    }

    private Element getElement(Document document){
        Elements elements = new Elements();
        for (String aListCssQueryVn : listCssQueryVn) {
            elements = document.select(aListCssQueryVn);
            if(elements.size() >0) {
                break;
            }
        }
        if(elements.get(0).getElementById("left_calculator") != null){
            return elements.get(0).getElementById("left_calculator");
        } else {
            return elements.get(0);
        }
    }

    @Override
    protected void onPostExecute(String data) {
        webView.loadData(data, "text/html; charset=UTF-8;", null);
        super.onPostExecute(data);
    }
}
