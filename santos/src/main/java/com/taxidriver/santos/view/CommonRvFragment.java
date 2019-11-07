package com.taxidriver.santos.view;


import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.taxidriver.santos.R;
import com.taxidriver.santos.R2;
import com.taxidriver.santos.presenter.IPresenter;
import com.taxidriver.santos.utils.log.LogUtils;
import com.taxidriver.santos.view.adapter.OnRvItemClickedListener;
import com.taxidriver.santos.view.adapter.XBaseAdapter;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import butterknife.BindView;


/**
 * Filename :  CommonRvFragment
 * Author   :  zhizhongbiao
 * Date     :  2018/6/29
 * Describe :
 */

public abstract class CommonRvFragment<P extends IPresenter, A extends XBaseAdapter, D> extends MvpFragment<P>
        implements OnRvItemClickedListener<D, A> {

    @BindView(R2.id.rv)
    RecyclerView rv;
    @BindView(R2.id.ivLoading)
    ImageView ivLoading;
    @BindView(R2.id.tvLoading)
    TextView tvLoading;
    @BindView(R2.id.flLoading)
    FrameLayout flLoading;
    @BindView(R2.id.tvEmpty)
    TextView tvEmpty;
    @BindView(R2.id.flContainer)
    FrameLayout flContainer;


    protected LinearLayoutManager layoutManager;
    protected A adapter;

    protected abstract int getType();

    /**
     * 子类可复写该方法创建自己扩展的Adapter
     *
     * @return
     */
    protected A getAdapter() {
        if (adapter == null) {
            ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
            Class<? extends XBaseAdapter> adpterClass = (Class<? extends XBaseAdapter>) type.getActualTypeArguments()[1];
            try {
                A instance = (A) adpterClass.newInstance();
                instance.initSetup(getActivity(), getType(), this);
                LogUtils.e("getAdapter  succeed  adapter=" + adapter);
                return instance;
            } catch (Exception e) {
                LogUtils.e("getAdapter  failed  e.getMessage()=" + e.getMessage());
                e.printStackTrace();
            }
        }
        return adapter;
    }

    @Override
    public int getViewLayout() {
        return R.layout.fragment_common_rv;
    }


    @Override
    protected void initView(Bundle savedInstanceState, Bundle args) {
        initViews();
    }

    @Override
    public void onDestroyView() {
        adapter.setOnRvItemClickedListener(null);
        super.onDestroyView();
    }

    private void initViews() {
        layoutManager = new LinearLayoutManager(getContext());
//        rv.setLayoutManager(layoutManager);
        adapter = getAdapter();
//        rv.setAdapter(adapter);

    }

    /**
     * 子类可以复写该方法进行数据显示
     *
     * @param datas
     */
    protected void updateDataList(List<D> datas) {
        getAdapter().setData(datas);
        LogUtils.e("updateDataList   adapter=" + adapter + "      Thread=" + Thread.currentThread().getName() + "    datas=" + datas);
    }


}
