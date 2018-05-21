package ktmt.rssreader.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.PropertyPermission;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import ktmt.rssreader.Data.DataManager;
import ktmt.rssreader.Data.NewsItem;
import ktmt.rssreader.GlideModule.GlideApp;
import ktmt.rssreader.Helper.Helper;
import ktmt.rssreader.R;

public class ListRssNewsAdapter extends RecyclerView.Adapter<ListRssNewsAdapter.BaseHolder> {

    private List<NewsItem> newsItems = new ArrayList<>();
    private boolean isBookmarkable = true;
    private boolean isDelete = false;

    public ListRssNewsAdapter() {
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
        notifyDataSetChanged();
    }

    public interface onClickItemListener{
        void onClickItem(int position);
    }

    private onClickItemListener onClickItemListener;

    public void setOnClickItemListener(ListRssNewsAdapter.onClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public List<NewsItem> getNewsItems() {
        return newsItems;
    }

    public void setNewsItems(List<NewsItem> newsItems) {
        this.newsItems = newsItems;
        notifyDataSetChanged();
    }

    public boolean isBookmarkable() {
        return isBookmarkable;
    }

    public void setBookmarkable(boolean bookmarkable) {
        isBookmarkable = bookmarkable;
    }

    @NonNull
    @Override
    public BaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChildHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.child_rss_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return newsItems.size();
    }

    public void onRecycleBinClick(){

    }

    class ChildHolder extends BaseHolder{

        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.imv)
        ImageView imageView;
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.tvDescription)
        TextView tvDescription;
        @BindView(R.id.imvBookmark)
        ImageView imvBookmark;
        @BindView(R.id.checkBox)
        CheckBox checkBox;

        private boolean isBookmarked;

        ChildHolder(View itemView) {
            super(itemView);
        }

        @Override
        void onBindingData(int position) {
            tvTitle.setText(newsItems.get(position).title);
            tvTime.setText(Helper.changeDateToString(newsItems.get(position).time));
            tvDescription.setText(newsItems.get(position).des);
            isBookmarked = DataManager.isBookmarked(newsItems.get(position).link);
            if(isBookmarked){
                GlideApp.with(itemView.getContext()).load(R.drawable.ic_bookmark_selected).into(imvBookmark);
            } else {
                GlideApp.with(itemView.getContext()).load(R.drawable.ic_bookmark_unselected).into(imvBookmark);
            }
            GlideApp.with(itemView.getContext()).load(newsItems.get(position).getImageLink())
                    .placeholder(R.drawable.img_error)
                    .into(imageView);
            if(!isBookmarkable){
                imvBookmark.setVisibility(View.GONE);
            }
            checkBox.setChecked(false);
            if(isDelete){
                checkBox.setVisibility(View.VISIBLE);
            } else {
                checkBox.setVisibility(View.GONE);
            }
        }

        @OnClick(R.id.imvBookmark)
        public void onImvBookmarkClick(){
            if(!isBookmarked) {
                DataManager.addItem(DataManager.BOOKMARK_LIST, (Activity) Objects.requireNonNull(itemView.getContext()),newsItems.get(getAdapterPosition()));
                GlideApp.with(itemView.getContext()).load(R.drawable.ic_bookmark_selected).into(imvBookmark);
            }
        }

        @OnCheckedChanged(R.id.checkBox)
        public void onCheckedCheckBoxChange(){
            if(checkBox.isChecked()) {
                DataManager.addItemDelete(getAdapterPosition());
            } else {
                DataManager.removeItemDelete(getAdapterPosition());
            }
        }

        public void deleteBookMark() {
            if(isBookmarked) {
                DataManager.deleteData(DataManager.BOOKMARK_LIST, (Activity) Objects.requireNonNull(itemView.getContext()), newsItems.get(getAdapterPosition()).link);
            }
        }

    }

    abstract class BaseHolder extends RecyclerView.ViewHolder {
        BaseHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickItemListener.onClickItem(getAdapterPosition());
                    ((ChildHolder) BaseHolder.this).deleteBookMark();
                }
            });
        }

        void bind(int position) {
            onBindingData(position);
        }

        abstract void onBindingData(int position);
    }
}
