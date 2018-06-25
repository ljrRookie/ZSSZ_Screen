package com.gzah.zssz_screen.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.gzah.zssz_screen.Common;
import com.gzah.zssz_screen.R;
import com.gzah.zssz_screen.adapter.RechargeAdapter;
import com.gzah.zssz_screen.listener.ITimerListener;
import com.gzah.zssz_screen.utils.BaseTimerTask;
import com.gzah.zssz_screen.utils.QRCode;
import com.gzah.zssz_screen.utils.SPUtil;
import com.vondear.rxtools.view.RxToast;
import com.vondear.rxtools.view.dialog.RxDialog;
import com.vondear.rxtools.view.likeview.tools.ei.RxEase;

import java.text.MessageFormat;
import java.util.ArrayList;
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

public class RechargeFragment extends Fragment  implements ITimerListener {
    @BindView(R.id.rv_item)
    RecyclerView mRvItem;
    @BindView(R.id.btn_commit)
    SuperButton mBtnCommit;
    Unbinder unbinder;
    private Timer mTimer;
    private int mCount;
    private TextView mTvTime;
    private RxDialog mDialog;
    private String mMachine_num;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_recharge, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        mDialog = new RxDialog(getContext());
        mMachine_num = (String) SPUtil.get(mContext, "MACHINE_NUM", "");
    }

    private void initData() {
        ArrayList<String> item = new ArrayList<>();
        item.add("1.充电期间不能退出此页面。");
        item.add("2.请勿离开手机，以免手机被盗。");
        item.add("3.充电一次 扣除一次次数。");
        item.add("4.购买商品、充值、发布广告都可以获取充电次数。");
        RechargeAdapter rechargeAdapter = new RechargeAdapter(R.layout.item_recharge_text, item);
        rechargeAdapter.openLoadAnimation();
        mRvItem.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvItem.setAdapter(rechargeAdapter);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_commit)
    public void onViewClicked() {
        String json =Common.ScanUrl + "{" +
                "  \"code\": \"2\"," +
                " \"machineid\": \"" + mMachine_num + "\"";
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

        Bitmap image = QRCode.Create2DCode(json, 300, 300);

        QR_CODE.setImageBitmap(image);
        initTimer();
        mDialog.setContentView(dialogView);
        mDialog.show();
    }
    private void initTimer() {
        mTimer = new Timer();
        final BaseTimerTask task = new BaseTimerTask(this);
        mTimer.schedule(task, 0, 1000);
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
