package ktmt.rssreader.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ktmt.rssreader.fragments.BaseFragment;
import ktmt.rssreader.fragments.BookMarkFragment;
import ktmt.rssreader.fragments.DetailNewsFragment;
import ktmt.rssreader.fragments.HistoryFragment;
import ktmt.rssreader.fragments.HomeFragment;
import ktmt.rssreader.fragments.LoveFragment;
import ktmt.rssreader.fragments.SearchFragment;

public class HomePagerAdapter extends FragmentStatePagerAdapter {
    private List<BaseFragment> fragments = new ArrayList<>();

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
        Log.e("Home Page Adapter", "----------------------------------------------------------------------------");
        fragments.add(SearchFragment.newInstance());
        fragments.add(HistoryFragment.newInstance());
        fragments.add(HomeFragment.newInstance());
        fragments.add(BookMarkFragment.newInstance());
        fragments.add(LoveFragment.newInstance());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }



    @Override
    public int getCount() {
        return fragments.size();
    }

}
