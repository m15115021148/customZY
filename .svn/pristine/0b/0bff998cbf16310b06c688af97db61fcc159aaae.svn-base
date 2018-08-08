package com.bluemobi.zhongyou.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bluemobi.zhongyou.R;
import com.bluemobi.zhongyou.adapter.PaymentSuccessAdapter;
import com.bluemobi.zhongyou.bean.CouponBean;
import com.bluemobi.zhongyou.bean.OrderBean;
import com.bluemobi.zhongyou.log.LogUtil;
import com.bluemobi.zhongyou.bean.PrinterBean;
import com.bluemobi.zhongyou.mvp.presenter.base.Presenter;
import com.bluemobi.zhongyou.util.PrintContract;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PaymentSuccessActivity extends BaseActivity {
    private PaymentSuccessActivity mContext;
    @BindView(R.id.title)
    public TextView mTitle;
    @BindView(R.id.recyclerView)
    public RecyclerView mRecyclerView;


    private OrderBean mOrderBean ;
    private CouponBean mCouponBean;

    private int type = 0;

    private String payment ;//payment
    private PaymentSuccessAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_payment_success;
    }

    @Override
    protected Presenter getModelView() {
        return null;
    }

    @Override
    protected void initData() {
        mContext = this;
        payment = getIntent().getStringExtra("payment");
        mOrderBean = (OrderBean) getIntent().getSerializableExtra("model");
        mCouponBean = (CouponBean) getIntent().getSerializableExtra("CouponBean");
        if (mCouponBean == null){
            mCouponBean = new CouponBean();
            mCouponBean.setPaymentMoney(mOrderBean.getTotalPrice());
        }

        type = mOrderBean.getType();
        if (type == 0){
            mTitle.setText(
                    String.format(
                            getResources().getString(R.string.unpaid_title),
                            getResources().getString(R.string.oils)
                    )
            );

            printerByte(PrintContract.startPrinter(getPrinterOilModel(mOrderBean,mCouponBean)));
        }else {
            mTitle.setText(
                    String.format(
                            getResources().getString(R.string.unpaid_title),
                            getResources().getString(R.string.goods)
                    )
            );

            printerByte(PrintContract.startPrinter(getPrinterGoodsModel(mOrderBean,mCouponBean)));
            sendBroadcastFinish();
        }
        initListData();
        mAdapter.setData(getData());
    }

    public void initListData(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PaymentSuccessAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    public SparseArray getData(){
        SparseArray array = new SparseArray();
        array.put(0,mOrderBean);
        array.put(1,mCouponBean);
        array.put(2,null);
        return array;
    }

    private void sendBroadcastFinish(){
        Intent intent = new Intent();
        intent.setAction(GoodsListActivity.GOODS_LIST_FINISH);
        sendBroadcast(intent);
    }

    private PrinterBean getPrinterOilModel(OrderBean bean,CouponBean coupon){
        PrinterBean model = new PrinterBean();
        model.setmTitle(getResources().getString(R.string.printer_title));
        model.setmOrderNumber(getResources().getString(R.string.printer_order_number));
        model.setmTime(getResources().getString(R.string.printer_time));
        model.setmType(getResources().getString(R.string.printer_oil_gun));
        model.setmName(getResources().getString(R.string.printer_oil_number));
        model.setmNumber(getResources().getString(R.string.printer_oil_liters));
        model.setmPrice(getResources().getString(R.string.printer_price));

        model.setOrderNumber(bean.getOrderNumber());
        model.setTime(bean.getOrderTime());
        model.setTotalPrice(bean.getTotalPrice());
        model.setOffer(coupon.getCouponMoney());
        model.setActualAmount(coupon.getPaymentMoney());

        List<PrinterBean.Printers> list = new ArrayList<>();
        PrinterBean.Printers p = new PrinterBean.Printers();
        p.setType(String.valueOf(bean.getData().get(0).getType()));
        p.setName(String.format(getResources().getString(R.string.order_oil_number_unit), Integer.parseInt(bean.getData().get(0).getName())));
        p.setNumber(String.format(getResources().getString(R.string.order_number_of_liters_unit), bean.getData().get(0).getNumber()));
        p.setPrice(bean.getData().get(0).getPrice());
        list.add(p);
        model.setData(list);
        model.setmTotalPrice(getResources().getString(R.string.printer_total_price));
        model.setmOffer(getResources().getString(R.string.printer_offer_money));
        model.setmActualAmount(getResources().getString(R.string.printer_real_money));
        model.setmPayment(getResources().getString(R.string.printer_payment));
        model.setQrTitle(getResources().getString(R.string.printer_qr_title));
        model.setPayment(payment);

        LogUtil.w(JSON.toJSONString(model));
        return model;
    }

    private PrinterBean getPrinterGoodsModel(OrderBean bean,CouponBean coupon){
        PrinterBean model = new PrinterBean();
        model.setmTitle(getResources().getString(R.string.printer_title));
        model.setmOrderNumber(getResources().getString(R.string.printer_order_number));
        model.setmTime(getResources().getString(R.string.printer_time));
        model.setmType(getResources().getString(R.string.printer_goods_type));
        model.setmName(getResources().getString(R.string.printer_goods_name));
        model.setmNumber(getResources().getString(R.string.printer_goods_number));
        model.setmPrice(getResources().getString(R.string.printer_price));

        model.setOrderNumber(bean.getOrderNumber());
        model.setTime(bean.getOrderTime());
        model.setTotalPrice(bean.getTotalPrice());
        model.setOffer(coupon.getCouponMoney());
        model.setActualAmount(coupon.getPaymentMoney());

        List<PrinterBean.Printers> list = new ArrayList<>();
        List<OrderBean.Orders> goods = bean.getData();
        for (OrderBean.Orders g:goods){
            PrinterBean.Printers p = new PrinterBean.Printers();
            p.setType(g.getType());
            p.setName(g.getName());
            p.setNumber(String.valueOf(g.getNumber()));
            p.setPrice(g.getPrice());
            list.add(p);
        }
        model.setData(list);
        model.setmTotalPrice(getResources().getString(R.string.printer_total_price));
        model.setmOffer(getResources().getString(R.string.printer_offer_money));
        model.setmActualAmount(getResources().getString(R.string.printer_real_money));
        model.setmPayment(getResources().getString(R.string.printer_payment));
        model.setQrTitle(getResources().getString(R.string.printer_qr_title));
        model.setPayment(payment);

        return model;
    }
}
