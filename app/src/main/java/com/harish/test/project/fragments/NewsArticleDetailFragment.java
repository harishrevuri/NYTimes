package com.harish.test.project.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.harish.test.project.R;
import com.harish.test.project.entities.NewsArticle;
import com.harish.test.project.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsArticleDetailFragment extends Fragment {
    private static final String ARG_NEWS_ARTICLE = "arg_news_article";

    private NewsArticle article;

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.txt_headline)
    TextView txtHeadline;
    @BindView(R.id.txt_date)
    TextView txtDate;
    @BindView(R.id.txt_snippet)
    TextView txtSnippet;
    @BindView(R.id.btn_share)
    View btnShare;

    public NewsArticleDetailFragment() {
        // Required empty public constructor
    }

    public static NewsArticleDetailFragment newInstance(NewsArticle article) {
        NewsArticleDetailFragment fragment = new NewsArticleDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_NEWS_ARTICLE, article);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            article = (NewsArticle) getArguments().getSerializable(ARG_NEWS_ARTICLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(
                R.layout.fragment_news_article_detail, container, false
        );
        ButterKnife.bind(this, rootView);
        setHasOptionsMenu(false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtHeadline.setText(article.getTitle());
        txtDate.setText(Utils.formatFullDateString(article.getPubDate()));
        txtSnippet.setText(article.getSnippet());
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onArticleShareRequested(article);
            }
        });
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onArticleShareRequested(NewsArticle article);
    }
}
