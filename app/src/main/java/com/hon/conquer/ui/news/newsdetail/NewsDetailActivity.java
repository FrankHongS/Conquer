package com.hon.conquer.ui.news.newsdetail;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.hon.conquer.ConquerExecutors;
import com.hon.conquer.R;
import com.hon.conquer.util.ToastUtil;
import com.hon.conquer.vo.event.NewsFavoritesEvent;

import org.greenrobot.eventbus.EventBus;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import timber.log.Timber;

/**
 * Created by Frank on 2018/3/8.
 * E-mail:frank_hon@foxmail.com
 */

public class NewsDetailActivity extends SwipeBackActivity{

    public static final String KEY_ARTICLE_ID = "KEY_ARTICLE_ID";
    public static final String KEY_ARTICLE_TITLE = "KEY_ARTICLE_TITLE";

    private static final String TAG = NewsDetailActivity.class.getSimpleName();

    private NewsDetailFragment mNewsDetailFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_in_left);
        setContentView(R.layout.activity_news_detail);

        getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_RIGHT);

        if(savedInstanceState!=null){
            mNewsDetailFragment= (NewsDetailFragment) getSupportFragmentManager().
                    getFragment(savedInstanceState,
                            NewsDetailFragment.class.getSimpleName());
        }else{
            mNewsDetailFragment=new NewsDetailFragment();
        }

        Bundle bundle=new Bundle();
        bundle.putInt(KEY_ARTICLE_ID, getIntent().getIntExtra(NewsDetailActivity.KEY_ARTICLE_ID, -1));
        bundle.putString(KEY_ARTICLE_TITLE, getIntent().getStringExtra(NewsDetailActivity.KEY_ARTICLE_TITLE));

        mNewsDetailFragment.setArguments(bundle);

        new NewsDetailPresenter(mNewsDetailFragment.getLifecycle(),mNewsDetailFragment);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,mNewsDetailFragment)
                .commit();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_out_right,R.anim.slide_out_right);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_detail,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.news_favorite:
                ConquerExecutors.getInstance().getIoExecutors().execute(
                        ()->{
                            boolean ifFavorites=mNewsDetailFragment.checkIfFavorites();
                            ConquerExecutors.getInstance().getMainExecutor()
                                    .execute(
                                            ()->{
                                                if(ifFavorites){
                                                    ToastUtil.showToast(getResources().getString(R.string.favorites_existing));
                                                }else {
                                                    ToastUtil.showToast(getResources().getString(R.string.add_to_favorites));
                                                    EventBus.getDefault().post(new NewsFavoritesEvent());
                                                }
                                            }
                                    );
                        }
                );

                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
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
