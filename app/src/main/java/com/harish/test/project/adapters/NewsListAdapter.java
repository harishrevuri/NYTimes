package com.harish.test.project.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.harish.test.project.R;
import com.harish.test.project.entities.NewsArticle;
import com.harish.test.project.utils.APIHelper;
import com.harish.test.project.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

    private final List<NewsArticle> mValues;
    private NewsItemHandler mHandler;

    public NewsListAdapter(List<NewsArticle> items, NewsItemHandler handler) {
        mValues = items;
        mHandler = handler;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void addItems(List<NewsArticle> items) {
        mValues.addAll(items);
        this.notifyDataSetChanged();
    }

    public void clearItems() {
        mValues.clear();
        notifyDataSetChanged();
    }

    public void replaceItems(List<NewsArticle> items) {
        mValues.clear();
        mValues.addAll(items);
        notifyDataSetChanged();
    }

    public List<NewsArticle> getItems() {
        return mValues;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_source)
        TextView txtSource;
        @BindView(R.id.txt_date)
        TextView txtDate;
        @BindView(R.id.img_thumb)
        ImageView imgThumb;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }

        public void bind(int position) {
            final NewsArticle article = mValues.get(position);
            txtTitle.setText(article.getTitle());
            txtSource.setText(article.getSource());
            txtDate.setText(Utils.formatDateString(article.getPubDate()));
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mHandler.onNewsSelected(article);
                }
            });
            mView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mHandler.onNewsPreviewRequested(article);
                    return true;
                }
            });
            String imageUrl = APIHelper.getImageUrl(article.getThumbImage());
            if (Utils.isValid(imageUrl)) {
                Glide.with(mView.getContext())
                        .asBitmap()
                        .load(imageUrl)
                        .into(imgThumb);
            }
        }
    }

    public interface NewsItemHandler {
        void onNewsSelected(NewsArticle article);

        void onNewsPreviewRequested(NewsArticle article);
    }
}
