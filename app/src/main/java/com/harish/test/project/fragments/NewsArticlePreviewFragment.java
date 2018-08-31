package com.harish.test.project.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.harish.test.project.R;
import com.harish.test.project.entities.NewsArticle;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsArticlePreviewFragment extends DialogFragment {
    private static final String ARG_NEWS_ARTICLE = "arg_news_article";

    private NewsArticle article;

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.txt_headline)
    TextView txtHeadline;
    @BindView(R.id.txt_snippet)
    TextView txtSnippet;
    @BindView(R.id.btn_readmore)
    View btnReadMore;

    public NewsArticlePreviewFragment() {
        // Required empty public constructor
    }

    public static NewsArticlePreviewFragment newInstance(NewsArticle article) {
        NewsArticlePreviewFragment fragment = new NewsArticlePreviewFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_NEWS_ARTICLE, article);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        //must setBackgroundDrawable(TRANSPARENT) in onActivityCreated()
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
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
                R.layout.fragment_news_article_preview, container, false
        );
        ButterKnife.bind(this, rootView);
        setHasOptionsMenu(false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtHeadline.setText(article.getTitle());
        txtSnippet.setText(article.getSnippet());

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (
                        motionEvent.getAction() == MotionEvent.ACTION_UP ||
                                motionEvent.getAction() == MotionEvent.ACTION_POINTER_UP
                        ) {
                    dismiss();
                    return true;
                }
                return false;
            }
        });

        btnReadMore.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                dismiss();
                mListener.onArticleOpenRequested(article);
                return true;
            }
        });

        btnReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onArticleOpenRequested(article);
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
        void onArticleOpenRequested(NewsArticle article);
    }
}
