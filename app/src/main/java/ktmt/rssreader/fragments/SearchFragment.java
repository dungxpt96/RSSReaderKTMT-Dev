package ktmt.rssreader.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTouch;
import ktmt.rssreader.Data.DataManager;
import ktmt.rssreader.Data.NewsItem;
import ktmt.rssreader.MainActivity;
import ktmt.rssreader.R;
import ktmt.rssreader.adapters.ListRssNewsAdapter;

public class SearchFragment extends BaseFragment implements ListRssNewsAdapter.onClickItemListener {

    @BindView(R.id.rcvSearch)
    RecyclerView rcvSearch;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.btBack)
    ImageView btBack;
    @BindView(R.id.btRecycleBin)
    ImageView btRecycleBin;
    @BindView(R.id.btCheck)
    ImageView btCheck;
    @BindView(R.id.btClose)
    ImageView btClose;
    @BindView(R.id.s_btOK)
    Button s_btOK;
    @BindView(R.id.s_edtSearch)
    EditText s_sdtSearch;

    private ListRssNewsAdapter listRssNewsAdapter = new ListRssNewsAdapter();
    private List<NewsItem> newsItems = new ArrayList<>();
    private boolean isDeleMode = false;

    public static SearchFragment newInstance(){
        Bundle args = new Bundle();
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    int getLayoutResource() {
        return R.layout.search_fragment;
    }

    @Override
    void onViewAppear() {

    }

    @OnClick(R.id.s_edtSearch)
    public void onEdtSearchClick() {

    }

    @OnClick(R.id.s_btOK)
    public void onOkButtonSearchClick() {
        String patternString = s_sdtSearch.getText().toString();
        if(patternString == null)
            patternString = "";
        Log.e("debug", patternString);
        newsItems = DataManager.SearchData(patternString);
        for(NewsItem ni : newsItems) {
            Log.e("Search items:", ni.link);
        }
        if(newsItems == null){
            Log.e("Null new item", "bong da bi null");
            return;
        }

        rcvSearch.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rcvSearch.setAdapter(listRssNewsAdapter);
        listRssNewsAdapter.setNewsItems(newsItems);
        listRssNewsAdapter.setOnClickItemListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    void initView(View view) {
        Log.e("Search View Init", "run");
        tvTitle.setText("Tìm kiếm");
        s_sdtSearch.requestFocus();
        setUpButton(view, new int[]{R.id.btBack, R.id.btRecycleBin}, new int[]{R.id.btCheck,R.id.btClose});
    }

    @OnClick(R.id.btBack)
    public void onBackPressd(){
        super.onBackPressd();
    }

    @Override
    public void onClickItem(int position) {
        ((MainActivity) Objects.requireNonNull(getActivity())).changeFragment(DetailNewsFragment.newInstance(newsItems.get(position), newsItems.get(position).webId));
        System.out.println("------------- " + newsItems.get(position).webId);
        Log.e("onClickItem", String.valueOf(newsItems.get(position).webId));
        DataManager.addItem(DataManager.HISTORY_LIST, Objects.requireNonNull(getActivity()), newsItems.get(position));
    }
}

