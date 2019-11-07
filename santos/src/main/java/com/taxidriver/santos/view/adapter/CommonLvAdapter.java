package com.taxidriver.santos.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Author : WaterFlower.
 * Created on 2017/8/11.
 * Desc :
 */

public abstract class CommonLvAdapter<T> extends BaseAdapter implements ListDataHelper<T> {
    protected List<T> mDataList;
    protected Context mContext;
    protected int layoutResourceId;


    public CommonLvAdapter(List<T> mDataList, Context context, int layoutResourceId) {
        this.mDataList = mDataList;
        this.mContext = context;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public T getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return layoutResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListViewHolder holder = ListViewHolder.getViewHolder(mContext, parent, convertView, getItemViewType(position), position);
        bindData(mContext, holder, getItem(position));
        return holder.getRootView();
    }


    protected abstract void bindData(Context context, ListViewHolder viewHolder, T dataVo);

    @Override
    public List<T> getDataSet() {
        return mDataList;
    }

    @Override
    public void updateAll(List<T> newDatas) {
        mDataList = newDatas;
        notifyDataSetChanged();
    }

    @Override
    public void addAll(List<T> newDatas) {
        if (mDataList != null) {
            mDataList.addAll(newDatas);
            notifyDataSetChanged();
        }
    }

    @Override
    public void clear() {
        if (mDataList != null) {
            mDataList.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public void addHead(T data) {
        if (mDataList != null) {
            mDataList.add(0, data);
            notifyDataSetChanged();
        }
    }

    @Override
    public void append(T data) {
        if (mDataList != null) {
            mDataList.add(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public void replace(int index, T data) {
        if (mDataList != null) {
            mDataList.add(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public void replace(T oldData, T newData) {
        if (mDataList != null) {
            replace(mDataList.indexOf(oldData), newData);
            notifyDataSetChanged();
        }
    }

    @Override
    public void insert(int index, T data) {
        if (mDataList != null) {
            mDataList.add(index, data);
            notifyDataSetChanged();
        }
    }

    @Override
    public void remove(int index) {
        if (mDataList != null) {
            mDataList.remove(index);
            notifyDataSetChanged();
        }
    }

    @Override
    public void remove(T data) {
        if (mDataList != null) {
            mDataList.remove(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public void removeHead() {
        if (mDataList != null && mDataList.size() > 0) {
            mDataList.remove(0);
            notifyDataSetChanged();
        }
    }

    @Override
    public void removeTail() {
        if (mDataList != null && mDataList.size() > 0) {
            mDataList.remove(mDataList.size() - 1);
            notifyDataSetChanged();
        }
    }

    @Override
    public void removeAll(List<T> datas) {
        if (mDataList != null) {
            mDataList.removeAll(datas);
            notifyDataSetChanged();
        }
    }


}
