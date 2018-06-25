package com.gzah.zssz_screen.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gzah.zssz_screen.R;

import java.util.List;

/**
 * Created by 林佳荣 on 2018/5/31.
 * Github：https://github.com/ljrRookie
 * Function ：
 */

public class ShopCartPageAdapter extends BaseQuickAdapter<String,BaseViewHolder>{

    private TextView mPage;
    private int mSelect = 0;
    public ShopCartPageAdapter(int layoutId, List<String> pages) {
        super(layoutId,pages);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_page, item);
        mPage = helper.getView(R.id.tv_page);

        int layoutPosition = helper.getLayoutPosition();
        if(layoutPosition == mSelect){
            mPage.setTextColor(mContext.getResources().getColor(R.color.blue_low));
        }else{
            mPage.setTextColor(mContext.getResources().getColor(R.color.text));
        }
    }
    public void changeSelected(int position){
        if(position != mSelect){
            mSelect = position;
            notifyDataSetChanged();
        }
    }
}
