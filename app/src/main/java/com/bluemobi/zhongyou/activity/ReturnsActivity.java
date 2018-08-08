package com.bluemobi.zhongyou.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bluemobi.zhongyou.R;
import com.bluemobi.zhongyou.adapter.ReturnsListAdapter;
import com.bluemobi.zhongyou.bean.OrderBean;
import com.bluemobi.zhongyou.bean.PrinterBean;
import com.bluemobi.zhongyou.mvp.presenter.base.Presenter;
import com.bluemobi.zhongyou.util.DateUtil;
import com.bluemobi.zhongyou.util.PrintContract;
import com.bluemobi.zhongyou.view.DateListener;
import com.bluemobi.zhongyou.view.TimeConfig;
import com.bluemobi.zhongyou.view.TimeSelectorDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ReturnsActivity extends BaseActivity implements View.OnClickListener ,ReturnsListAdapter.OnReturnsCallBack{
    private ReturnsActivity mContext;
    @BindView(R.id.title)
    public TextView mTitle;
    @BindView(R.id.startTime)
    public TextView mStartTime;
    @BindView(R.id.beginTime)
    public TextView mBeginTime;
    @BindView(R.id.recyclerView)
    public RecyclerView mRecyclerView;
    private ReturnsListAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_returns;
    }

    @Override
    protected Presenter getModelView() {
        return null;
    }

    @Override
    protected void initData() {
        mContext = this;
        mTitle.setText(R.string.returns_title);
        mStartTime.setOnClickListener(this);
        mBeginTime.setOnClickListener(this);
        mStartTime.setText(DateUtil.getCurrentTime());
        mBeginTime.setText(DateUtil.getCurrentTime());
        initRecyclerView();
    }

    private void initRecyclerView(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ReturnsListAdapter(this,this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setData(getData());
    }

    @Override
    public void onClick(View v) {
        if (v == mStartTime){
            TimeSelectorDialog dialog = new TimeSelectorDialog(this);
            dialog.setCurrentDate(DateUtil.getCurrentTime());
            dialog.setIsShowtype(TimeConfig.YEAR_MONTH_DAY);
            dialog.setStartYear(2000);
            dialog.setDateListener(new DateListener() {
                @Override
                public void onReturnDate(String time,int year, int month, int day, int hour, int minute, int isShowType) {
                    mStartTime.setText(time);
                }
                @Override
                public void onReturnDate(String empty) {

                }
            });
            dialog.show();
        }
        if (v == mBeginTime){
            TimeSelectorDialog dialog = new TimeSelectorDialog(this);
            dialog.setCurrentDate(DateUtil.getCurrentTime());
            dialog.setIsShowtype(TimeConfig.YEAR_MONTH_DAY);
            dialog.setStartYear(2000);
            dialog.setDateListener(new DateListener() {
                @Override
                public void onReturnDate(String time,int year, int month, int day, int hour, int minute, int isShowType) {
                    mBeginTime.setText(time);
                }
                @Override
                public void onReturnDate(String empty) {

                }
            });
            dialog.show();
        }
    }

    @Override
    public void onItemContentListener(int position) {

    }

    @Override
    public void onReturnsListener(int position) {

    }

    @Override
    public void onPrintTicketListener(int position) {
        printerByte(PrintContract.startPrinter(getPrinterGoodsModel(mAdapter.getData().get(position))));
        mAdapter.getData().remove(position);
        mAdapter.notifyDataSetChanged();
    }

    private PrinterBean getPrinterGoodsModel(OrderBean mOrderGoodsBean){
        int offerMoney = 5;
        String payment = "现金/支付宝";
        PrinterBean model = new PrinterBean();
        model.setmTitle(getResources().getString(R.string.printer_title));
        model.setmOrderNumber(getResources().getString(R.string.printer_order_number));
        model.setmTime(getResources().getString(R.string.printer_time));
        model.setmType(getResources().getString(R.string.printer_goods_type));
        model.setmName(getResources().getString(R.string.printer_goods_name));
        model.setmNumber(getResources().getString(R.string.printer_goods_number));
        model.setmPrice(getResources().getString(R.string.printer_price));

        model.setOrderNumber(mOrderGoodsBean.getOrderNumber());
        model.setTime(mOrderGoodsBean.getOrderTime());
        model.setTotalPrice(mOrderGoodsBean.getTotalPrice());
        model.setOffer(offerMoney);
        model.setActualAmount(mOrderGoodsBean.getTotalPrice()-offerMoney);

        List<PrinterBean.Printers> list = new ArrayList<>();
        List<OrderBean.Orders> goods = mOrderGoodsBean.getData();
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

    private List<OrderBean> getData(){
        List<OrderBean> list = new ArrayList<>();

        OrderBean m = new OrderBean();
        m.setType(1);
        m.setOrderNumber(123456);
        m.setOrderTime("2018-05-30 15:20:12");
        m.setTotalPrice(100);

        List<OrderBean.Orders> l = new ArrayList<>();
        OrderBean.Orders g = new OrderBean.Orders();
        g.setType("汽车用品");
        g.setName("燃油添加剂");
        g.setPrice(10);
        g.setNumber(10);
        l.add(g);
        m.setData(l);

        OrderBean m1 = new OrderBean();
        m1.setType(1);
        m1.setOrderNumber(123457);
        m1.setOrderTime("2018-05-30 16:20:12");
        m1.setTotalPrice(75);

        List<OrderBean.Orders> l1 = new ArrayList<>();
        OrderBean.Orders g1 = new OrderBean.Orders();
        g1.setType("汽车用品");
        g1.setName("燃油添加剂");
        g1.setPrice(10);
        g1.setNumber(5);
        l1.add(g1);

        OrderBean.Orders g2 = new OrderBean.Orders();
        g2.setType("饮料");
        g2.setName("矿泉水");
        g2.setPrice(5);
        g2.setNumber(5);
        l1.add(g2);
        m1.setData(l1);


        OrderBean m2 = new OrderBean();
        m2.setType(1);
        m2.setOrderNumber(898555);
        m2.setOrderTime("2018-06-04 15:20:12");
        m2.setTotalPrice(50);

        List<OrderBean.Orders> l2 = new ArrayList<>();
        OrderBean.Orders g3 = new OrderBean.Orders();
        g3.setType("饮料");
        g3.setName("燃油添加剂");
        g3.setPrice(5);
        g3.setNumber(10);
        l2.add(g3);
        m2.setData(l2);

        list.add(m);
        list.add(m1);
        list.add(m2);

        return list;
    }
}
