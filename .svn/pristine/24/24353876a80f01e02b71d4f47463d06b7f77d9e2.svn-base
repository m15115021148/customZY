package com.bluemobi.zhongyou.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bluemobi.zhongyou.R;
import com.bluemobi.zhongyou.bean.TypeBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${chenM} on ${2017}.
 */
public class PaymentMethodAdapter extends RecyclerView.Adapter<PaymentMethodAdapter.Holder>{
    private List<TypeBean> mList = new ArrayList<>();
    private OnPaymentMethodCallBack mCallBack;

    public PaymentMethodAdapter(OnPaymentMethodCallBack callBack){
        this.mCallBack = callBack;
    }

    public interface OnPaymentMethodCallBack{
        void onItemPaymentListener(int position);
    }

    public void setData(List<TypeBean> list){
        this.mList = list;
        this.notifyDataSetChanged();
    }

    public List<TypeBean> getData(){
        return this.mList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_method_item_layout,null);
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

    public class Holder extends BaseViewHolder{
        @BindView(R.id.layout)
        public LinearLayout mLayout;
        @BindView(R.id.img)
        public ImageView mImg;
        @BindView(R.id.name)
        public TextView mName;
        private int width;

        public Holder(View itemView) {
            super(itemView);
            DisplayMetrics dm = itemView.getContext().getResources().getDisplayMetrics();
            width = dm.widthPixels;
            mLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    width/3,width/3
            ));
        }

        @Override
        public void initData(final int position){
            TypeBean model = mList.get(position);
            mName.setText(model.getName());
            mImg.setImageResource(model.getRes());

            mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCallBack!=null)mCallBack.onItemPaymentListener(position);
                }
            });
        }
    }
}
