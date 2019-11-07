package com.taxidriver.santos.view.adapter;



import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Created by zhizhongbiao on 2018/6/13.
 */

public interface OnRvItemLongClickedListener<D,A extends RecyclerView.Adapter> {

    boolean onItemLongClick(A adapter, List<D> datas, int pos);
}
