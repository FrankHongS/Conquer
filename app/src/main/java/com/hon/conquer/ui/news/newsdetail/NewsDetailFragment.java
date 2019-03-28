package com.hon.conquer.ui.news.newsdetail;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hon.conquer.Conquer;
import com.hon.conquer.R;
import com.hon.conquer.db.ConquerDatabase;
import com.hon.conquer.db.FavoriteNews;
import com.hon.conquer.ui.common.MyJSBridge;
import com.hon.conquer.util.ToastUtil;
import com.hon.conquer.util.WebUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Frank on 2018/3/8.
 * E-mail:frank_hon@foxmail.com
 */

public class NewsDetailFragment extends Fragment implements NewsDetailContract.View {

    private static final String TAG = NewsDetailFragment.class.getSimpleName();

    private Toolbar mToolbar;
    private ImageView mAvatar;
    private TextView mTitle;
    private TextView mAuthor;
    private TextView mBio;
    private WebView mNewsDetail;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private int mArticleId;
    private String mArticleTitle;
    private boolean mIsNightMode;

    private MyJSBridge mMyJSBridge;

    private NewsDetailContract.Presenter mNewsPresenter;

    public NewsDetailFragment() {
    }

    @Override
    public void setPresenter(NewsDetailContract.Presenter presenter) {
        this.mNewsPresenter = presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mArticleId = bundle.getInt(NewsDetailActivity.KEY_ARTICLE_ID);
            mArticleTitle = bundle.getString(NewsDetailActivity.KEY_ARTICLE_TITLE);
        }

        mMyJSBridge = new MyJSBridge(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_detail, container, false);
        initViews(view);
        mNewsPresenter.getNewsDetail(mArticleId);
        return view;
    }

    private void initViews(View view) {
        mToolbar = view.findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.bottom_news);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(v -> getActivity().finish());

        mAvatar = view.findViewById(R.id.iv_avatar);
//        showAvatar();
        mTitle = view.findViewById(R.id.tv_title);
        mTitle.setText(mArticleTitle);
        mAuthor = view.findViewById(R.id.tv_author);
        mBio = view.findViewById(R.id.tv_bio);
//        mSwipeRefreshLayout=view.findViewById(R.id.srl_web);
//        mSwipeRefreshLayout.setOnRefreshListener(this::getNewsDetail);

        mNewsDetail = view.findViewById(R.id.wv_news_detail);
        setWebView();
    }

    @Override
    public void showContent(String body) {
        String data = WebUtil.appendToHTML(body);

        mNewsDetail.loadDataWithBaseURL("x-data://base", data, "text/html", "utf-8", null);

    }

    private void setWebView() {
        mNewsDetail.setScrollbarFadingEnabled(false);
        mNewsDetail.getSettings().setJavaScriptEnabled(true);
        mNewsDetail.getSettings().setBuiltInZoomControls(false);
        mNewsDetail.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mNewsDetail.getSettings().setDomStorageEnabled(true);
        mNewsDetail.getSettings().setAppCacheEnabled(false);
        mNewsDetail.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)));
                } catch (ActivityNotFoundException e) {
                    ToastUtil.showToast("no broswer in your device :)");
                }
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.d(TAG, "onPageStarted: ");
//                mSwipeRefreshLayout.setRefreshing(true);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d(TAG, "onPageFinished: ");
                showTitle();
//                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
//                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        mNewsDetail.addJavascriptInterface(mMyJSBridge, "android");
    }

    private void showTitle() {
        showAvatar(mMyJSBridge.getAvatarUrl());
        mAuthor.setText(mMyJSBridge.getAuthor());
        mBio.setText(mMyJSBridge.getBio());
    }

    private void showAvatar(String url) {
        Glide.with(Conquer.sConquer)
                .load(url)
                .apply(
                        new RequestOptions()
                    .transform(new CropCircleTransformation(getActivity()))
                )
                .into(mAvatar);
    }

    public boolean checkIfFavorites() {
        List<FavoriteNews> news = ConquerDatabase.getInstance().newsFavoritesDao().loadNewsFavorites(mArticleId);
        return news != null && !news.isEmpty();
    }

}
