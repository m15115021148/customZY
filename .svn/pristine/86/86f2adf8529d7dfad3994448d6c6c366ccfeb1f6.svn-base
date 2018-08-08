package com.bluemobi.zhongyou.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bluemobi.zhongyou.R;
import com.bluemobi.zhongyou.adapter.GoodsListAdapter;
import com.bluemobi.zhongyou.bean.OrderBean;
import com.bluemobi.zhongyou.log.LogUtil;
import com.bluemobi.zhongyou.mvp.presenter.base.Presenter;
import com.bluemobi.zhongyou.view.recyclerview.SwipeMenuView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GoodsListActivity extends BaseActivity implements View.OnClickListener,GoodsListAdapter.OnGoodsListCallBack {
    private GoodsListActivity mContext;
    @BindView(R.id.title)
    public TextView mTitle;
    @BindView(R.id.recyclerView)
    public RecyclerView mRecyclerView;
    private GoodsListAdapter mAdapter;

    private static final int REQUEST_CODE_SCAN = 0x0000;

    public static final String GOODS_LIST_FINISH = "com.bluemobi.goods.finish";
    private FinishBroadcast mBroadcast;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_goods_list;
    }

    @Override
    protected Presenter getModelView() {
        return null;
    }

    @Override
    protected void initData() {
        mContext = this;
        mTitle.setText(R.string.goods_list_title);

        mBroadcast = new FinishBroadcast();
        registerReceiver(mBroadcast,new IntentFilter(GOODS_LIST_FINISH));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new GoodsListAdapter(this,this);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setData(getData());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcast);
    }

    private SparseArray getData(){
        SparseArray array = new SparseArray();
        OrderBean bean = new OrderBean();
        List<OrderBean.Orders> list = new ArrayList<>();

        OrderBean.Orders goods = new OrderBean.Orders();
        goods.setType("汽车用品");
        goods.setName("燃油添加剂");
        goods.setPrice(100);
        goods.setNumber(1);
        list.add(goods);

        bean.setData(list);

        array.put(0,bean);
        array.put(1,null);
        return array;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK){
            if (data != null) {
                String content = data.getStringExtra(CaptureActivity.RESULT_CONTENT);
                LogUtil.d(content);

                OrderBean.Orders goods = new OrderBean.Orders();
                goods.setName("矿泉水");
                goods.setType("饮料");
                goods.setPrice(5);
                goods.setNumber(1);

                mAdapter.getOrdersData().add(goods);
                mAdapter.notifyDataSetChanged();

            }
        }
    }

    @Override
    public void onItemClickListener(int position) {

    }

    @Override
    public void onItemDeleteListener(SwipeMenuView view, int position) {
        view.quickClose();
        OrderBean bean = (OrderBean) mAdapter.getData().get(0);
        bean.getData().remove(position);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onContinueScanListener() {
        Intent intent = new Intent(mContext, CaptureActivity.class);
        startActivityForResult(intent,REQUEST_CODE_SCAN);
    }

    @Override
    public void onOverListener() {
        if (mAdapter.getOrdersData().size()>0){
            Intent intent = new Intent(mContext,GoodsListDetailActivity.class);
            intent.putExtra("data", JSON.toJSONString(mAdapter.getOrdersData()));
            startActivity(intent);
        }
    }

    private class FinishBroadcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (GOODS_LIST_FINISH.equals(action)){
                mContext.finish();
            }
        }
    }

}
