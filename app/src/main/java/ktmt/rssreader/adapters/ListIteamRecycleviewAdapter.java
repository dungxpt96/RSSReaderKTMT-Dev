package ktmt.rssreader.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ktmt.rssreader.Data.NewsItem;
import ktmt.rssreader.Helper.Helper;
import ktmt.rssreader.R;

public class ListIteamRecycleviewAdapter extends RecyclerView.Adapter<ListIteamRecycleviewAdapter.BaseHolder> {

    private List<NewsItem> datas;

    public ListIteamRecycleviewAdapter(List<NewsItem> newsItemList) {
        this.datas = newsItemList;
    }

    @NonNull
    @Override
    public BaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChildHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.child_recycle_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ChildHolder extends BaseHolder {

        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvDescription)
        TextView tvDescription;
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.img)
        ImageView img;

        ChildHolder(View itemView) {
            super(itemView);
        }

        @Override
        void bindingData(int position) {
            Glide.with(itemView.getContext()).load(datas.get(position).link).into(img);
            tvTitle.setText(datas.get(position).title);
            tvDescription.setText(datas.get(position).des);
            tvTime.setText(Helper.changeDateToString(datas.get(position).time));
        }
    }

    abstract class BaseHolder extends RecyclerView.ViewHolder {

        BaseHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(int position) {
            bindingData(position);
        }

        abstract void bindingData(int position);
    }
}
