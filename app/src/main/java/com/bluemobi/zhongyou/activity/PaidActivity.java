package com.bluemobi.zhongyou.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bluemobi.zhongyou.R;
import com.bluemobi.zhongyou.adapter.PaidListAdapter;
import com.bluemobi.zhongyou.bean.PaidBean;
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

public class PaidActivity extends BaseActivity implements View.OnClickListener ,PaidListAdapter.OnPaidCallBack {
    private PaidActivity mContext;
    @BindView(R.id.title)
    public TextView mTitle;
    @BindView(R.id.startTime)
    public TextView mStartTime;
    @BindView(R.id.beginTime)
    public TextView mBeginTime;
    @BindView(R.id.recyclerView)
    public RecyclerView mRecyclerView;
    private PaidListAdapter mAdapter;
    @BindView(R.id.spinner)
    public Spinner mSpinner;
    private ArrayAdapter<CharSequence> mSpinnerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_paid;
    }

    @Override
    protected Presenter getModelView() {
        return null;
    }

    @Override
    protected void initData() {
        mContext = this;
        mTitle.setText(R.string.paid_title);
        mStartTime.setOnClickListener(this);
        mBeginTime.setOnClickListener(this);
        mStartTime.setText(DateUtil.getCurrentTime());
        mBeginTime.setText(DateUtil.getCurrentTime());
        initSpinner();
        initRecyclerView();
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

    private void initRecyclerView(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PaidListAdapter(this,this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setData(getData());
    }

    private void initSpinner(){
        mSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.PaidListArray, R.layout.spinner_item);
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mSpinnerAdapter);
        mSpinner.setSelection(0);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        mAdapter.setData(getData());
                        break;
                    case 1:
                        mAdapter.setData(brushSelection(0));
                        break;
                    case 2:
                        mAdapter.setData(brushSelection(1));
                        break;
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onItemContentListener(int position) {
    }

    @Override
    public void onPrintTicketListener(int position) {
        printerByte(PrintContract.startPrinter(getPrinterGoodsModel(mAdapter.getData().get(position))));
        mAdapter.getData().remove(position);
        mAdapter.notifyDataSetChanged();
    }

    private PrinterBean getPrinterGoodsModel(PaidBean bean){
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

        model.setOrderNumber(bean.getOrderNumber());
        model.setTime(bean.getOrderTime());
        model.setTotalPrice(bean.getTotalPrice());
        model.setOffer(offerMoney);
        model.setActualAmount(bean.getTotalPrice()-offerMoney);

        List<PrinterBean.Printers> list = new ArrayList<>();
        List<PaidBean.Paid> goods = bean.getData();
        for (PaidBean.Paid g:goods){
            PrinterBean.Printers p = new PrinterBean.Printers();
            p.setType(g.getCategory());
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

    private List<PaidBean> brushSelection(int type){
        List<PaidBean> list = new ArrayList<>();
        List<PaidBean> data = getData();
        for (PaidBean bean:data){
            if (bean.getType()==type){
                list.add(bean);
            }
        }
        return list;
    }

    private List<PaidBean> getData(){
        String str = "[\n" +
                "    {\n" +
                "        \"type\": 0,\n" +
                "        \"orderNumber\": 12345678,\n" +
                "        \"orderTime\": \"2018-6-12 15:05:11\",\n" +
                "        \"data\": [\n" +
                "            {\n" +
                "                \"category\": \"1\",\n" +
                "                \"name\": \"96#\",\n" +
                "                \"price\": 10.0,\n" +
                "                \"number\": 2\n" +
                "            }\n" +
                "        ],\n" +
                "        \"totalPrice\": 20.0\n" +
                "    },\n" +
                "    {\n" +
                "        \"type\": 1,\n" +
                "        \"orderNumber\": 12345679,\n" +
                "        \"orderTime\": \"2018-6-12 16:05:11\",\n" +
                "        \"data\": [\n" +
                "            {\n" +
                "                \"category\": \"汽车用品\",\n" +
                "                \"name\": \"燃油添加剂\",\n" +
                "                \"price\": 10.0,\n" +
                "                \"number\": 10\n" +
                "            },\n" +
                "            {\n" +
                "                \"category\": \"饮料\",\n" +
                "                \"name\": \"矿泉水\",\n" +
                "                \"price\": 2.0,\n" +
                "                \"number\": 5\n" +
                "            }\n" +
                "        ],\n" +
                "        \"totalPrice\": 110.0\n" +
                "    },\n" +
                "    {\n" +
                "        \"type\": 1,\n" +
                "        \"orderNumber\": 12345611,\n" +
                "        \"orderTime\": \"2018-6-12 16:18:58\",\n" +
                "        \"data\": [\n" +
                "            {\n" +
                "                \"category\": \"饮料\",\n" +
                "                \"name\": \"矿泉水\",\n" +
                "                \"price\": 2.0,\n" +
                "                \"number\": 5\n" +
                "            }\n" +
                "        ],\n" +
                "        \"totalPrice\": 10.0\n" +
                "    },\n" +
                "    {\n" +
                "        \"type\": 0,\n" +
                "        \"orderNumber\": 12131415,\n" +
                "        \"orderTime\": \"2018-6-12 19:35:19\",\n" +
                "        \"data\": [\n" +
                "            {\n" +
                "                \"category\": \"2\",\n" +
                "                \"name\": \"95#\",\n" +
                "                \"price\": 100.0,\n" +
                "                \"number\": 1\n" +
                "            }\n" +
                "        ],\n" +
                "        \"totalPrice\": 100.0\n" +
                "    }\n" +
                "]";
        return JSON.parseArray(str, PaidBean.class);
    }
}
