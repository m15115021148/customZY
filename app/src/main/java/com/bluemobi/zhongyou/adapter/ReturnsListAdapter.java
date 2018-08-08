package com.bluemobi.zhongyou.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bluemobi.zhongyou.R;
import com.bluemobi.zhongyou.bean.OrderBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${chenM} on ${2017}.
 */
public class ReturnsListAdapter extends RecyclerView.Adapter<ReturnsListAdapter.Holder>{
    private Context mContext;
    private List<OrderBean> mList = new ArrayList<>();
    private OnReturnsCallBack mCallBack;

    public ReturnsListAdapter(Context context, OnReturnsCallBack callBack){
        this.mContext = context;
        this.mCallBack = callBack;
    }

    public interface OnReturnsCallBack {
        void onItemContentListener(int position);
        void onReturnsListener(int position);
        void onPrintTicketListener(int position);
    }

    public void setData(List<OrderBean> list){
        this.mList = list;
        this.notifyDataSetChanged();
    }

    public List<OrderBean> getData(){
        return this.mList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.returns_list_item_layout,null,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.initData(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        @BindView(R.id.layout)
        public LinearLayout mLayout;
        @BindView(R.id.comment_recyclerView)
        public RecyclerView mRecyclerView;
        private CommentAdapter mAdapter;
        @BindView(R.id.orderNumber)
        public TextView mOrderNumber;
        @BindView(R.id.orderTime)
        public TextView mOrderTime;
        @BindView(R.id.orderTotalPrice)
        public TextView mOrderTotalPrice;
        @BindView(R.id.oilCardMoney)
        public TextView mOilCardMoney;
        @BindView(R.id.otherMoney)
        public TextView mOtherMoney;
        @BindView(R.id.operating)
        public TextView mOperating;//returns
        @BindView(R.id.printTicket)
        public TextView mPrintTicket;//print ticket

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void initData(final int position){
            OrderBean model = mList.get(position);

            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext) {
                @Override
                public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                    return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                }
            };
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(layoutManager);
            mAdapter = new CommentAdapter();
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setData(model.getData(),model.getType());

            mOrderNumber.setText(
                    String.format(
                            mContext.getResources().getString(R.string.order_number),
                            model.getOrderNumber()
                    )
            );
            mOrderTime.setText(model.getOrderTime());
            mOrderTotalPrice.setText(String.valueOf(model.getTotalPrice()));

            mOilCardMoney.setText(
                    String.format(
                            mContext.getResources().getString(R.string.returns_oil_card_money),
                            20.00
                    )
            );

            mOtherMoney.setText(
                    String.format(
                            mContext.getResources().getString(R.string.returns_other_money),
                            30.00
                    )
            );

            mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCallBack!=null)mCallBack.onItemContentListener(position);
                }
            });

            mOperating.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCallBack!=null)mCallBack.onReturnsListener(position);
                }
            });

            mPrintTicket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCallBack!=null)mCallBack.onPrintTicketListener(position);
                }
            });
        }
    }
}
