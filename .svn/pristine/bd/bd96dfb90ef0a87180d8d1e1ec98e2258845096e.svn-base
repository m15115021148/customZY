package com.bluemobi.zhongyou.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bluemobi.zhongyou.R;
import com.bluemobi.zhongyou.adapter.GoodsListDetailAdapter;
import com.bluemobi.zhongyou.adapter.PaymentMethodAdapter;
import com.bluemobi.zhongyou.bean.OrderBean;
import com.bluemobi.zhongyou.bean.TypeBean;
import com.bluemobi.zhongyou.log.LogUtil;
import com.bluemobi.zhongyou.mvp.presenter.base.Presenter;
import com.bluemobi.zhongyou.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;

public class GoodsListDetailActivity extends BaseActivity implements PaymentMethodAdapter.OnPaymentMethodCallBack{
    private GoodsListDetailActivity mContext;
    @BindView(R.id.title)
    public TextView mTitle;
    @BindView(R.id.recyclerView)
    public RecyclerView mRecyclerView;

    private OrderBean mOrderBean; ;

    private int[] res = {
            R.drawable.pay_1,R.drawable.pay_2, R.drawable.pay_3, R.drawable.pay_4,
            R.drawable.pay_5,R.drawable.pay_6, R.drawable.pay_7
    };
    @BindArray(R.array.PaymentArray)
    public String[] mArray;

    private int currPosition = 0;
    private static final int REQUEST_CODE_SCAN = 0x0000;
    private static final int REQUEST_CODE_SCAN_COUPON = 0x0001;
    private GoodsListDetailAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_goods_list_detail;
    }

    @Override
    protected Presenter getModelView() {
        return null;
    }

    @Override
    protected void initData() {
        mContext = this;
        mTitle.setText(
                String.format(getResources().getString(R.string.unpaid_title), getResources().getString(R.string.goods))
        );
        List<OrderBean.Orders> data = JSON.parseArray(getIntent().getStringExtra("data"), OrderBean.Orders.class);

        mOrderBean = new OrderBean();
        mOrderBean.setType(1);
        mOrderBean.setOrderNumber(1231234);
        mOrderBean.setOrderTime(DateUtil.getSWAHDate());
        mOrderBean.setData(data);
        double totalPrice = 0;
        for (OrderBean.Orders bean : data){
            totalPrice = totalPrice + bean.getNumber()*bean.getPrice();
        }
        mOrderBean.setTotalPrice(totalPrice);

        SparseArray array = new SparseArray<>();
        array.put(0,mOrderBean);
        array.put(1,getPaymentData(mArray,res));

        mAdapter = new GoodsListDetailAdapter(this,this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setData(array);
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
                OrderBean mGoods = (OrderBean) mAdapter.getData().get(0);
                List<TypeBean> mPay = (List<TypeBean>) mAdapter.getData().get(1);

                String content = data.getStringExtra(CaptureActivity.RESULT_CONTENT);
                LogUtil.d(content);
                Intent intent = new Intent(mContext,PaymentSuccessActivity.class);
                intent.putExtra("model",mGoods);
                intent.putExtra("payment",mPay.get(currPosition).getName());
                startActivity(intent);
                mContext.finish();
            }
        }
        if (requestCode == REQUEST_CODE_SCAN_COUPON && resultCode == RESULT_OK){
            if (data != null) {
                OrderBean mGoods = (OrderBean) mAdapter.getData().get(0);
                List<TypeBean> list = (List<TypeBean>) mAdapter.getData().get(1);

                list.remove(currPosition);
                String content = data.getStringExtra(CaptureActivity.RESULT_CONTENT);
                LogUtil.d(content);
                Intent intent = new Intent(mContext,PayCouponActivity.class);
                intent.putExtra("model",mGoods);
                intent.putExtra("data", JSON.toJSONString(list));
                startActivity(intent);
                mContext.finish();
            }
        }
    }

    private List<TypeBean> getPaymentData(String[] str, int[] res){
        List<TypeBean> list = new ArrayList<>();
        for (int i=0;i<str.length;i++){
            TypeBean model = new TypeBean();
            model.setName(str[i]);
            model.setRes(res[i]);
            list.add(model);
        }
        return list;
    }
}
