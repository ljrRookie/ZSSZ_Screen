package com.gzah.zssz_screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.EditText;

import com.allen.library.SuperTextView;
import com.gzah.zssz_screen.utils.SPUtil;
import com.vondear.rxtools.view.RxToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.gzah.zssz_screen.application.MyApplication.mContext;

/**
 * Created by 林佳荣 on 2018/6/8.
 * Github：https://github.com/ljrRookie
 * Function ：
 */

public class MachineActivity extends AppCompatActivity {
    @BindView(R.id.edit_machine_num)
    EditText mEditMachineNum;
    @BindView(R.id.btn_commit)
    SuperTextView mBtnCommit;
    @BindView(R.id.edit_machine_num_agin)
    EditText mEditMachineNumAgin;
    private String mMachine_num = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setContentView(R.layout.activity_machine);
        ButterKnife.bind(this);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mMachine_num = (String) SPUtil.get(mContext, "MACHINE_NUM", "");
        if(!mMachine_num.isEmpty()){
            SPUtil.put(mContext, "HAS_FIRST_LAUNCHER_APP", true);
            SPUtil.put(mContext, "MACHINE_NUM", mMachine_num);
            Intent intent = new Intent(MachineActivity.this, LauncherActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.action_alpha_in, R.anim.action_alpha_out);
        }
    }

    @OnClick(R.id.btn_commit)
    public void onViewClicked() {
        String machineNum = mEditMachineNum.getText().toString().trim();
        String machineNumAgain = mEditMachineNumAgin.getText().toString().trim();
        if (machineNum.isEmpty()) {
            RxToast.normal("请输入机器码");
            return;
        }
        if(!machineNum.equals(machineNumAgain)){
            RxToast.normal("输入的机器码不一致");
            return;
        }
        SPUtil.put(mContext, "HAS_FIRST_LAUNCHER_APP", true);
        SPUtil.put(mContext, "MACHINE_NUM", machineNum);
        Intent intent = new Intent(MachineActivity.this, LauncherActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.action_alpha_in, R.anim.action_alpha_out);


    }
}
