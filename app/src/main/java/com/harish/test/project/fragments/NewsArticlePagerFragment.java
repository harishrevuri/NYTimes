package com.harish.test.project.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.harish.test.project.R;
import com.harish.test.project.entities.NewsArticle;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsArticlePagerFragment extends Fragment {

    public static final String ARG_ARTICLES = "ARG_ARTICLES";
    public static final String ARG_INDEX = "ARG_INDEX";

    @BindView(R.id.pager)
    ViewPager pager;

    private ArrayList<NewsArticle> articles;
    private int selectedIndex;

    public NewsArticlePagerFragment() {
        // Required empty public constructor
    }

    public static NewsArticlePagerFragment newInstance(ArrayList<NewsArticle> articles, int selectedIndex) {
        NewsArticlePagerFragment fragment = new NewsArticlePagerFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ARTICLES, articles);
        args.putInt(ARG_INDEX, selectedIndex);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            articles = (ArrayList<NewsArticle>) getArguments().getSerializable(ARG_ARTICLES);
            selectedIndex = getArguments().getInt(ARG_INDEX);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news_article_pager, container, false);
        ButterKnife.bind(this, rootView);
        setHasOptionsMenu(false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pager.setAdapter(new NewsPagerAdapter(getChildFragmentManager()));
        pager.setCurrentItem(selectedIndex);
    }

    private class NewsPagerAdapter extends FragmentPagerAdapter {

        public NewsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return NewsArticleDetailFragment.newInstance(articles.get(position));
        }

        @Override
        public int getCount() {
            return articles.size();
        }
    }
}
