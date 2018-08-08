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
import com.bluemobi.zhongyou.bean.PaidBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${chenM} on ${2017}.
 */
public class PaidListAdapter extends RecyclerView.Adapter<PaidListAdapter.Holder>{
    private Context mContext;
    private List<PaidBean> mList = new ArrayList<>();
    private OnPaidCallBack mCallBack;

    public PaidListAdapter(Context context, OnPaidCallBack callBack){
        this.mContext = context;
        this.mCallBack = callBack;
    }

    public interface OnPaidCallBack {
        void onItemContentListener(int position);
        void onPrintTicketListener(int position);
    }

    public void setData(List<PaidBean> list){
        this.mList = list;
        this.notifyDataSetChanged();
    }

    public List<PaidBean> getData(){
        return this.mList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.paid_list_item_layout,null,false);
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
        private PaidListItemAdapter mAdapter;
        @BindView(R.id.orderNumber)
        public TextView mOrderNumber;
        @BindView(R.id.orderTime)
        public TextView mOrderTime;
        @BindView(R.id.orderTotalPrice)
        public TextView mOrderTotalPrice;
        @BindView(R.id.categoryKey)
        public TextView mCategory;
        @BindView(R.id.nameKey)
        public TextView mName;
        @BindView(R.id.numberKey)
        public TextView mNumber;

        @BindView(R.id.oilCardMoney)
        public TextView mOilCardMoney;
        @BindView(R.id.otherMoney)
        public TextView mOtherMoney;
        @BindView(R.id.operating)
        public TextView mPrint;//print ticket

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void initData(final int position){
            PaidBean model = mList.get(position);
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext) {
                @Override
                public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                    return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                }
            };
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(layoutManager);
            mAdapter = new PaidListItemAdapter(mContext);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setData(model.getData());

            mOrderNumber.setText(
                    String.format(
                            mContext.getResources().getString(R.string.order_number),
                            model.getOrderNumber()
                    )
            );
            mOrderTime.setText(model.getOrderTime());
            mCategory.setText(
                    model.getType()==0?
                    mContext.getResources().getString(R.string.order_oil_gun):
                    mContext.getResources().getString(R.string.order_goods_type)
            );
            mName.setText(
                    model.getType()==0?
                    mContext.getResources().getString(R.string.order_oil_number):
                    mContext.getResources().getString(R.string.order_goods_name)
            );
            mNumber.setText(
                    model.getType()==0?
                    mContext.getResources().getString(R.string.order_number_of_liters):
                    mContext.getResources().getString(R.string.order_goods_number)
            );

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

            mPrint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCallBack!=null)mCallBack.onPrintTicketListener(position);
                }
            });
        }
    }
}
