package com.harish.test.project.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.harish.test.project.R;
import com.harish.test.project.entities.NewsArticle;
import com.harish.test.project.fragments.NewsArticleDetailFragment;
import com.harish.test.project.fragments.NewsArticleListFragment;
import com.harish.test.project.fragments.NewsArticlePagerFragment;
import com.harish.test.project.fragments.NewsArticlePreviewFragment;
import com.harish.test.project.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        NewsArticleListFragment.OnFragmentInteractionListener,
        NewsArticleDetailFragment.OnFragmentInteractionListener,
        NewsArticlePreviewFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        showNewsList();
    }

    @Override
    public void onNewsSelected(NewsArticle article) {
        openNewsArticle(article);
    }

    @Override
    public void onNewsSelected(List<NewsArticle> articles, int selectedIndex) {
        openNewsArticles(articles, selectedIndex);
    }

    @Override
    public void onNewsPreviewRequested(NewsArticle article) {
        openNewsArticlePreview(article);
    }

    private void showNewsList() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, NewsArticleListFragment.newInstance())
                .commit();
    }

    private void openNewsArticle(NewsArticle article) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content, NewsArticleDetailFragment.newInstance(article))
                .addToBackStack(NewsArticleDetailFragment.class.getName())
                .commit();
    }

    private void openNewsArticles(List<NewsArticle> articles, int selectedIndex) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content, NewsArticlePagerFragment.newInstance((ArrayList<NewsArticle>) articles, selectedIndex))
                .addToBackStack(NewsArticlePagerFragment.class.getName())
                .commit();
    }

    private void openNewsArticlePreview(NewsArticle article) {
        NewsArticlePreviewFragment
                .newInstance(article)
                .show(
                        getSupportFragmentManager(),
                        NewsArticlePreviewFragment.class.getName()
                );
    }

    @Override
    public void onArticleShareRequested(NewsArticle article) {
        Utils.shareText(this, article.getWebUrl());
    }

    @Override
    public void onArticleOpenRequested(NewsArticle article) {
        openNewsArticle(article);
    }
}
