package com.hon.conquer.ui.favorites;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hon.conquer.ConquerExecutors;
import com.hon.conquer.R;
import com.hon.conquer.db.ConquerDatabase;
import com.hon.conquer.db.FavoriteNews;
import com.hon.conquer.ui.common.NewsItemDivider;
import com.hon.conquer.ui.news.newsdetail.NewsDetailActivity;
import com.hon.conquer.util.Util;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Frank_Hon on 9/28/2018.
 * E-mail: v-shhong@microsoft.com
 */
public class NewsFavoritesFragment extends Fragment {

    private Toolbar mToolbar;
    private RecyclerView mNewsListView;

    private NewsFavoritesAdapter mNewsFavoritesAdapter;
    private List<FavoriteNews> mFavoriteNewsList=new ArrayList<>();

    private ConquerExecutors mExecutors=ConquerExecutors.getInstance();

    public NewsFavoritesFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_news_favorites,container,false);

        initView(view);

        fetchData();

        return view;
    }

    private void initView(View view) {
        mToolbar = view.findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.news_favorite);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(v -> getActivity().finish());

        mNewsListView=view.findViewById(R.id.rv_news_favorites);
        mNewsFavoritesAdapter=new NewsFavoritesAdapter(getActivity(),mFavoriteNewsList);
        mNewsListView.setAdapter(mNewsFavoritesAdapter);
        mNewsListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mNewsListView.addItemDecoration(new NewsItemDivider(Util.getColor(R.color.colorDividerColor)));
        mNewsFavoritesAdapter.setOnItemClickListener(
                position -> {
                    FavoriteNews item=mFavoriteNewsList.get(position);
                    int articleId=item.getId();
                    String articleTitle=item.getTitle();
                    Intent intent=new Intent(getActivity(), NewsDetailActivity.class);
                    intent.putExtra(NewsDetailActivity.KEY_ARTICLE_ID,articleId);
                    intent.putExtra(NewsDetailActivity.KEY_ARTICLE_TITLE,articleTitle);
                    startActivity(intent);
                }
        );
    }

    private void fetchData() {
        mExecutors.getIoExecutors().execute(
                ()->{
                    List<FavoriteNews> news=ConquerDatabase.getInstance().newsFavoritesDao().loadNewsFavorites();

                    mExecutors.getMainExecutor().execute(
                            ()->{
                                mFavoriteNewsList.addAll(news);
                                mNewsFavoritesAdapter.notifyItemRangeInserted(0,news.size());
                            }
                    );

                }
        );

    }
}
