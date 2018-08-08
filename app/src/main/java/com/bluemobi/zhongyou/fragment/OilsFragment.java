package com.bluemobi.zhongyou.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bluemobi.zhongyou.R;
import com.bluemobi.zhongyou.adapter.UnpaidOilsAdapter;
import com.bluemobi.zhongyou.activity.PaymentMethodActivity;
import com.bluemobi.zhongyou.bean.OrderBean;
import com.bluemobi.zhongyou.util.ToastUtil;
import com.bluemobi.zhongyou.view.recyclerview.EmptyWrapper;
import com.bluemobi.zhongyou.view.recyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ${chenM} on ${2017}.
 */
public class OilsFragment extends BaseFragment implements UnpaidOilsAdapter.OnUnpaidOilsCallBack,
        PullLoadMoreRecyclerView.PullLoadMoreListener{
    private Context mContext;
    @BindView(R.id.recyclerView)
    public PullLoadMoreRecyclerView mRecyclerView;
    private UnpaidOilsAdapter mAdapter;
    private EmptyWrapper mEmptyAdapter;

    @Override
    protected int setContentView() {
        return R.layout.fragment_oils;
    }

    @Override
    protected void startLoad() {
//        if (mAdapter!=null)mAdapter.setData(getData());
    }

    @Override
    protected void initData() {
        mContext = getContext();
        initRecyclerViewData();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ToastUtil.showBottomShort("finish complete");
            mRecyclerView.setPullLoadMoreCompleted();
        }
    };

    private void initRecyclerViewData() {
        mRecyclerView.setLinearLayout();
        mRecyclerView.setFooterViewBackgroundColor(R.color.pop_txt_bg);
        mRecyclerView.setOnPullLoadMoreListener(this);
        mAdapter = new UnpaidOilsAdapter(mContext, this);

        mEmptyAdapter = new EmptyWrapper(mAdapter);
        View empty = LayoutInflater.from(mContext).inflate(R.layout.empty_layout, null);
        empty.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        ));
        TextView e = empty.findViewById(R.id.empty);
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAdapter != null) mAdapter.setData(getData());
                mEmptyAdapter.notifyDataSetChanged();
            }
        });
        mEmptyAdapter.setEmptyView(empty);
        mRecyclerView.setAdapter(mEmptyAdapter);
    }

    @Override
    public void onItemClickListener(int position) {
        Intent intent = new Intent(mContext, PaymentMethodActivity.class);
        intent.putExtra("model", mAdapter.getData().get(position));
        startActivity(intent);
    }

    public List<OrderBean> getData() {
        List<OrderBean> list = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            OrderBean model = new OrderBean();
            model.setOrderNumber(123456 + i);
            model.setOrderTime("2018.05.30 17:1" + i + ":20");
            model.setType(0);

            List<OrderBean.Orders> data = new ArrayList<>();
            OrderBean.Orders bean = new OrderBean.Orders();
            bean.setType(String.valueOf(i+1));
            bean.setName(String.valueOf(90+i));
            bean.setNumber(6+i);
            bean.setPrice(10.0);
            data.add(bean);

            model.setData(data);
            model.setTotalPrice(bean.getPrice()*bean.getNumber());
            list.add(model);
        }
        return list;
    }

    @Override
    public void onRefresh() {
        mHandler.sendEmptyMessageDelayed(1001,5000);
    }

    @Override
    public void onLoadMore() {
        mHandler.sendEmptyMessageDelayed(1001,5000);
    }
}
