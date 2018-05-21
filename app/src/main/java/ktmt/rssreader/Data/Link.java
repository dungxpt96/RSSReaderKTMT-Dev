package ktmt.rssreader.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Myth on 3/28/2018.
 */

public class Link {

    public final static int ID_VNXPRESS = 2;
    public final static int ID_24H = 1;
    private static String[] VNEXPRESS= new String[]{
            "https://vnexpress.net/rss/tin-moi-nhat.rss",
            "https://vnexpress.net/rss/thoi-su.rss",
            "https://vnexpress.net/rss/the-gioi.rss",
            "https://vnexpress.net/rss/kinh-doanh.rss",
            "https://vnexpress.net/rss/startup.rss",
            "https://vnexpress.net/rss/giai-tri.rss",
            "https://vnexpress.net/rss/the-thao.rss",
            "https://vnexpress.net/rss/phap-luat.rss",
            "https://vnexpress.net/rss/giao-duc.rss",
            "https://vnexpress.net/rss/suc-khoe.rss",
            "https://vnexpress.net/rss/gia-dinh.rss",
            "https://vnexpress.net/rss/du-lich.rss",
            "https://vnexpress.net/rss/so-hoa.rss",
            "https://vnexpress.net/rss/oto-xe-may.rss",
            "https://vnexpress.net/rss/cong-dong.rss",
            "https://vnexpress.net/rss/tam-su.rss",
            "https://vnexpress.net/rss/cuoi.rss"
    };

    private static String[] VNEXPRESS_TITLE = new String[]{
            "Tin mới nhất",
            "Thời sự",
            "Thế giới",
            "Kinh doanh",
            "Startup",
            "Giải trí",
            "Thể thao",
            "Pháp luật",
            "Giáo dục",
            "Sức khỏe",
            "Gia đình",
            "Du lịch",
            "Số hóa",
            "Ô tô xe máy",
            "Cộng đồng",
            "Tâm sự",
            "Cười"
    };


    private static String[] _24H = new String[]{
            "https://www.24h.com.vn/upload/rss/trangchu24h.rss",
            "https://www.24h.com.vn/upload/rss/tintuctrongngay.rss",
            "https://www.24h.com.vn/upload/rss/bongda.rss",
            "https://www.24h.com.vn/upload/rss/anninhhinhsu.rss",
            "https://www.24h.com.vn/upload/rss/thoitrang.rss",
            "https://www.24h.com.vn/upload/rss/thoitranghitech.rss",
            "https://www.24h.com.vn/upload/rss/taichinhbatdongsan.rss",
            "https://www.24h.com.vn/upload/rss/amthuc.rss",
            "https://www.24h.com.vn/upload/rss/lamdep.rss",
            "https://www.24h.com.vn/upload/rss/phim.rss",
            "https://www.24h.com.vn/upload/rss/giaoducduhoc.rss",
            "https://www.24h.com.vn/upload/rss/bantrecuocsong.rss",
            "https://www.24h.com.vn/upload/rss/canhacmtv.rss",
            "https://www.24h.com.vn/upload/rss/thethao.rss",
            "https://www.24h.com.vn/upload/rss/congnghethongtin.rss",
            "https://www.24h.com.vn/upload/rss/otoxemay.rss",
            "https://www.24h.com.vn/upload/rss/thitruongtieudung.rss",
            "https://www.24h.com.vn/upload/rss/dulich.rss",
            "https://www.24h.com.vn/upload/rss/suckhoedoisong.rss",
            "https://www.24h.com.vn/upload/rss/cuoi24h.rss",
            "https://www.24h.com.vn/upload/rss/tintucquocte.rss",
            "https://www.24h.com.vn/upload/rss/giaitri.rss"
    };

    private static String[] _24H_TITLE = new String[]{
            "Trang chủ",
            "Tin tức trong ngày",
            "Bóng đá",
            "An ninh",
            "Thời trang",
            "High tech",
            "Tài chính bất động sản",
            "Ẩm thực",
            "Làm đẹp",
            "Phim",
            "Giáo dục du học",
            "Bạn trẻ và cuộc sống",
            "Ca nhạc MTV",
            "Thể thao",
            "Công nghệ thông tin",
            "Ô tô xe máy",
            "Thị trường tiêu dùng",
            "Du lịch",
            "Sức khỏe đời sống",
            "Cười",
            "Thế giới",
            "Giải trí"
    };

    public static String getLink(int webId, int channelId)
    {
        if(webId == ID_24H)
        {
            if(channelId >= _24H.length)
            {
                return _24H[0];
            }
            return _24H[channelId];
        }
        else
        {
            if(channelId >= VNEXPRESS.length)
            {
                return VNEXPRESS[0];
            }
            return VNEXPRESS[channelId];
        }
    }

    public static String getTitle(int webId, int channelId)
    {
        if(webId == ID_24H)
        {
            if(channelId >= _24H_TITLE.length)
            {
                return _24H_TITLE[0];
            }
            return _24H_TITLE[channelId];
        }
        else
        {
            if(channelId >= VNEXPRESS_TITLE.length)
            {
                return VNEXPRESS_TITLE[0];
            }
            return VNEXPRESS_TITLE[channelId];
        }
    }

    public static List<String> getTitles(int webId){
        switch (webId){
            case ID_24H:
                return Arrays.asList(_24H_TITLE);
            case ID_VNXPRESS:
                return Arrays.asList(VNEXPRESS_TITLE);
        }
        return null;
    }
}
