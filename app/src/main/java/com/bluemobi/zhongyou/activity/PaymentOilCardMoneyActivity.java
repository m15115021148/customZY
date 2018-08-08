package com.bluemobi.zhongyou.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bluemobi.zhongyou.R;
import com.bluemobi.zhongyou.bean.CouponBean;
import com.bluemobi.zhongyou.mvp.presenter.base.Presenter;
import com.bluemobi.zhongyou.util.ToastUtil;
import com.bluemobi.zhongyou.view.keyboard.CustomKeyboardHelper;

import butterknife.BindView;

public class PaymentOilCardMoneyActivity extends BaseActivity  {
    private PaymentOilCardMoneyActivity mContext;
    @BindView(R.id.title)
    public TextView mTitle;
    @BindView(R.id.inputMoney)
    public EditText mInputMoney;
    private CustomKeyboardHelper mHelper;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_payment_oil_card_money;
    }

    @Override
    protected Presenter getModelView() {
        return null;
    }

    @Override
    protected void initData() {
        mContext = this;
        mTitle.setText(R.string.pay_oil_card_title);
        mHelper = new CustomKeyboardHelper(mContext);
        mHelper.registerEditText(mInputMoney);
    }

    public void onNextWay(View view) {
        if (TextUtils.isEmpty(mInputMoney.getText().toString())){
            ToastUtil.showBottomShort(getResources().getString(R.string.pay_oil_card_flag));
            return;
        }
        Intent intent = new Intent(mContext,PayCouponOilCardActivity.class);
        intent.putExtra("model",getIntent().getSerializableExtra("model"));
        intent.putExtra("data", getIntent().getStringExtra("data"));
        intent.putExtra("CouponBean",getIntent().getSerializableExtra("CouponBean"));
        intent.putExtra("oilCardMoney",Double.parseDouble(mInputMoney.getText().toString()));
        startActivity(intent);
        mContext.finish();
    }
}
