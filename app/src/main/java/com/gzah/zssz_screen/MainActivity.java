package com.gzah.zssz_screen;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gzah.zssz_screen.ui.fragment.RechargeFragment;
import com.gzah.zssz_screen.ui.fragment.ShopFragment;
import com.gzah.zssz_screen.ui.fragment.WaterFragment;
import com.gzah.zssz_screen.utils.QRCode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @BindView(R.id.iv_btn_a)
    ImageView mIvBtnA;
    @BindView(R.id.tv_btn_a)
    TextView mTvBtnA;
    @BindView(R.id.btn_a)
    RelativeLayout mBtnA;
    @BindView(R.id.iv_btn_b)
    ImageView mIvBtnB;
    @BindView(R.id.tv_btn_b)
    TextView mTvBtnB;
    @BindView(R.id.btn_b)
    RelativeLayout mBtnB;
    @BindView(R.id.iv_btn_c)
    ImageView mIvBtnC;
    @BindView(R.id.tv_btn_c)
    TextView mTvBtnC;
    @BindView(R.id.btn_c)
    RelativeLayout mBtnC;
    @BindView(R.id.iv_btn_d)
    ImageView mIvBtnD;
    @BindView(R.id.tv_btn_d)
    TextView mTvBtnD;
    @BindView(R.id.btn_d)
    RelativeLayout mBtnD;
    @BindView(R.id.iv_btn_e)
    ImageView mIvBtnE;
    @BindView(R.id.tv_btn_e)
    TextView mTvBtnE;
    @BindView(R.id.btn_e)
    RelativeLayout mBtnE;
    @BindView(R.id.fl)
    FrameLayout mFl;
    @BindView(R.id.btn_back)
    RelativeLayout mBtnBack;
    @BindView(R.id.ll_btn)
    LinearLayout mLlBtn;
    @BindView(R.id.iv_qrcode)
    ImageView mIvQrcode;
    private List<Fragment> mFragments;
    private int mPosition;//fragmentID
    private Fragment mTempFragment;//当前Fragment
    private AlphaAnimation mShowAction;
    private AlphaAnimation mHiddenAction;
    private String mMachineId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        initAnim();
        mBtnB.setVisibility(View.GONE);
        mLlBtn.setAnimation(mShowAction);
        mBtnBack.setAnimation(mShowAction);
        Bitmap image = QRCode.Create2DCode(Common.ScanUrl+"index=32", 300, 300);
        mIvQrcode.setImageBitmap(image);
        initFragments(savedInstanceState);
        startTimer();

    }


    private void initAnim() {
        mShowAction = new AlphaAnimation(0.0f, 1f);
        mShowAction.setDuration(500);
        mHiddenAction = new AlphaAnimation(1f, 0.0f);
        mHiddenAction.setDuration(300);

    }

    private void initFragments(Bundle savedInstanceState) {
        mFragments = new ArrayList<>();

        mFragments.add(WaterFragment.create(Common.water_a));
    //    mFragments.add(WaterFragment.create(Common.water_b));
        mFragments.add(WaterFragment.create(Common.water_c));
        mFragments.add(new ShopFragment());
        mFragments.add(new RechargeFragment());
       // mFragments.add(new MapFragment());



    }

    @OnClick({R.id.btn_a, R.id.btn_c, R.id.btn_d, R.id.btn_e/*,R.id.btn_b*/})
    public void onViewClicked(View view) {
        initView();
        switch (view.getId()) {
            case R.id.btn_a:
                //清清泉饮水
                mIvBtnA.setImageResource(R.mipmap.btn_a_press);
                mTvBtnA.setTextSize(35);
                mPosition = 0;
                break;

            case R.id.btn_c:
                //品牌水
                mIvBtnC.setImageResource(R.mipmap.btn_c_press);
                mTvBtnC.setTextSize(35);
                mPosition = 1;
                break;
            case R.id.btn_d:
                //点点物
                mIvBtnD.setImageResource(R.mipmap.btn_d_press);
                mTvBtnD.setTextSize(35);
                mPosition = 2;
                break;
            case R.id.btn_e:
                //速充宝
                mIvBtnE.setImageResource(R.mipmap.btn_e_press);
                mTvBtnE.setTextSize(35);
                mPosition = 3;
                break;
           /*    case R.id.btn_b:
                //地图
                mIvBtnB.setImageResource(R.mipmap.btn_b_press);
                mTvBtnB.setTextSize(35);
                mPosition = 4;
                break;*/
        }
        Fragment baseFragment = getFragment(mPosition);
        swithFragment(mTempFragment, baseFragment);
    }

    private void swithFragment(Fragment fromFragment, Fragment nextFragment) {
        if (fromFragment != nextFragment) {
            mTempFragment = nextFragment;
            if (nextFragment != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                //判断nextFragment是否添加
                if (!nextFragment.isAdded()) {
                    if (fromFragment != null) {
                        //隐藏当前Fragment
                        ft.hide(fromFragment);
                    }
                    ft.add(R.id.fl, nextFragment).commit();
                } else {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        ft.hide(fromFragment);
                    }
                    ft.show(nextFragment).commit();
                }
            }
        }
    }

    private Fragment getFragment(int position) {
        if (mFragments != null && mFragments.size() > 0) {
            Fragment baseFragment = mFragments.get(position);
            return baseFragment;
        }
        return null;
    }

    private void initView() {
        mIvBtnA.setImageResource(R.mipmap.btn_a);
        mTvBtnA.setTextSize(30);
        mIvBtnB.setImageResource(R.mipmap.btn_b);
        mTvBtnB.setTextSize(30);
        mIvBtnC.setImageResource(R.mipmap.btn_c);
        mTvBtnC.setTextSize(30);
        mIvBtnD.setImageResource(R.mipmap.btn_d);
        mTvBtnD.setTextSize(30);
        mIvBtnE.setImageResource(R.mipmap.btn_e);
        mTvBtnE.setTextSize(30);
    }

    @OnClick(R.id.btn_back)
    public void onViewClicked() {

        Intent intent = new Intent(MainActivity.this, LauncherActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.action_alpha_in, R.anim.action_alpha_out);
    }
}
