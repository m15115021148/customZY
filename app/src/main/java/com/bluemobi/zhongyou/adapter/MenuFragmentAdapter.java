package com.bluemobi.zhongyou.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluemobi.zhongyou.R;
import com.bluemobi.zhongyou.fragment.BaseFragment;
import com.bluemobi.zhongyou.bean.TypeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${chenM} on ${2017}.
 */
public class MenuFragmentAdapter extends FragmentPagerAdapter {
    private List<TypeBean> mList;
    private List<BaseFragment> mFragmentList;
    private Context mContext;
    private int[] mRes = {R.color.title_bg,R.color.title_bg,R.color.title_bg};

    public MenuFragmentAdapter(FragmentManager fm, String[] data, List<BaseFragment> fragments, Context context) {
        super(fm);
        this.mList = getData(data);
        this.mFragmentList = fragments;
        this.mContext = context;
    }

    private List<TypeBean> getData(String[] data){
        List<TypeBean> list = new ArrayList<>();
        for (int i=0;i<data.length;i++){
            TypeBean model = new TypeBean();
            model.setRes(mRes[i]);
            model.setName(data[i]);
            list.add(model);
        }
        return list;
    }

    public View getView(int pos){
        View view = LayoutInflater.from(mContext).inflate(R.layout.tablayout_title_item_layout,null);
        TextView name = (TextView) view.findViewById(R.id.name);
        ImageView img = (ImageView) view.findViewById(R.id.img);
        name.setText(mList.get(pos).getName());
        img.setImageResource(mList.get(pos).getRes());
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
