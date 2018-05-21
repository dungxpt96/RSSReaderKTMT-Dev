package ktmt.rssreader.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ktmt.rssreader.Data.Link;
import ktmt.rssreader.Data.NewsItem;
import ktmt.rssreader.fragments.ListNewsFragment;

public class ListTitlePagerAdapter extends FragmentPagerAdapter {

    private List<String> titles = new ArrayList<>();
    private int webId;

    public ListTitlePagerAdapter(FragmentManager fm, int webId) {
        super(fm);
        this.webId = webId;
    }

    @Override
    public int getCount() {
        if(titles == null){
            return 0;
        }
        return titles.size();
    }

    @Override
    public Fragment getItem(int position) {
        return ListNewsFragment.newInstance(position, webId);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
        notifyDataSetChanged();
    }
}
