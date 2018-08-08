package com.bluemobi.zhongyou.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bluemobi.zhongyou.R;
import com.bluemobi.zhongyou.adapter.CommentAdapter;
import com.bluemobi.zhongyou.adapter.PayCouponOilCardAdapter;
import com.bluemobi.zhongyou.adapter.PaymentMethodAdapter;
import com.bluemobi.zhongyou.bean.CouponBean;
import com.bluemobi.zhongyou.bean.OrderBean;
import com.bluemobi.zhongyou.log.LogUtil;
import com.bluemobi.zhongyou.bean.TypeBean;
import com.bluemobi.zhongyou.mvp.presenter.base.Presenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PayCouponOilCardActivity extends BaseActivity implements PaymentMethodAdapter.OnPaymentMethodCallBack{
    private PayCouponOilCardActivity mContext;
    @BindView(R.id.title)
    public TextView mTitle;
    @BindView(R.id.recyclerView)
    public RecyclerView mRecyclerView;

    private static final int REQUEST_CODE_SCAN = 0x0000;

    private OrderBean mOrderBean;
    private CouponBean mCouponBean;

    private List<TypeBean> mPaymentMethodList = new ArrayList<>();

    private double oilCardMoney = -1;
    private int currPosition = 0;
    private PayCouponOilCardAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_coupon_oil_card;
    }

    @Override
    protected Presenter getModelView() {
        return null;
    }

    @Override
    protected void initData() {
        mContext = this;

        oilCardMoney = getIntent().getDoubleExtra("oilCardMoney",-1);
        mPaymentMethodList = JSON.parseArray(getIntent().getStringExtra("data"),TypeBean.class);
        mOrderBean = (OrderBean) getIntent().getSerializableExtra("model");
        mCouponBean = (CouponBean) getIntent().getSerializableExtra("CouponBean");

        mTitle.setText(
                String.format(
                        getResources().getString(R.string.unpaid_title),
                        mOrderBean.getType()==0?getResources().getString(R.string.oils):getResources().getString(R.string.goods)
                )
        );
        initListData();
        mAdapter.setData(getData());
    }

    private void initListData(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PayCouponOilCardAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private SparseArray getData(){
        SparseArray array = new SparseArray();
        array.put(0,mOrderBean);
        mCouponBean.setOilCardMoney(oilCardMoney);
        mCouponBean.setPaymentMoney(mCouponBean.getPaymentMoney()-oilCardMoney);
        array.put(1,mCouponBean);
        array.put(2,mPaymentMethodList);
        return array;
    }


    @Override
    public void onItemPaymentListener(int position) {
        currPosition = position;
        switch (position){
            case 0:
            case 1:
            case 2:
                Intent intent = new Intent(mContext, CaptureActivity.class);
                startActivityForResult(intent,REQUEST_CODE_SCAN);
                break;
            case 3:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK){
            if (data != null) {
                String content = data.getStringExtra(CaptureActivity.RESULT_CONTENT);
                LogUtil.d(content);
                Intent intent = new Intent(mContext,PaymentSuccessActivity.class);
                intent.putExtra("model",(OrderBean)mAdapter.getData().get(0));
                intent.putExtra("CouponBean",(CouponBean)mAdapter.getData().get(1));
                intent.putExtra("payment",((List<TypeBean>)mAdapter.getData().get(2)).get(currPosition).getName());
                startActivity(intent);
                mContext.finish();
            }
        }
    }
}
