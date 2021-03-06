package com.bluemobi.zhongyou.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bluemobi.zhongyou.R;
import com.bluemobi.zhongyou.fragment.BaseFragment;
import com.bluemobi.zhongyou.bean.TypeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${chenM} on ${2017}.
 */
public class UnpaidFragmentAdapter extends FragmentPagerAdapter {
    private List<TypeBean> mList;
    private List<BaseFragment> mFragmentList;
    private Context mContext;

    public UnpaidFragmentAdapter(FragmentManager fm, String[] data, List<BaseFragment> fragments, Context context) {
        super(fm);
        this.mList = getData(data);
        this.mFragmentList = fragments;
        this.mContext = context;
    }

    private List<TypeBean> getData(String[] data){
        List<TypeBean> list = new ArrayList<>();
        for (int i=0;i<data.length;i++){
            TypeBean model = new TypeBean();
            model.setName(data[i]);
            list.add(model);
        }
        return list;
    }

    public View getView(int pos){
        View view = LayoutInflater.from(mContext).inflate(R.layout.tablayout_unpaid_item,null);
        TextView name = (TextView) view.findViewById(R.id.name);
        name.setText(mList.get(pos).getName());
        view.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT
        ));
        return view;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    //配置标题的方法
    @Override
    public CharSequence getPageTitle(int position) {
        return mList.get(position).getName();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
