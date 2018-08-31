package com.harish.test.project.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.harish.test.project.R;
import com.harish.test.project.adapters.NewsListAdapter;
import com.harish.test.project.entities.NewsArticle;
import com.harish.test.project.utils.APIHelper;
import com.harish.test.project.utils.EndlessOnScrollListener;
import com.harish.test.project.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsArticleListFragment extends Fragment {

    @BindView(R.id.empty)
    View empty;
    @BindView(R.id.progress)
    SwipeRefreshLayout progress;
    @BindView(R.id.list_news)
    RecyclerView listNews;

    private NewsListAdapter adapter;

    private int currentPage = 0;
    private String searchKeyword = null;

    private OnFragmentInteractionListener mListener;

    private List<NewsArticle> backupList;
    private int backupCurrentPage;

    public NewsArticleListFragment() {
        // Required empty public constructor
    }

    public static NewsArticleListFragment newInstance() {
        NewsArticleListFragment fragment = new NewsArticleListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news_article_list, container, false);
        ButterKnife.bind(this, rootView);
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new NewsListAdapter(new ArrayList<NewsArticle>(), new NewsListAdapter.NewsItemHandler() {
            @Override
            public void onNewsSelected(NewsArticle article) {
                //mListener.onNewsSelected(article);
                List<NewsArticle> articles = new ArrayList<>(adapter.getItems());
                int index = articles.indexOf(article);
                mListener.onNewsSelected(articles, index);
            }

            @Override
            public void onNewsPreviewRequested(NewsArticle article) {
                mListener.onNewsPreviewRequested(article);
            }
        });
        listNews.setAdapter(adapter);

        listNews.addOnScrollListener(new EndlessOnScrollListener() {
            @Override
            public void onScrolledToEnd() {
                loadNews(currentPage);
            }
        });

        progress.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNews(currentPage);
            }
        });
        loadNews(currentPage);
    }

    private void loadNews(int page) {
        showProgress();
        APIHelper.getAdCampaignsInBackground(getContext(), searchKeyword, page, new APIHelper.TaskListener() {
            @Override
            public void onTaskDone(Object object) {
                hideProgress();
                List<NewsArticle> articles = (List<NewsArticle>) object;
                adapter.addItems(articles);
                currentPage += 1;
            }

            @Override
            public void onTaskFailed(String problem) {
                hideProgress();
            }

            @Override
            public void onConnectionError() {
                hideProgress();
            }
        });
    }

    private void showProgress() {
        progress.setRefreshing(true);
    }

    private void hideProgress() {
        progress.setRefreshing(false);
    }

    private void handleSearchText(String text) {
        searchKeyword = text;
        if (Utils.isValid(text)) {
            if (backupList == null) {
                backupList = adapter.getItems();
                backupCurrentPage = currentPage;
            }
            adapter.clearItems();
            currentPage = 0;
            loadNews(currentPage);
        } else {
            if (backupList != null) {
                adapter.replaceItems(backupList);
                backupList.clear();
                backupList = null;
                currentPage = backupCurrentPage;
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.news_list, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                handleSearchText(newText);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onNewsSelected(NewsArticle article);

        void onNewsPreviewRequested(NewsArticle article);

        void onNewsSelected(List<NewsArticle> articles, int selectedIndex);
    }
}
