package com.gzah.zssz_screen.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gzah.zssz_screen.Common;
import com.gzah.zssz_screen.R;
import com.gzah.zssz_screen.bean.MachineGoodBean;
import com.gzah.zssz_screen.listener.ShopCartInterface;

import java.util.List;

/**
 * Created by 林佳荣 on 2018/4/18.
 * Github：https://github.com/ljrRookie
 * Function ：
 */

public class ShopAdapter extends BaseQuickAdapter<MachineGoodBean.DataBean,BaseViewHolder>{
    private ShopCartInterface shopCartImp;

    public ShopAdapter(int layoutId,  List<MachineGoodBean.DataBean> string) {
        super(layoutId,string);
    }

    @Override
    protected void convert(BaseViewHolder helper, final MachineGoodBean.DataBean item) {
        ImageView view =helper.getView(R.id.iv_pic);
        Glide.with(mContext).load(Common.BaseUrl + item.getImage()).into(view);
        final TextView price = helper.getView(R.id.tv_count);
        helper.setText(R.id.tv_count,item.getCount()+"");
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_price,"¥"+item.getPrice1());
        final int position = helper.getLayoutPosition();
        helper.getView(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int stock = Integer.parseInt(item.getStock());
                int countCur = Integer.parseInt(price.getText().toString().trim());
                if(countCur<stock){
                    price.setText(countCur+1+"");
                    if (shopCartImp != null) {
                        shopCartImp.add(v, position);
                    }
                }
            }
        });
        helper.getView(R.id.btn_reduce).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int countCur = Integer.parseInt(price.getText().toString());
                if(countCur>0){
                    price.setText(countCur-1+"");
                    if (shopCartImp != null) {
                        shopCartImp.remove(v, position);
                    }
                }
            }
        });

    }
    public ShopCartInterface getShopCartInterface() {
        return shopCartImp;
    }

    public void setShopCartInterface(ShopCartInterface shopCartImp) {
        this.shopCartImp = shopCartImp;
    }
}
