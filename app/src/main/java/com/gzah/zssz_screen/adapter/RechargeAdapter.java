package com.gzah.zssz_screen.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gzah.zssz_screen.R;

import java.util.ArrayList;

/**
 * Created by 林佳荣 on 2018/4/17.
 * Github：https://github.com/ljrRookie
 * Function ：
 */

public class RechargeAdapter extends BaseQuickAdapter<String,BaseViewHolder>{
    public RechargeAdapter(int layoutId, ArrayList<String> item) {
        super(layoutId,item);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_item, item);
    }
}
