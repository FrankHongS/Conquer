package com.hon.conquer;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.SwipeDismissBehavior;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hon.conquer.ui.news.NewsFragment;
import com.hon.conquer.util.AnimUtil;
import com.hon.conquer.util.ToastUtil;

import java.util.List;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements DrawerLayout.DrawerListener{

    private static final String TAG=MainActivity.class.getSimpleName();

    private BottomNavigationView mBottomNavigationView;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private Snackbar mSnackbar;

    private NavigationController mNavigationController;
    private AnimUtil mAnimUtil;

    private boolean mBindDrawerLayoutWithFragments=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNavigationController=new NavigationController(this);
        mNavigationController.initFragments();
        mAnimUtil=new AnimUtil();
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!mBindDrawerLayoutWithFragments)
            bindDrawerLayoutWithFragments();
    }

    private void bindDrawerLayoutWithFragments() {
        mBindDrawerLayoutWithFragments=true;
        List<BaseFragment> fragments=mNavigationController.getFragments();
        for(BaseFragment fragment:fragments)
            fragment.bindToDrawerLayout(mDrawerLayout);
    }

    private void initView() {
        mBottomNavigationView=findViewById(R.id.bottom_navigation_view);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
        });
        mNavigationController.navigationTo("news");

        mDrawerLayout=findViewById(R.id.dl_main);
        mNavigationView=findViewById(R.id.nv_drawer_menu);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return false;
            }
        });
        mNavigationView.inflateHeaderView(R.layout.drawer_header_layout);
    }

    private void initDrawer() {

//        addToggle(mNavigationController.getCurrentFragment().getToolbar());
    }

//    private void addToggle(Toolbar toolbar){
//
//        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
//        ActionBarDrawerToggle toggle =
//                new ActionBarDrawerToggle(this, mDrawerLayout,toolbar, R.string.navigation_drawer_open,
//                        R.string.navigation_drawer_close);
//        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ToastUtil.showToast("hello");
//            }
//        });
//        toggle.setDrawerIndicatorEnabled(false);
//        mDrawerLayout.addDrawerListener(toggle);
//
//        toggle.syncState();
//    }

    private void setBehavior() {
        CoordinatorLayout.LayoutParams params= (CoordinatorLayout.LayoutParams) mBottomNavigationView.getLayoutParams();
        params.setBehavior(new SwipeDismissBehavior<BottomNavigationView>());
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
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    @Override
    public void onBackPressed() {
        mSnackbar=Snackbar.make(mBottomNavigationView,R.string.exit_app,Snackbar.LENGTH_LONG)
                .setAction(R.string.confirm, v->finish());
        hideBottomNavigationView();
        mBottomNavigationView.postDelayed(mSnackbar::show,200);

    }
}
