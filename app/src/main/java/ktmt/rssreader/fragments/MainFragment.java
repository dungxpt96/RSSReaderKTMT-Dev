package ktmt.rssreader.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import ktmt.rssreader.R;
import ktmt.rssreader.adapters.HomePagerAdapter;

public class MainFragment extends BaseFragment implements OnTabSelectListener {

    @BindView(R.id.vpgMain)
    ViewPager viewPager;
    @BindView(R.id.bottomBar)
    BottomBar bottomBar;
    private int currentTab = 2;

    public static MainFragment newInstance(){
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    int getLayoutResource() {
        return R.layout.main_fragment;
    }

    @Override
    void initView(View view) {
        Log.e("Main fragment", "----- init ------");
        setupPager();
    }

    private void setupBottomBar() {
        bottomBar.setOnTabSelectListener(this);
    }

    private void setupPager() {
        viewPager.setAdapter(new HomePagerAdapter(getChildFragmentManager()));
        viewPager.postDelayed(new Runnable() {

            @Override
            public void run() {
                viewPager.setCurrentItem(currentTab);
                setupBottomBar();
            }
        }, 100);
        viewPager.setOffscreenPageLimit(5);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.e("Main fragment", "----- onPageSelected ------");
                bottomBar.selectTabAtPosition(position);
                ((BaseFragment)((HomePagerAdapter) Objects.requireNonNull(viewPager.getAdapter()))
                        .getItem(position)).refreshView();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void onTabSelected(int tabId) {
        switch (tabId){
            case R.id.tab_search:
                currentTab = 0;
                break;
            case R.id.tab_history:
                currentTab = 1;
                break;
            case R.id.tab_home:
                currentTab = 2;
                break;
            case R.id.tab_love:
                currentTab = 4;
                break;
            case R.id.tab_bookmark:
                currentTab = 3;
                break;
        }
        View view = getActivity().getCurrentFocus();
        if(view != null)
        {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        viewPager.setCurrentItem(currentTab, false);
//        updateTitle();
    }

    public void onBtSearchClick() {
        viewPager.setCurrentItem(0);
    }

}
