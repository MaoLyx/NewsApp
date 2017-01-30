package com.maohongyu.newsapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.maohongyu.newsapp.R;
import com.maohongyu.newsapp.model.CategoryBean;
import com.maohongyu.newsapp.model.ResponseBean;

import java.util.List;

/**
 * Created by hongyu on 2017/1/25 下午9:05.
 * 作用：带实现
 */

public class ContentAdapter extends BaseAdapter {


    private static final String TAG = "ContentAdapter";

    private List<ResponseBean.ResultBean.DataBean> datas;

    private CategoryBean.Category currentCategory;

    private Context mContext;

    public ContentAdapter(List<ResponseBean.ResultBean.DataBean> datas, CategoryBean.Category category, Context context) {
        this.datas      = datas;
        currentCategory = category;
        this.mContext = context;

    }


    public void reSetNewsData(List<ResponseBean.ResultBean.DataBean> datas,CategoryBean.Category category){
        if (currentCategory != null) {
            if (!currentCategory.getType().equals(category.getType())) {
                this.datas      = datas;
                currentCategory = category;
            }
        }else {
            this.datas      = datas;
            currentCategory = category;
        }
    }

    @Override
    public int getCount() {
        if (datas.isEmpty() || datas == null) {
            return 0;
        }
        return datas.size();
    }

    @Override
    public ResponseBean.ResultBean.DataBean getItem(int position) {
        ResponseBean.ResultBean.DataBean dataBean = datas.get(position);
        return dataBean;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold viewHold = null;
        if (convertView == null) {
            Log.i(TAG, "getView: ");
            convertView = LayoutInflater.from(mContext).inflate(R.layout.news_item,null);
            viewHold = new ViewHold(convertView);
            convertView.setTag(viewHold);
        }else {
            viewHold = (ViewHold) convertView.getTag();
        }
        viewHold.newImage_sv.setImageURI(getItem(position).getThumbnail_pic_s());
        viewHold.newsTitle_tv.setText(getItem(position).getTitle());
        viewHold.newsAuthor_tv.setText(getItem(position).getAuthor_name());
        viewHold.newsDate_tv.setText(getItem(position).getDate());
        return convertView;
    }

    class ViewHold{
        private SimpleDraweeView newImage_sv;
        private TextView newsTitle_tv,newsAuthor_tv,newsDate_tv;

        public ViewHold(View view) {
            this.newImage_sv = (SimpleDraweeView) view.findViewById(R.id.newImage_sv);
            this.newsTitle_tv = (TextView) view.findViewById(R.id.newsTitle_tv);
            this.newsAuthor_tv = (TextView) view.findViewById(R.id.newsAuthor_tv);
            this.newsDate_tv = (TextView) view.findViewById(R.id.newsDate_tv);
        }
    }
}
