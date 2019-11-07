package com.taxidriver.santos.view.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;

import androidx.recyclerview.widget.RecyclerView;

import com.taxidriver.santos.utils.log.LogUtils;
import com.taxidriver.santos.view.hover_view.ListBaseAdapter;

import java.util.HashMap;
import java.util.List;

/**
 * Filename :  XBaseAdapter
 * Author   :  zhizhongbiao
 * Date     :  2018/6/29
 * Describe :
 */

public abstract class XBaseAdapter<VH extends RecyclerView.ViewHolder, D>
        extends ListBaseAdapter<VH>
        implements View.OnClickListener
        , SectionIndexer {


    protected Context context;
    protected HashMap<Character, Integer> indexMap = new HashMap<>();
    private List<D> datas;
    private int type;
    private OnRvItemClickedListener onRvItemClickedListener;
    private int selectedPos;

    public XBaseAdapter() {
    }

    public XBaseAdapter(Context context, int type, OnRvItemClickedListener onRvItemClickedListener) {
        initSetup(context, type, onRvItemClickedListener);
    }

    public void setOnRvItemClickedListener(OnRvItemClickedListener onRvItemClickedListener) {
        this.onRvItemClickedListener = onRvItemClickedListener;
    }

    protected abstract VH getViewHolder(View itemView);

    protected abstract int getItemLayout();

    protected abstract void updataItemViewData(VH holder, int position, boolean isSelectedPos);

    public void initSetup(Context context, int type, OnRvItemClickedListener onRvItemClickedListener) {
        this.context = context;
        this.type = type;
        this.onRvItemClickedListener = onRvItemClickedListener;
    }

    public List<D> getData() {
        return datas;
    }

    public void setData(List<D> dataList) {
        LogUtils.e("before setData  datas=" + datas);
        this.datas = dataList;
        notifyDataSetChanged();
        LogUtils.e("after setData  datas=" + datas);

    }

    public void clearData() {
        if (datas != null) {
            datas.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(getItemLayout(), parent, false);
        View parentView = itemView.findViewById(getItemViewResId());
        View childView = itemView.findViewById(getItemChildViewResId());
        if (parentView != null) {
            parentView.setOnClickListener(this);
        }

        if (childView != null) {
            childView.setOnClickListener(this);
        }

        VH viewHolder = getViewHolder(itemView);
        return viewHolder;
    }

    protected int getItemChildViewResId() {
        return -1;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.itemView.setTag(position);
        updataItemViewData(holder, position, getSelectedPos() == position);
    }


    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }


    @Override
    public int getItemViewType(int position) {
        return type;
    }

    @Override
    public void onClick(View v) {
        int itemViewResId = getItemViewResId();
        if (v.getId() == itemViewResId) {
            if (onRvItemClickedListener != null) {
                Object tag = v.getTag();
                LogUtils.e("tag=" + tag);
                onRvItemClickedListener.onItemClick(this, datas, (Integer) tag);
            }
        }

    }

    protected abstract int getItemViewResId();


    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        char sortKey = (char) sectionIndex;
        if (indexMap.containsKey(sortKey)) {
            return indexMap.get(sortKey);
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }


    public int getSelectedPos() {
        return selectedPos;
    }

    public void setSelectedPos(int selectedPos) {
        this.selectedPos = selectedPos;
    }


}
