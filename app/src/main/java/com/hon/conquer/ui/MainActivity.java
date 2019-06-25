package com.hon.conquer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.hon.conquer.NavigationController;
import com.hon.conquer.R;
import com.hon.conquer.ui.favorites.FavoritesActivity;
import com.hon.conquer.ui.news.NewsFragment;
import com.hon.conquer.util.AnimUtil;
import com.hon.conquer.util.ToastUtil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity{

    private static final String TAG=MainActivity.class.getSimpleName();

    private BottomNavigationView mBottomNavigationView;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private Toolbar mToolbar;
    private FloatingActionButton mFab;

    private Snackbar mSnackbar;

    private NavigationController mNavigationController;
    private AnimUtil mAnimUtil=new AnimUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("onCreate");
        setContentView(R.layout.activity_main);

        initView();

        mNavigationController=new NavigationController(
                this,
                R.id.fl_fragment_container,
                mToolbar,
                mFab,
                "news");

    }

    private void initView() {
        mBottomNavigationView=findViewById(R.id.bottom_navigation_view);
        mDrawerLayout=findViewById(R.id.dl_main);
        mNavigationView=findViewById(R.id.nv_drawer_menu);
        mFab=findViewById(R.id.fab_main);
        mToolbar=findViewById(R.id.toolbar);

        mBottomNavigationView.setOnNavigationItemSelectedListener(
            item -> {
                switch (item.getItemId()){
                    case R.id.nav_news:
                        if(!clickBottomButtonTwice())
                            mNavigationController.navigationTo("news");
                        return true;
                    case R.id.nav_video:
                        mNavigationController.navigationTo("video");
                        return true;
                    case R.id.nav_music:
                        mNavigationController.navigationTo("music");
                        return true;
                    case R.id.nav_message:
                        mNavigationController.navigationTo("message");
                        return true;
                    default:
                        break;
                }

                return false;
            }
        );

        initDrawer();
    }

    private void initDrawer() {
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()){
                            case R.id.drawer_news_favorite:
                                ToastUtil.showToast("favorites :(");
                                Intent intent=new Intent(MainActivity.this, FavoritesActivity.class);
                                startActivity(intent);
                                break;
                            default:
                                break;
                        }

                        return true;
                    }
                }
        );
        mNavigationView.inflateHeaderView(R.layout.drawer_header_layout);
        ActionBarDrawerToggle toggle =
                    new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open,
                            R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    public void hideBottomNavigationView(){
        mAnimUtil.hideBottomNavigationView(mBottomNavigationView,200);
    }
    public void showBottomNavigationView(){
        if(mSnackbar!=null&&mSnackbar.isShown()){
            mSnackbar.dismiss();
            mBottomNavigationView.postDelayed(()->mAnimUtil.showBottomNavigationView(mBottomNavigationView,200),
                    600);
        }else{
            mAnimUtil.showBottomNavigationView(mBottomNavigationView,200);
        }
    }

    private boolean clickBottomButtonTwice(){
        if(R.id.nav_news==mBottomNavigationView.getSelectedItemId()){
            NewsFragment newsFragment= (NewsFragment) getSupportFragmentManager().findFragmentByTag("NewsFragment");
            newsFragment.smoothScrollToFirst();
            return true;
        }
        return false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Timber.d("onRestart");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Timber.d("onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Timber.d("onRestoreInstanceState");
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        if(mSnackbar==null){
            mSnackbar=Snackbar.make(mNavigationView,R.string.exit_app,Snackbar.LENGTH_LONG)
                    .setAction(R.string.confirm, v->finish());
        }

        hideBottomNavigationView();
        mBottomNavigationView.postDelayed(mSnackbar::show,200);

    }
}
