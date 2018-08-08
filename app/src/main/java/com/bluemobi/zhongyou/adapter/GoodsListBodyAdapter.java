package com.bluemobi.zhongyou.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bluemobi.zhongyou.R;
import com.bluemobi.zhongyou.bean.OrderBean;
import com.bluemobi.zhongyou.log.LogUtil;
import com.bluemobi.zhongyou.view.keyboard.CustomKeyboardHelper;
import com.bluemobi.zhongyou.view.recyclerview.SwipeMenuView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ${chenM} on 2018/7/19.
 */
public class GoodsListBodyAdapter extends RecyclerView.Adapter<BaseViewHolder>{
    private Activity mContext;
    private List<OrderBean.Orders> mList = new ArrayList<>();
    private CustomKeyboardHelper mHelper;
    private boolean isChange;
    private GoodsListAdapter.OnGoodsListCallBack mCallBack;

    public GoodsListBodyAdapter(Activity context,GoodsListAdapter.OnGoodsListCallBack callBack){
        this.mContext = context;
        this.mCallBack = callBack;
        mHelper = new CustomKeyboardHelper(mContext);
    }

    public void setData(List<OrderBean.Orders> list) {
        this.mList = list;
        this.notifyDataSetChanged();
    }

    public List<OrderBean.Orders> getData() {
        return this.mList;
    }

    public void clear(){
        mList.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.goods_list_item_layout, parent,false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.initData(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class Holder extends BaseViewHolder{
        @BindView(R.id.orderGoodsType)
        public TextView mGoodType;
        @BindView(R.id.orderGoodsName)
        public TextView mGoodsName;
        @BindView(R.id.orderPrice)
        public TextView mPrice;
        @BindView(R.id.orderGoodsNumber)
        public EditText mGoodsNumber;
        @BindView(R.id.orderGoodsTotalMoney)
        public TextView mTotalMoney;
        @BindView(R.id.swipeView)
        public SwipeMenuView mSwipe;
        @BindView(R.id.delete)
        public LinearLayout mDelete;
        @BindView(R.id.layout)
        public LinearLayout mLayout;

        public Holder(View itemView) {
            super(itemView);
        }

        @Override
        public void initData(final int position) {
            final OrderBean.Orders goods = mList.get(position);
            mSwipe.setSwipeEnable(true);

            mGoodType.setText(goods.getType());
            mGoodsName.setText(goods.getName());
            mPrice.setText(String.valueOf(goods.getPrice()));
            mGoodsNumber.setText(
                    String.valueOf(goods.getNumber())
            );
            mTotalMoney.setText(
                    String.valueOf(goods.getNumber() * goods.getPrice())
            );

            mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCallBack!=null)mCallBack.onItemClickListener(position);
                }
            });

            mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCallBack!=null)mCallBack.onItemDeleteListener(mSwipe,position);
                }
            });

            mGoodsNumber.requestFocus();
            mHelper.registerEditText(mGoodsNumber);
            mGoodsNumber.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    isChange = true;
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length()>0 && isChange){
                        goods.setNumber(Integer.parseInt(s.toString()));
                        mTotalMoney.setText(
                                String.valueOf(goods.getNumber() * goods.getPrice())
                        );
                    }
                }
            });

        }
    }
}
