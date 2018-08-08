package com.bluemobi.zhongyou.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluemobi.zhongyou.R;
import com.bluemobi.zhongyou.bean.CouponBean;
import com.bluemobi.zhongyou.bean.OrderBean;
import com.bluemobi.zhongyou.bean.TypeBean;

import java.util.List;

import butterknife.BindView;

/**
 * Created by ${chenM} on 2018/7/16.
 */
public class PayCouponOilCardAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private SparseArray mList = new SparseArray<>();
    private static final int VIEW_TYPE_HEADER = 0x001;
    private static final int VIEW_TYPE_BODY = 0x002;
    private static final int VIEW_TYPE_FOOTER = 0x003;

    private PaymentMethodAdapter.OnPaymentMethodCallBack mCallBack;

    public PayCouponOilCardAdapter(PaymentMethodAdapter.OnPaymentMethodCallBack callBack){
        this.mCallBack = callBack;
    }

    public void setData(SparseArray array) {
        this.mList = array;
        this.notifyDataSetChanged();
    }

    public SparseArray getData() {
        return this.mList;
    }

    public void clear(){
        this.mList.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pay_coupon_header_layout,parent,false);
            return new HeaderHolder(view);
        } else if (viewType == VIEW_TYPE_BODY) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pay_coupon_body_layout,parent,false);
            return new BodyHolder(view);
        } else if (viewType == VIEW_TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pay_coupon_footer_layout,null);
            return new FooterHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.initData(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_HEADER;
        } else if (position == 1) {
            return VIEW_TYPE_BODY;
        } else if (position == 2) {
            return VIEW_TYPE_FOOTER;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class HeaderHolder extends BaseViewHolder {
        @BindView(R.id.title)
        public TextView mTitle;
        @BindView(R.id.orderStatus)
        public TextView mStatus;
        @BindView(R.id.type)
        public TextView mType;
        @BindView(R.id.name)
        public TextView mName;
        @BindView(R.id.number)
        public TextView mNumber;
        @BindView(R.id.recyclerView)
        public RecyclerView mRecyclerView;
        private CommentAdapter mAdapter;
        private View mItemView;
        @BindView(R.id.orderTotalPrice)
        public TextView mTotalPrice;

        public HeaderHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            mRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            mAdapter = new CommentAdapter();
            mRecyclerView.setAdapter(mAdapter);
        }

        @Override
        public void initData(int pos) {
            OrderBean model = (OrderBean) mList.get(pos);
            mTitle.setText(String.format(mItemView.getContext().getResources().getString(R.string.order_number),model.getOrderNumber()));
            mStatus.setText(model.getOrderTime());
            if (model.getType() == 0){
                mType.setText(mItemView.getResources().getString(R.string.order_oil_gun));
                mName.setText(mItemView.getResources().getString(R.string.order_oil_number));
                mNumber.setText(mItemView.getResources().getString(R.string.order_number_of_liters));
            }else {
                mType.setText(mItemView.getResources().getString(R.string.order_goods_type));
                mName.setText(mItemView.getResources().getString(R.string.order_goods_name));
                mNumber.setText(mItemView.getResources().getString(R.string.order_goods_number));
            }
            mAdapter.setData(model.getData(),model.getType());
            mTotalPrice.setText(String.valueOf(model.getTotalPrice()));
        }
    }

    public class BodyHolder extends BaseViewHolder {
        @BindView(R.id.offerMoney)
        public TextView mOfferMoney;
        @BindView(R.id.realMoney)
        public TextView mRealMoney;
        private View mItemView;
        @BindView(R.id.oilCardMoney)
        public TextView mOilCardMoney;

        public BodyHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
        }

        @Override
        public void initData(int pos) {
            CouponBean bean = (CouponBean) mList.get(pos);
            mOfferMoney.setVisibility(bean.getCouponMoney()==0?View.GONE:View.VISIBLE);
            mOfferMoney.setText(String.format(mItemView.getResources().getString(R.string.pay_offer_money),bean.getCouponMoney()));

            mOilCardMoney.setVisibility(bean.getOilCardMoney()==0?View.GONE:View.VISIBLE);
            mOilCardMoney.setText(String.format(mItemView.getResources().getString(R.string.pay_success_oil_card_money),bean.getOilCardMoney()));

            mRealMoney.setText(String.format(mItemView.getResources().getString(R.string.pay_real_money),bean.getPaymentMoney()));
        }
    }

    public class FooterHolder extends BaseViewHolder {
        @BindView(R.id.recyclerView)
        public RecyclerView mRecyclerView;
        private PaymentMethodAdapter mPaymentAdapter;

        public FooterHolder(View itemView) {
            super(itemView);
            mRecyclerView.setLayoutManager(new GridLayoutManager(itemView.getContext(),3));
            mPaymentAdapter = new PaymentMethodAdapter(mCallBack);
            mRecyclerView.setAdapter(mPaymentAdapter);
        }

        @Override
        public void initData(int pos) {
            List<TypeBean> list = (List<TypeBean>) mList.get(pos);
            mPaymentAdapter.setData(list);
        }
    }
}
