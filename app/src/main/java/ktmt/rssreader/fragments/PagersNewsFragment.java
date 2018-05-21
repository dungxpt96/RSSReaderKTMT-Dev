package ktmt.rssreader.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import butterknife.BindView;
import ktmt.rssreader.Data.Link;
import ktmt.rssreader.R;
import ktmt.rssreader.adapters.ListTitlePagerAdapter;

public class PagersNewsFragment extends BaseFragment {

    @BindView(R.id.vpgHome)
    ViewPager viewPager;
    @BindView(R.id.tabNewsPaper)
    TabLayout tableLayout;

    private int webId;
    private ListTitlePagerAdapter mAdapter;

    public void setWebId(int webId) {
        this.webId = webId;
    }

    public static PagersNewsFragment newInstance(int webId){
        Bundle args = new Bundle();
        PagersNewsFragment fragment = new PagersNewsFragment();
        fragment.setWebId(webId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    int getLayoutResource() {
        return R.layout.pager_news_fragment;
    }

    @Override
    void initView(View view) {
        setupPager();
    }

    private void setupPager() {
        if(mAdapter == null){
            mAdapter = new ListTitlePagerAdapter(getChildFragmentManager(),webId);
        }
        mAdapter.setTitles(Link.getTitles(webId));
        viewPager.setAdapter(mAdapter);
    }

    @Override
    public boolean isKeepFragment() {
        return true;
    }
}
