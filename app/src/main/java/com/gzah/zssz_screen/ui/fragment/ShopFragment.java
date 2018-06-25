package com.gzah.zssz_screen.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gzah.zssz_screen.Common;
import com.gzah.zssz_screen.R;
import com.gzah.zssz_screen.adapter.ShopAdapter;
import com.gzah.zssz_screen.adapter.ShopCartPageAdapter;
import com.gzah.zssz_screen.bean.GetCodeBean;
import com.gzah.zssz_screen.bean.MachineGoodBean;
import com.gzah.zssz_screen.listener.ITimerListener;
import com.gzah.zssz_screen.listener.ShopCartInterface;
import com.gzah.zssz_screen.network.RequestCenter;
import com.gzah.zssz_screen.utils.BaseTimerTask;
import com.gzah.zssz_screen.utils.CartStorage;
import com.gzah.zssz_screen.utils.QRCode;
import com.gzah.zssz_screen.utils.SPUtil;
import com.ljr.delegate_sdk.okhttp.exception.OkHttpException;
import com.ljr.delegate_sdk.okhttp.listener.DisposeDataListener;
import com.ljr.delegate_sdk.okhttp.request.RequestParams;
import com.vondear.rxtools.RxDataTool;
import com.vondear.rxtools.RxLogTool;
import com.vondear.rxtools.view.RxToast;
import com.vondear.rxtools.view.dialog.RxDialog;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.WeakHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

import static com.gzah.zssz_screen.application.MyApplication.mContext;

/**
 * Created by 林佳荣 on 2018/4/17.
 * Github：https://github.com/ljrRookie
 * Function ：
 */

public class ShopFragment extends Fragment implements ITimerListener, ShopCartInterface {
    @BindView(R.id.rv_shop)
    RecyclerView mRvShop;
    Unbinder unbinder;
    @BindView(R.id.btn_last)
    ImageButton mBtnLast;
    @BindView(R.id.btn_next)
    ImageButton mBtnNext;
    @BindView(R.id.ll_shop)
    LinearLayout mLlShop;
    @BindView(R.id.tv_last)
    TextView mTvLast;
    @BindView(R.id.rv_page)
    RecyclerView mRvPage;
    @BindView(R.id.tv_next)
    TextView mTvNext;
    @BindView(R.id.ll_page)
    LinearLayout mLlPage;

    @BindView(R.id.btn_buy)
    SuperTextView mBtnBuy;
    @BindView(R.id.iv_cart)
    ImageView mIvCart;
    @BindView(R.id.rv_cart)
    TextView mRvCart;
    @BindView(R.id.empty_shop)
    RelativeLayout mEmptyShop;
    @BindView(R.id.tv_empty)
    TextView mTvEmpty;
    private RxDialog mDialog;
    private Timer mTimer;
    private TextView mTvTime;
    private int mCount = 60;
    private int shopCount = 0;
    private Badge mBadge;

    private CartStorage mCartStorage;
    private List<MachineGoodBean.DataBean> mCartData = null;
    private List<MachineGoodBean.DataBean> mData;
    private ShopAdapter mShopAdapter;
    private int page;
    private int CurrentPage = 1;
    private int itemCount = 15;
    private WeakHashMap<Integer, List<MachineGoodBean.DataBean>> mShopData;
    private ShopCartPageAdapter mShopCartPageAdapter;
    private String mMachineid;
    private String mMachine_num;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_shop, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCartStorage = CartStorage.getInstance();
        mBadge = new QBadgeView(getContext()).bindTarget(mIvCart).setBadgeBackgroundColor(getResources().getColor(R.color.red))
                .setGravityOffset(0, 0, true)
                .setBadgePadding(5, true)
                .setBadgeTextSize(15, true)
                .setBadgeGravity(Gravity.START | Gravity.TOP);
        mDialog = new RxDialog(getContext());
        mShopData = new WeakHashMap<>();

        mMachine_num = (String) SPUtil.get(mContext, "MACHINE_NUM", "");
        Log.e("mMachine", mMachine_num + "-==================");
        initMachineId();

    }

    private void initTimer() {
        mTimer = new Timer();
        final BaseTimerTask task = new BaseTimerTask(this);
        mTimer.schedule(task, 0, 1000);
    }

    private void initData(String machineid) {
        showTotalPrice();

        final RequestParams requestParams = new RequestParams();
        requestParams.put("machineid", machineid);
        RequestCenter.GetMachineCommodityList(requestParams, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                MachineGoodBean machineGoodBean = (MachineGoodBean) responseObj;
                mData = machineGoodBean.getData();
                int count = machineGoodBean.getCount();

                if (mData != null && mData.size() > 0) {
                    mEmptyShop.setVisibility(View.GONE);
                    mRvShop.setVisibility(View.VISIBLE);
                    if (count <= 15) {
                        page = 1;

                        mShopAdapter = new ShopAdapter(R.layout.item_shop, mData);
                        mBtnLast.setClickable(false);
                        mBtnNext.setClickable(false);
                    } else {
                        mBtnLast.setClickable(true);
                        mBtnNext.setClickable(true);
                        List<MachineGoodBean.DataBean> dataBeen = checkPage(count);
                        mShopAdapter = new ShopAdapter(R.layout.item_shop, dataBeen);
                    }
                    initPage(page);
                    //mShopAdapter = new ShopAdapter(R.layout.item_shop, mData);

                    mShopAdapter.setShopCartInterface(ShopFragment.this);
                    mRvShop.setLayoutManager(new GridLayoutManager(getActivity(), 5));
                    mShopAdapter.openLoadAnimation();
                    mRvShop.setAdapter(mShopAdapter);
                } else {
                    mEmptyShop.setVisibility(View.VISIBLE);
                    mRvShop.setVisibility(View.GONE);
                    mTvEmpty.setText("抱歉Sorry!商品已售罄~");

                }
            }

            @Override
            public void onFailure(Object responseObj) {
                OkHttpException error = (OkHttpException) responseObj;
                int errorCode = error.getCode();
                mEmptyShop.setVisibility(View.VISIBLE);
                mRvShop.setVisibility(View.GONE);
                mTvEmpty.setText("抱歉Sorry!商品已售罄~");
            }
        });


    }

    private List<MachineGoodBean.DataBean> checkPage(int count) {
        int i = count / itemCount;
        int i1 = count % itemCount;
        if (i1 != 0) {
            page = i + 1;
        } else {
            page = i;
        }

        int z = 0;
        for (int j = 1; j <= page; j++) {
            List<MachineGoodBean.DataBean> item = new ArrayList<>();
            for (int k = z; k < itemCount; k++) {
                if (k < count) {
                    MachineGoodBean.DataBean dataBean = mData.get(k);
                    if (dataBean != null) {
                        item.add(dataBean);
                    }
                }
            }
            z = itemCount;
            itemCount = itemCount + 15;
            mShopData.put(j, item);
        }

        return mShopData.get(CurrentPage);
    }

    private void initPage(final int page) {
        List<String> pages = new ArrayList<>();
        for (int i = 0; i < page; i++) {
            int item = i + 1;
            pages.add(String.valueOf(item));
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRvPage.setLayoutManager(linearLayoutManager);
        mShopCartPageAdapter = new ShopCartPageAdapter(R.layout.item_cart_page, pages);
        mShopCartPageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (page == 1) {
                    return;
                }
                CurrentPage = position + 1;
                List<MachineGoodBean.DataBean> lastData = mShopData.get(CurrentPage);
                mShopAdapter.setNewData(lastData);
                mShopAdapter.openLoadAnimation();
                mShopAdapter.notifyDataSetChanged();
                mShopCartPageAdapter.changeSelected(position);
            }
        });
        mShopCartPageAdapter.openLoadAnimation();
        mRvPage.setAdapter(mShopCartPageAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCartStorage.clearCartData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @OnClick({R.id.btn_last, R.id.btn_next, R.id.btn_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_last:
                if (CurrentPage > 1) {
                    CurrentPage = CurrentPage - 1;
                    if (mShopCartPageAdapter != null) {
                        mShopCartPageAdapter.changeSelected(CurrentPage - 1);
                    }
                    List<MachineGoodBean.DataBean> lastData = mShopData.get(CurrentPage);
                    mShopAdapter.setNewData(lastData);
                    mShopAdapter.openLoadAnimation();
                    mShopAdapter.notifyDataSetChanged();
                }


                break;
            case R.id.btn_next:
                if (CurrentPage < page) {
                    CurrentPage = CurrentPage + 1;
                    if (mShopCartPageAdapter != null) {
                        mShopCartPageAdapter.changeSelected(CurrentPage - 1);
                    }
                    List<MachineGoodBean.DataBean> nextData = mShopData.get(CurrentPage);
                    mShopAdapter.setNewData(nextData);
                    mShopAdapter.openLoadAnimation();
                    mShopAdapter.notifyDataSetChanged();
                }


                break;

            case R.id.btn_buy:
                List<MachineGoodBean.DataBean> dataBeen = mCartStorage.cartSparseToList();
                if (dataBeen != null && dataBeen.size() > 0) {
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

                    String orderid = "";
                    StringBuffer stringBuffer = new StringBuffer();
                    for (int i = 0; i < dataBeen.size(); i++) {
                        RxLogTool.e("shop", dataBeen.get(i).toString());
                        MachineGoodBean.DataBean dataBean = dataBeen.get(i);
                        String id = dataBean.getId();
                        int count = dataBean.getCount();
                        stringBuffer.append(id + "_" + count + ",");
                    }
                    orderid = stringBuffer.toString().substring(0, stringBuffer.toString().length() - 1);
                    String json = Common.ScanUrl + "{" +
                            "  \"code\": \"0\"," +
                            " \"orderid\": \"" + orderid + "\"," +
                            " \"machineid\": \"" + mMachine_num + "\"}";
                    RxLogTool.e("json", json);

                    Bitmap image = QRCode.Create2DCode(json, 300, 300);

                    QR_CODE.setImageBitmap(image);
                    initTimer();
                    mDialog.setContentView(dialogView);
                    mDialog.show();
                } else {
                    return;
                }


                break;
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
                       /* Intent intent = new Intent(getActivity(), LauncherActivity.class);
                        startActivity(intent);

                        getActivity().finish();
                        getActivity().overridePendingTransition(R.anim.action_alpha_in, R.anim.action_alpha_out);*/
                        if (mTimer != null) {
                            mTimer.cancel();
                            mTimer = null;
                        }
                    }
                }
            }
        });

    }

    @Override
    public void add(View view, int postion) {
        if (page == 1) {
            MachineGoodBean.DataBean dataBean = mData.get(postion);
            mCartStorage.addData(dataBean);
        } else {
            List<MachineGoodBean.DataBean> curData = mShopData.get(CurrentPage);
            MachineGoodBean.DataBean dataBean = curData.get(postion);
            mCartStorage.addData(dataBean);
        }

        showTotalPrice();
    }

    private void showTotalPrice() {
        mCartData = mCartStorage.cartSparseToList();
        if (mCartData != null && mCartData.size() > 0) {
            int totalCount = 0;
            double totalPrice = 0.00;
            int size = mCartData.size();
            for (int i = 0; i < size; i++) {
                MachineGoodBean.DataBean dataBean = mCartData.get(i);
                int count = Integer.valueOf(dataBean.getCount());
                double price = Double.parseDouble(dataBean.getPrice1());
                double itemPrice = price * count;
                totalPrice = itemPrice + totalPrice;
                totalCount = count + totalCount;
            }
            mRvCart.setText("¥" + RxDataTool.getAmountValue(totalPrice));
            mBadge.setBadgeNumber(totalCount);

        } else {
            mRvCart.setText("¥" + "0.00");
            mBadge.setBadgeNumber(0);
        }
    }

    @Override
    public void remove(View view, int postion) {
        if (page == 1) {
            MachineGoodBean.DataBean dataBean = mData.get(postion);
            mCartStorage.deleteData(dataBean);
        } else {
            List<MachineGoodBean.DataBean> curData = mShopData.get(CurrentPage);
            MachineGoodBean.DataBean dataBean = curData.get(postion);
            mCartStorage.deleteData(dataBean);
        }

        showTotalPrice();
    }


    private void initMachineId() {
        final RequestParams requestParams = new RequestParams();
        String machine_num = (String) SPUtil.get(mContext, "MACHINE_NUM", "");
        if (!machine_num.isEmpty()) {
            requestParams.put("machineid", machine_num);
            RequestCenter.GetMachineInfo1(requestParams, new DisposeDataListener() {

                private String mMachineId;

                @Override
                public void onSuccess(Object responseObj) {
                    GetCodeBean getCodeBean = (GetCodeBean) responseObj;
                    int resultCode = Integer.parseInt(getCodeBean.getCode());
                    if (resultCode == 0) {
                        //绑定机器
                        mMachineId = getCodeBean.getId();
                        initData(mMachineId);

                    } else {
                        mEmptyShop.setVisibility(View.VISIBLE);
                        mRvShop.setVisibility(View.GONE);
                        mTvEmpty.setText("Sorry！连接网络错误");
                    }


                }

                @Override
                public void onFailure(Object responseObj) {
                    OkHttpException error = (OkHttpException) responseObj;
                    int errorCode = error.getCode();
                    mEmptyShop.setVisibility(View.VISIBLE);
                    mRvShop.setVisibility(View.GONE);
                    mTvEmpty.setText("Sorry！连接网络错误");

                }
            });
        } else {
            SPUtil.put(mContext, "MACHINE_NUM", "");

        }
    }
}