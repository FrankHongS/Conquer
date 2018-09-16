package com.hon.conquer.ui;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.hon.conquer.NavigationController;
import com.hon.conquer.R;
import com.hon.conquer.ui.news.NewsFragment;
import com.hon.conquer.util.AnimUtil;

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
                item -> false //todo add item selected listener
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
        if(mSnackbar==null){
            mSnackbar=Snackbar.make(mNavigationView,R.string.exit_app,Snackbar.LENGTH_LONG)
                    .setAction(R.string.confirm, v->finish());
        }

        hideBottomNavigationView();
        mBottomNavigationView.postDelayed(mSnackbar::show,200);

    }
}
