package com.bluemobi.zhongyou.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bluemobi.zhongyou.R;
import com.bluemobi.zhongyou.adapter.PaymentMethodAdapter;
import com.bluemobi.zhongyou.log.LogUtil;
import com.bluemobi.zhongyou.bean.TypeBean;
import com.bluemobi.zhongyou.mvp.presenter.base.Presenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;

/**
 * payment method
 * create by chenMeng on 2018-05-30
 */
public class PaymentMethodActivity extends BaseActivity implements PaymentMethodAdapter.OnPaymentMethodCallBack{
    private PaymentMethodActivity mContext;
    @BindView(R.id.title)
    public TextView mTitle;
    @BindView(R.id.recyclerView)
    public RecyclerView mRecyclerView;
    private int[] res = {
            R.drawable.pay_1,R.drawable.pay_2,R.drawable.pay_3,
            R.drawable.pay_4,R.drawable.pay_5,R.drawable.pay_6,R.drawable.pay_7
    };
    @BindArray(R.array.PaymentArray)
    public String[] mArray;
    private static final int REQUEST_CODE_SCAN = 0x0000;
    private static final int REQUEST_CODE_SCAN_COUPON = 0x0001;
    private PaymentMethodAdapter mAdapter;
    private int currPosition = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_payment_method;
    }

    @Override
    protected Presenter getModelView() {
        return null;
    }

    @Override
    protected void initData() {
        mContext = this;
        mTitle.setText(R.string.payment_method);
        initRecyclerViewData();
    }

    private void initRecyclerViewData(){
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        mAdapter = new PaymentMethodAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setData(getData(mArray,res));
    }

    private List<TypeBean> getData(String[] str, int[] res){
        List<TypeBean> list = new ArrayList<>();
        for (int i=0;i<str.length;i++){
            TypeBean model = new TypeBean();
            model.setName(str[i]);
            model.setRes(res[i]);
            list.add(model);
        }
        return list;
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
            case 4:
                break;
            case 5:
                break;
            case 6://coupon pay
                Intent coupon = new Intent(mContext, CaptureActivity.class);
                startActivityForResult(coupon,REQUEST_CODE_SCAN_COUPON);
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
                intent.putExtra("model",getIntent().getSerializableExtra("model"));
                intent.putExtra("payment",mAdapter.getData().get(currPosition).getName());
                startActivity(intent);
                mContext.finish();
            }
        }
        if (requestCode == REQUEST_CODE_SCAN_COUPON && resultCode == RESULT_OK){
            if (data != null) {
                List<TypeBean> list = mAdapter.getData();
                list.remove(currPosition);
                String content = data.getStringExtra(CaptureActivity.RESULT_CONTENT);
                LogUtil.d(content);
                Intent intent = new Intent(mContext,PayCouponActivity.class);
                intent.putExtra("model",getIntent().getSerializableExtra("model"));
                intent.putExtra("data", JSON.toJSONString(list));
                startActivity(intent);
                mContext.finish();
            }
        }
    }
}
