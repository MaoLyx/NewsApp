package com.maohongyu.newsapp.view.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.maohongyu.newsapp.adapter.ContentAdapter;
import com.maohongyu.newsapp.model.ResponseBean;
import com.maohongyu.newsapp.view.detial.DetialActivity;

import java.util.List;

/**
 * Created by hongyu on 2017/2/7 下午3:59.
 * 作用：
 */

public class ContentsFragment extends Fragment implements IContentView {

    private static final String TAG = "ContentsFragment";

    private static final String BUNBLE_CATEGORY = "category";

    private ContentAdapter adapter;

    private boolean isLoad;


    public static ContentsFragment newInstance() {
        Bundle args = new Bundle();
        ContentsFragment fragment = new ContentsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
        super.onCreate(savedInstanceState);
        adapter = new ContentAdapter(null, getContext());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() called with: inflater = [" + inflater + "], container = [" + container + "], savedInstanceState = [" + savedInstanceState + "]");
        ListView listView = new ListView(getContext());
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        listView.setLayoutParams(lp);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ResponseBean.ResultBean.DataBean item = adapter.getItem(position);
                Intent intent = new Intent(getContext(), DetialActivity.class);
                intent.putExtra("url", item.getUrl());
                intent.putExtra("img", item.getThumbnail_pic_s());
                intent.putExtra("author", item.getAuthor_name());
                intent.putExtra("title", item.getTitle());
                getActivity().startActivity(intent);
            }
        });
        return listView;
    }

    @Override
    public void addNewsContent(List<ResponseBean.ResultBean.DataBean> data) {
        if (data != null &&adapter!=null) {
            Log.d(TAG, "addNewsContent() called with: data = [" + data + "]");
               adapter.addData(data);
               adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean isLoad() {
        return isLoad;
    }

    @Override
    public void setLoad(boolean load) {
        isLoad = load;
    }
}
