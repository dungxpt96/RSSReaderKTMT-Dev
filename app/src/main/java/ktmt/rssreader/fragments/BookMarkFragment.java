package ktmt.rssreader.fragments;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.OnClick;
import ktmt.rssreader.Data.DataManager;
import ktmt.rssreader.Data.NewsItem;
import ktmt.rssreader.MainActivity;
import ktmt.rssreader.R;
import ktmt.rssreader.adapters.ListRssNewsAdapter;

import static ktmt.rssreader.Data.DataManager.BOOKMARK_LIST;

public class BookMarkFragment extends BaseFragment implements ListRssNewsAdapter.onClickItemListener {

    @BindView(R.id.rcvBookmark)
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

    public static BookMarkFragment newInstance() {
        Bundle args = new Bundle();
        BookMarkFragment fragment = new BookMarkFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    int getLayoutResource() {
        return R.layout.book_mark_fragment;
    }

    @Override
    void onViewAppear() {
        Log.e("onViewAppear: ", "bookmark");
        refreshView();
    }

    @Override
    void initView(View view) {
        Log.e("initView: ", "bookmark");
        tvTitle.setText("Đánh dấu");
        setUpButton(view, new int[]{R.id.btBack, R.id.btRecycleBin}, new int[]{R.id.btCheck,R.id.btClose});
        newsItems = Objects.requireNonNull(DataManager.getData(BOOKMARK_LIST, Objects.requireNonNull(getActivity()))).getNewsItems();
        if (newsItems == null) {
            return;
        }
        rcvBookmark.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rcvBookmark.setAdapter(listRssNewsAdapter);
        listRssNewsAdapter.setNewsItems(newsItems);
        listRssNewsAdapter.setOnClickItemListener(this);
    }

    /*@Override
    void initView(View view) {
        Log.e("initView: ", "bookmark");
        tvTitle.setText("Đánh dấu");
        setUpButton(view, new int[]{R.id.btBack, R.id.btRecycleBin}, new int[]{R.id.btCheck,R.id.btClose});
    }
    */

    @Override
    public void onResume() {
        Log.e("onResume: ", "Bookmark");
        super.onResume();
    }

    @OnClick(R.id.btBack)
    public void onBackPressd() {
        Log.e("onBackPressd: ", "sfsfsfs");
        super.onBackPressd();
    }

    @Override
    public void onClickItem(int position) {
        if(!isDeleMode) {
            ((MainActivity) Objects.requireNonNull(getActivity())).changeFragment(DetailNewsFragment.newInstance(newsItems.get(position), newsItems.get(position).webId));
            DataManager.addItem(DataManager.HISTORY_LIST, Objects.requireNonNull(getActivity()), newsItems.get(position));
        }
    }

    @Override
    public void refreshView() {

        Log.e("refreshView: ", "-------------- bookmark ---------------");
        if(getActivity() == null){
            return;
        }
        newsItems = DataManager.getData(BOOKMARK_LIST, getActivity()).getNewsItems();
        listRssNewsAdapter.setNewsItems(newsItems);
        listRssNewsAdapter.setIsDelete(false);
        isDeleMode = false;

    }

    @OnClick(R.id.btRecycleBin)
    public void onBtRecycleBinClick(){
        isDeleMode = true;
        listRssNewsAdapter.setIsDelete(true);
        setUpButton(this.getView(), new int[]{R.id.btCheck,R.id.btClose}, new int[]{R.id.btBack, R.id.btRecycleBin});
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick(R.id.btCheck)
    public void onAcceptDelete(){
        DataManager.deleteFromList(BOOKMARK_LIST,getActivity());
        setUpButton(this.getView(), new int[]{R.id.btBack, R.id.btRecycleBin}, new int[]{R.id.btCheck,R.id.btClose});
        refreshView();
    }

    @OnClick(R.id.btClose)
    public void onCloseClick(){
        DataManager.resetDelete();
        listRssNewsAdapter.setIsDelete(false);
        setUpButton(this.getView(), new int[]{R.id.btBack, R.id.btRecycleBin}, new int[]{R.id.btCheck,R.id.btClose});

    }
}
