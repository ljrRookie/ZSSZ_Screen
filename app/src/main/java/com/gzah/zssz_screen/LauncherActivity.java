package com.gzah.zssz_screen;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gzah.zssz_screen.bean.BannerBean;
import com.gzah.zssz_screen.network.RequestCenter;
import com.gzah.zssz_screen.utils.GlideImageLoader;
import com.gzah.zssz_screen.utils.QRCode;
import com.gzah.zssz_screen.utils.SPUtil;
import com.ljr.delegate_sdk.okhttp.listener.DisposeDataListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.gzah.zssz_screen.application.MyApplication.mContext;


public class LauncherActivity extends BaseActivity {

    @BindView(R.id.iv_pic)
    ImageView mIvPic;
    @BindView(R.id.btn_main)
    RelativeLayout mBtnMain;
    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.tv_title)
    TextView mTitle;
    @BindView(R.id.iv_qrcode)
    ImageView mIvQrcode;
    private TranslateAnimation mShowAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setContentView(R.layout.activity_launcher);
        ButterKnife.bind(this);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        stopTimer();
        String mMachine_num = (String) SPUtil.get(mContext, "MACHINE_NUM", "");
        if (!mMachine_num.isEmpty()) {
            initView();
            String Machine_num = (String) SPUtil.get(mContext, "MACHINE_NUM", "");
            Bitmap image = QRCode.Create2DCode(Common.ScanUrl+Machine_num, 300, 300);
            mIvQrcode.setImageBitmap(image);
            initData();
        } else {
            Intent intent = new Intent(this, MachineActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(0, 0);
        }

    }

    private void initData() {
        RequestCenter.BigScreenAdvertisement(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                BannerBean bannerBean = (BannerBean) responseObj;
                List<BannerBean.DataBean> data = bannerBean.getData();
                if (data != null && data.size() > 0) {
                    initBanner(data);
                    mTitle.setVisibility(View.VISIBLE);
                }else{
                    mTitle.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Object responseObj) {

            }
        });
    }

    private void initBanner(List<BannerBean.DataBean> data) {
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        List<String> images = new ArrayList<>();
        final List<String> titles = new ArrayList<>();
        mBanner.setImageLoader(new GlideImageLoader());
        int bannerSize = data.size();
        for (int i = 0; i < bannerSize; i++) {
            BannerBean.DataBean dataBean = data.get(i);
            if (dataBean == null) {
                return;
            }
            String image = dataBean.getImage();
            String title = dataBean.getTitle() + "\n" + dataBean.getContent();
            if (!image.contains(",")) {
                images.add(Common.BaseUrl + image);
                titles.add(title);
            } else {
                String str2 = image.replace(" ", "");//去掉所用空格
                List<String> list = Arrays.asList(str2.split(","));
                for (int j = 0; j < list.size(); j++) {
                    String s = list.get(j);
                    images.add(Common.BaseUrl + s);
                    titles.add(title);
                }

            }

        }
        mBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //  Log.e("page", "onPageScrolled===" + position + "=====" + positionOffset + "====" + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                //Log.e("page", "onPageSelected===" + position + "=====");
                mTitle.setText(titles.get(position));

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // Log.e("page", "onPageScrollStateChanged===" + state + "=====");
            }
        });
        // mBanner.setBannerTitles(titles);
        mBanner.setImages(images);
        mBanner.start();
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        });

    }

    private void initView() {
        mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -0.5f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setRepeatCount(Animation.INFINITE);
        mShowAction.setRepeatMode(Animation.REVERSE);
        mShowAction.setDuration(800);
        mIvPic.setAnimation(mShowAction);
    }

    @OnClick(R.id.btn_main)
    public void onViewClicked() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }
}
