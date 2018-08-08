package com.bluemobi.zhongyou.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluemobi.zhongyou.R;
import com.bluemobi.zhongyou.bean.PaidBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${chenM} on ${2017}.
 */
public class PaidListItemAdapter extends RecyclerView.Adapter<PaidListItemAdapter.Holder>{
    private Context mContext;
    private List<PaidBean.Paid> mList;

    public PaidListItemAdapter(Context context){
        this.mContext = context;
    }

    public void setData(List<PaidBean.Paid> list){
        this.mList = list;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_recyclerview_item_layout,null);
        return new Holder(v);
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
        @BindView(R.id.orderGoodsType)
        public TextView mGoodType;
        @BindView(R.id.orderGoodsName)
        public TextView mGoodsName;
        @BindView(R.id.orderPrice)
        public TextView mPrice;
        @BindView(R.id.orderGoodsNumber)
        public TextView mGoodsNumber;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void initData(int position){
            PaidBean.Paid goods = mList.get(position);

            mGoodType.setText(goods.getCategory());
            mGoodsName.setText(goods.getName());
            mPrice.setText(String.valueOf(goods.getPrice()));
            mGoodsNumber.setText(
                    String.valueOf(goods.getNumber())
            );

        }
    }
}
