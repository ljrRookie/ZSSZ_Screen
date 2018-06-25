package com.gzah.zssz_screen.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.gzah.zssz_screen.Common;
import com.gzah.zssz_screen.R;
import com.gzah.zssz_screen.bean.WaterBean2Json;
import com.gzah.zssz_screen.listener.ITimerListener;
import com.gzah.zssz_screen.utils.BaseTimerTask;
import com.gzah.zssz_screen.utils.QRCode;
import com.gzah.zssz_screen.utils.SPUtil;
import com.vondear.rxtools.RxLogTool;
import com.vondear.rxtools.view.dialog.RxDialog;

import java.text.MessageFormat;
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.gzah.zssz_screen.application.MyApplication.mContext;


/**
 * Created by 林佳荣 on 2018/4/17.
 * Github：https://github.com/ljrRookie
 * Function ：
 */

public class WaterFragment extends Fragment implements ITimerListener {

    Unbinder unbinder;
    @BindView(R.id.btn_buy)
    SuperTextView mBtnBuy;
    @BindView(R.id.relativeLayout)
    RelativeLayout mRelativeLayout;
    @BindView(R.id.rl_btn_cool)
    RelativeLayout mRlBtnCool;
    @BindView(R.id.rl_select_hot)
    RelativeLayout mRlSelectHot;
    @BindView(R.id.rl_btn_hot)
    RelativeLayout mRlBtnHot;
    @BindView(R.id.rl_select_cool)
    RelativeLayout mRlSelectCool;
    private TranslateAnimation mShowAction;
    private TranslateAnimation mHiddenAction;
    private int mWater_id;
    private Timer mTimer;
    private TextView mTvTime;
    private int mCount = 60;
    private RxDialog mDialog;
    /**
     * state : 1 = 热水   =冷水
     */
    private int state = 1;
    private String mMachine_num;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_home, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        mDialog = new RxDialog(getContext());
        mMachine_num = (String) SPUtil.get(mContext, "MACHINE_NUM", "");

    }

    private void initView() {
        mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -0.2f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(200);
        mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f);
        mHiddenAction.setDuration(500);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.btn_buy, R.id.rl_btn_cool, R.id.rl_btn_hot})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_buy:
                String type = "";
                if (mTimer != null) {
                    mTimer.cancel();
                    mTimer = null;
                }
                mCount = 60;
                mDialog.getLayoutParams().gravity = Gravity.CENTER;
                View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_pay, null);
                ImageView QR_CODE = (ImageView) dialogView.findViewById(R.id.iv_qrcode);
                mTvTime = (TextView) dialogView.findViewById(R.id.tv_time);
                mTvTime.setText("60s");
                if (mWater_id == Common.water_a) {
                    //清清泉
                    if (state == 1) {
                        //热水
                        type = "11";
                    } else {
                        //冷水
                        type = "12";
                    }
                } else {
                    //品牌水
                    if (state == 1) {
                        //热水
                        type = "21";
                    } else {
                        //冷水
                        type = "22";
                    }
                }
                String json = Common.ScanUrl + "{" +
                        "  \"code\": \"1\"," +
                        " \"machineid\": \"" + mMachine_num + "\"," +
                        " \"type\": \"" + type + "\"}";
                RxLogTool.e("json", json);

                Bitmap image = QRCode.Create2DCode(json, 300, 300);

                QR_CODE.setImageBitmap(image);
                initTimer();
                mDialog.setContentView(dialogView);
                mDialog.show();
                break;
            case R.id.rl_btn_cool:
                state = 2;
                initView();
                mRlSelectCool.setAnimation(mShowAction);
                mRlSelectCool.setVisibility(View.VISIBLE);
                mRlSelectHot.setVisibility(View.GONE);
                break;
            case R.id.rl_btn_hot:
                state = 1;
                initView();
                mRlSelectHot.setAnimation(mShowAction);
                mRlSelectHot.setVisibility(View.VISIBLE);
                mRlSelectCool.setVisibility(View.GONE);
                break;
        }
    }

    private void initTimer() {
        mTimer = new Timer();
        final BaseTimerTask task = new BaseTimerTask(this);
        mTimer.schedule(task, 0, 1000);
    }

    public static WaterFragment create(int position) {
        WaterFragment waterFragment = new WaterFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("Water_Id", position);
        waterFragment.setArguments(bundle);
        return waterFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mWater_id = arguments.getInt("Water_Id");
        }

    }

    @Override
    public void onTimer() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mTvTime != null) {
                    mTvTime.setText(MessageFormat.format("{0}s", mCount));
                    mCount--;
                    if (mCount < 0) {
                        mDialog.dismiss();
                        if (mTimer != null) {
                            mTimer.cancel();
                            mTimer = null;
                        }
                    }
                }
            }
        });

    }
}
