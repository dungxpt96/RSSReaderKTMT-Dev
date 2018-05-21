package ktmt.rssreader.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import ktmt.rssreader.Data.DataManager;
import ktmt.rssreader.Data.NewsItem;
import ktmt.rssreader.MainActivity;
import ktmt.rssreader.R;
import ktmt.rssreader.adapters.ListRssNewsAdapter;

import static ktmt.rssreader.Data.DataManager.BOOKMARK_LIST;
import static ktmt.rssreader.Data.DataManager.HISTORY_LIST;

public class HistoryFragment extends BaseFragment implements ListRssNewsAdapter.onClickItemListener{

    @BindView(R.id.rcvHistory)
    RecyclerView rcvBookmark;
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
    private ListRssNewsAdapter listRssNewsAdapter = new ListRssNewsAdapter();
    private List<NewsItem> newsItems = new ArrayList<>();
    private boolean isDeleMode = false;

    public static HistoryFragment newInstance() {
        Bundle args = new Bundle();
        HistoryFragment fragment = new HistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    int getLayoutResource() {
        return R.layout.history_fragment;
    }

    @Override
    void initView(View view) {
        tvTitle.setText("Lịch sử");
        Log.e("initView: ", "historyFrag" );
        setUpButton(view, new int[]{R.id.btBack, R.id.btRecycleBin}, new int[]{R.id.btCheck,R.id.btClose});
    }

    @Override
    void onViewAppear() {
        Log.e("onViewAppear: ", "historyFrag" );
        newsItems = Objects.requireNonNull(DataManager.getData(DataManager.HISTORY_LIST, Objects.requireNonNull(getActivity()))).getNewsItems();
        if (newsItems == null) {
            return;
        }
        listRssNewsAdapter.setBookmarkable(false);
        rcvBookmark.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rcvBookmark.setAdapter(listRssNewsAdapter);
        listRssNewsAdapter.setNewsItems(newsItems);
        listRssNewsAdapter.setOnClickItemListener(this);
    }


    @OnClick(R.id.btBack)
    public void onBackPressd() {
        super.onBackPressd();
    }

    @Override
    public void onClickItem(int position) {
        if(!isDeleMode) {
            ((MainActivity) Objects.requireNonNull(getActivity())).changeFragment(DetailNewsFragment.newInstance(newsItems.get(position), newsItems.get(position).webId));
            System.out.println("------------- " + newsItems.get(position).webId);
            Log.e("onClickItem-history", String.valueOf(newsItems.get(position).webId));
            DataManager.addItem(DataManager.HISTORY_LIST, Objects.requireNonNull(getActivity()), newsItems.get(position));
        }
    }

    @Override
    public void refreshView() {
        Log.e("refreshView: ", "");
        if(getActivity() == null){
            return;
        }
        newsItems = DataManager.getData(HISTORY_LIST, getActivity()).getNewsItems();
        listRssNewsAdapter.setNewsItems(newsItems);
    }

    @OnClick(R.id.btRecycleBin)
    public void onBtRecycleBinClick(){
        isDeleMode = true;
        listRssNewsAdapter.setIsDelete(true);
        setUpButton(this.getView(), new int[]{R.id.btCheck,R.id.btClose}, new int[]{R.id.btBack, R.id.btRecycleBin});
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick(R.id.btCheck)
    public void onAcceptDelete(){
        isDeleMode = false;
        DataManager.deleteFromList(HISTORY_LIST,getActivity());
        setUpButton(this.getView(), new int[]{R.id.btBack, R.id.btRecycleBin}, new int[]{R.id.btCheck,R.id.btClose});
        refreshView();
        listRssNewsAdapter.setIsDelete(false);
    }

    @OnClick(R.id.btClose)
    public void onCloseClick(){
        isDeleMode = false;
        DataManager.resetDelete();
        listRssNewsAdapter.setIsDelete(false);
        setUpButton(this.getView(), new int[]{R.id.btBack,  R.id.btRecycleBin}, new int[]{R.id.btCheck,R.id.btClose});
    }
}
