package com.bluemobi.zhongyou.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bluemobi.zhongyou.R;
import com.bluemobi.zhongyou.log.LogUtil;
import com.bluemobi.zhongyou.activity.CaptureActivity;
import com.bluemobi.zhongyou.activity.GoodsListActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ${chenM} on ${2017}.
 */
public class GoodsFragment extends BaseFragment  {
    private Context mContext;
    @BindView(R.id.recyclerView)
    public RecyclerView mRecyclerView;
    @BindView(R.id.scan)
    public TextView mScan;

    private static final int REQUEST_CODE_SCAN = 0x001;

    @Override
    protected int setContentView() {
        return R.layout.fragment_goods;
    }

    @Override
    protected void startLoad() {

    }

    @Override
    protected void initData() {
        mContext = getContext();

        mScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CaptureActivity.class);
                startActivityForResult(intent,REQUEST_CODE_SCAN);
//                Intent intent = new Intent("com.summi.scan");
//                intent.setPackage("com.sunmi.sunmiqrcodescanner");
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK){
            if (data != null) {
                String content = data.getStringExtra(CaptureActivity.RESULT_CONTENT);
                LogUtil.d(content);
                Intent intent = new Intent(mContext, GoodsListActivity.class);
                startActivity(intent);
            }
        }
    }
}
