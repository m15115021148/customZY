package com.bluemobi.zhongyou.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.bluemobi.zhongyou.R;
import com.bluemobi.zhongyou.log.LogUtil;
import com.bluemobi.zhongyou.mvp.presenter.base.Presenter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import io.reactivex.functions.Consumer;

public class HomePagerActivity extends BaseActivity implements View.OnClickListener{
    private HomePagerActivity mContext;
    @BindView(R.id.title)
    public TextView mTitle;
    @BindViews({R.id.unpaid,R.id.returns,R.id.paid})
    public List<TextView> mMenuTypeList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_pager;
    }

    @Override
    protected Presenter getModelView() {
        return null;
    }

    @Override
    protected void initData() {
        mContext = this;
        mTitle.setText(R.string.home_title);
        mMenuTypeList.get(0).setOnClickListener(this);
        mMenuTypeList.get(1).setOnClickListener(this);
        mMenuTypeList.get(2).setOnClickListener(this);
        getPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA);
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent();
        intent.setAction(BaseActivity.TAG_ESC_ACTIVITY);
        sendBroadcast(intent);
        mContext.finish();
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        if (view == mMenuTypeList.get(0)){
            Intent intent = new Intent(mContext,UnpaidActivity.class);
            startActivity(intent);
        }
        if (view == mMenuTypeList.get(1)){
            Intent intent = new Intent(mContext,ReturnsActivity.class);
            startActivity(intent);
        }
        if (view == mMenuTypeList.get(2)){
            Intent intent = new Intent(mContext,PaidActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @SuppressLint("CheckResult")
    public void getPermission(Activity context, String...str){
        RxPermissions permissions = new RxPermissions(context);
        permissions.request(str)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean){//所以权限都同意 才为true

                        }else{

                        }
                    }
                });
    }

}
