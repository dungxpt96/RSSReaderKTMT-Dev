package ktmt.rssreader.fragments;

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

import butterknife.BindView;
import butterknife.OnClick;
import ktmt.rssreader.Data.DataManager;
import ktmt.rssreader.Data.NewsItem;
import ktmt.rssreader.MainActivity;
import ktmt.rssreader.R;
import ktmt.rssreader.adapters.ListRssNewsAdapter;

import static ktmt.rssreader.Data.DataManager.BOOKMARK_LIST;
import static ktmt.rssreader.Data.DataManager.HISTORY_LIST;
import static ktmt.rssreader.Data.DataManager.LOVE_LIST;

public class LoveFragment extends BaseFragment implements ListRssNewsAdapter.onClickItemListener{

    @BindView(R.id.rcvLove)
    RecyclerView rcvLove;
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

    public static LoveFragment newInstance(){
        Bundle args = new Bundle();
        LoveFragment fragment = new LoveFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    int getLayoutResource() {
        return R.layout.love_fragment;
    }


    @Override
    void onViewAppear() {
        newsItems = Objects.requireNonNull(DataManager.getData(DataManager.LOVE_LIST, Objects.requireNonNull(getActivity()))).getNewsItems();
        if(newsItems == null){
            return;
        }
        listRssNewsAdapter.setBookmarkable(false);
        rcvLove.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rcvLove.setAdapter(listRssNewsAdapter);
        listRssNewsAdapter.setNewsItems(newsItems);
        listRssNewsAdapter.setOnClickItemListener(this);
    }

    @Override
    void initView(View view) {
        tvTitle.setText("Yêu thích");
        setUpButton(view, new int[]{R.id.btBack, R.id.btRecycleBin}, new int[]{R.id.btCheck,R.id.btClose});
    }

    @OnClick(R.id.btBack)
    public void onBackPressd(){
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
        Log.e("refreshView: ", "bookmark");
        if(getActivity() == null){
            return;
        }
        newsItems = DataManager.getData(LOVE_LIST, getActivity()).getNewsItems();
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick(R.id.btCheck)
    public void onAcceptDelete(){
        isDeleMode = false;
        DataManager.deleteFromList(LOVE_LIST,getActivity());
        setUpButton(this.getView(), new int[]{R.id.btBack, R.id.btRecycleBin}, new int[]{R.id.btCheck,R.id.btClose});
        refreshView();
        listRssNewsAdapter.setIsDelete(false);
    }

    @OnClick(R.id.btClose)
    public void onCloseClick(){
        isDeleMode = false;
        DataManager.resetDelete();
        listRssNewsAdapter.setIsDelete(false);
        setUpButton(this.getView(), new int[]{R.id.btBack, R.id.btRecycleBin}, new int[]{R.id.btCheck,R.id.btClose});
    }
}
