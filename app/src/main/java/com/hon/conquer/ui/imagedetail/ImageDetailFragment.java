package com.hon.conquer.ui.imagedetail;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.hon.conquer.ConquerExecutors;
import com.hon.conquer.R;
import com.hon.conquer.ui.common.popup.Popup;
import com.hon.conquer.ui.common.popup.PopupItem;
import com.hon.conquer.ui.imagedetail.adapter.PhotoViewAdapter;
import com.hon.conquer.util.FileUtil;
import com.hon.conquer.util.Util;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by Frank on 2018/5/27.
 * E-mail:frank_hon@foxmail.com
 */

public class ImageDetailFragment extends Fragment {

    public static final String IMAGE_POSITION = "image_position";
    public static final String IMAGE_LIST = "image_list";

    private ViewPager mViewPager;
    private TextView mIndicatorText;

    private ImageButton mBackButton;
    private ImageButton mMoreButton;

    private PagerAdapter mPagerAdapter;

    private List<String> mImageUrlList;
    private int mPosition;

    private int mPhotoCount = 0;

    public ImageDetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null)
            mImageUrlList = bundle.getStringArrayList(IMAGE_LIST);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_detail, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mViewPager = view.findViewById(R.id.vp_photo);
        mIndicatorText = view.findViewById(R.id.tv_photo_indicator);

        mBackButton = view.findViewById(R.id.ib_back);
        mMoreButton = view.findViewById(R.id.ib_more);

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mIndicatorText.setText((position + 1) + "/" + mPhotoCount);
            }
        });

        mBackButton.setOnClickListener(v -> getActivity().finish());

        mMoreButton.setOnClickListener(this::showPopupWindow);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPosition = getActivity().getIntent().getIntExtra(IMAGE_POSITION, 2);

        if (mImageUrlList != null && mImageUrlList.size() > 0) {
            mPagerAdapter = new PhotoViewAdapter(mImageUrlList);
            mViewPager.setAdapter(mPagerAdapter);
            mViewPager.setCurrentItem(mPosition);

            mPhotoCount = mImageUrlList.size();
            mIndicatorText.setText((mPosition + 1) + "/" + mPhotoCount);
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void showPopupWindow(View view) {
        List<PopupItem> list = new ArrayList<>();
        list.add(new PopupItem("save", R.drawable.ic_file_download_24dp));

        Popup popup = new Popup(getContext(), view);
        popup.setWidth(Util.dip2px(150));
        popup.setAdapter(new Popup.PopupAdapter(getContext(), list,
                (v, position) -> {
                    switch (position) {
                        case 0:
                            downloadImageFromNetwork();
                            break;
                        default:
                            break;
                    }
                    popup.dismiss();

                }));
        popup.show();
    }

    private void downloadImageFromNetwork() {
        String imageUrl = mImageUrlList.get(mViewPager.getCurrentItem());
        Glide.with(this)
                .load(imageUrl)
                .downloadOnly(new SimpleTarget<File>() {
                    @Override
                    public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                        savePicturesToGallery(resource);
                    }
                });
    }

    @SuppressWarnings("checkResult")
    private void savePicturesToGallery(File resource) {
        new RxPermissions(this)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(
                        granted -> {
                            if (granted) {
                                ConquerExecutors.getInstance().getIoExecutors()
                                        .execute(
                                                () -> FileUtil.savePicturesToGallery(resource)

                                        );


                            }
                        }
                );
    }
}
