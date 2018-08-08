package com.bluemobi.zhongyou.adapter;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bluemobi.zhongyou.R;
import com.bluemobi.zhongyou.bean.OrderBean;
import com.bluemobi.zhongyou.view.keyboard.CustomKeyboardHelper;
import com.bluemobi.zhongyou.view.recyclerview.LRecyclerView;
import com.bluemobi.zhongyou.view.recyclerview.SwipeMenuView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${chenM} on ${2017}.
 */
public class GoodsListAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private Activity mActivity;
    private SparseArray mList = new SparseArray();
    private OnGoodsListCallBack mCallBack;

    public GoodsListAdapter(Activity activity, OnGoodsListCallBack callBack) {
        this.mActivity = activity;
        this.mCallBack = callBack;
    }

    private static final int VIEW_TYPE_BODY = 0x001;
    private static final int VIEW_TYPE_FOOTER = 0x002;

    public interface OnGoodsListCallBack {
        void onItemClickListener(int position);

        void onItemDeleteListener(SwipeMenuView view, int position);

        void onContinueScanListener();

        void onOverListener();
    }

    public void setData(SparseArray array) {
        this.mList = array;
        this.notifyDataSetChanged();
    }

    public SparseArray getData() {
        return mList;
    }

    public List<OrderBean.Orders> getOrdersData(){
        OrderBean bean = (OrderBean) mList.get(0);
        return bean!=null?bean.getData():new ArrayList<OrderBean.Orders>();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_BODY) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goods_list_body_layout, parent, false);
            return new BodyHolder(view);
        } else if (viewType == VIEW_TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goods_list_footer_layout, parent, false);
            return new FooterHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.initData(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_BODY;
        } else if (position == 1) {
            return VIEW_TYPE_FOOTER;
        }
        return super.getItemViewType(position);
    }

    public class BodyHolder extends BaseViewHolder {
        private View mItemView;
        @BindView(R.id.recyclerView)
        public LRecyclerView mRecyclerView;
        private GoodsListBodyAdapter mAdapter;

        public BodyHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mItemView.getContext()));
            mAdapter = new GoodsListBodyAdapter(mActivity, mCallBack);
            mRecyclerView.setAdapter(mAdapter);
        }

        @Override
        public void initData(int pos) {
            OrderBean bean = (OrderBean) mList.get(0);
            mAdapter.setData(bean.getData());
        }
    }

    public class FooterHolder extends BaseViewHolder {
        private View mItemView;
        @BindView(R.id.continueScan)
        public TextView mContinueScan;
        @BindView(R.id.over)
        public TextView mOver;

        public FooterHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
        }

        @Override
        public void initData(int pos) {
            mContinueScan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCallBack != null) mCallBack.onContinueScanListener();
                }
            });
            mOver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCallBack != null) mCallBack.onOverListener();
                }
            });
        }
    }
}
