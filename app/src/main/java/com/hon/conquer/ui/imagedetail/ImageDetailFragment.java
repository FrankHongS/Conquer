package com.hon.conquer.ui.imagedetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hon.conquer.R;
import com.hon.conquer.util.ToastUtil;
import com.hon.conquer.vo.event.ImageDetailEvent;
import com.hon.photopreviewlayout.ImageData;
import com.hon.photopreviewlayout.PhotoPreviewLayout;
import com.hon.photopreviewlayout.PhotoViewPagerAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by Frank on 2018/5/27.
 * E-mail:frank_hon@foxmail.com
 */

public class ImageDetailFragment extends Fragment{

    private PhotoPreviewLayout mPhotoPreviewLayout;
    private PagerAdapter mPagerAdapter;

    private List<String> mImgUrl;

    private int mPosition;

    public ImageDetailFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_image_detail,container,false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPosition=getActivity().getIntent().getIntExtra("image_position",2);
        ToastUtil.showToast(mPosition+"");
        mPhotoPreviewLayout.setViewPagerCurrentItem(mPosition);
    }

    private void initView(View view){
        mPhotoPreviewLayout=view.findViewById(R.id.ppl_image);
        if(mImgUrl!=null&&mImgUrl.size()>0){
            ImageData<String> imageData=new ImageData<>(ImageData.URL,mImgUrl);
            mPagerAdapter=new PhotoViewPagerAdapter<>(getActivity(),imageData);
            mPhotoPreviewLayout.setViewPagerAdapter(mPagerAdapter);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onImageDetailEvent(ImageDetailEvent event){
        mImgUrl=event.getImageUrl();
        Log.d("onImageDetailEvent", "onImageDetailEvent: "+mImgUrl);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
