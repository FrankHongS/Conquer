package com.hon.conquer.ui.news.newsdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;

import com.hon.conquer.R;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by Frank on 2018/3/8.
 * E-mail:frank_hon@foxmail.com
 */

public class NewsDetailActivity extends SwipeBackActivity{

    public static final String KEY_ARTICLE_ID = "KEY_ARTICLE_ID";
    public static final String KEY_ARTICLE_TITLE = "KEY_ARTICLE_TITLE";

    private NewsDetailFragment mNewsDetailFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_in_left);
        setContentView(R.layout.activity_news_detail);

        getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_RIGHT);

        if(savedInstanceState!=null)
            mNewsDetailFragment= (NewsDetailFragment) getSupportFragmentManager().
                    getFragment(savedInstanceState,
                            NewsDetailFragment.class.getSimpleName());
        else
            mNewsDetailFragment=new NewsDetailFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,mNewsDetailFragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_detail,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_out_right,R.anim.slide_out_right);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mNewsDetailFragment.isAdded()){
            getSupportFragmentManager().putFragment(outState,
                    NewsDetailFragment.class.getSimpleName(),mNewsDetailFragment);
        }
    }
}
