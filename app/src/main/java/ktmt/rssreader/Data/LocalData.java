package ktmt.rssreader.Data;

public class LocalData {
    public static int isHomeChangeFragment = 0;
    public static String currentLink = "";
    public static int isFirstAccessApp = 0;
    public static int[][] isAccess = new int[3][25];
    public static ListNewsItem[][] listNewItems = new ListNewsItem[3][25];

    public static void initLocalData() {
        for (int i = 0; i < 3; i ++)
            for (int j = 0; j < 25; j ++) {
                listNewItems[i][j] = new ListNewsItem();
            }
    }
}
