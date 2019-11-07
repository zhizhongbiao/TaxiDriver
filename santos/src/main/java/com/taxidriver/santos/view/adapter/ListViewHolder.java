package com.taxidriver.santos.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import com.taxidriver.santos.manager.ImageManager;
import com.taxidriver.santos.utils.ui.UIUtils;


/**
 * Author : WaterFlower.
 * Created on 2017/8/11.
 * Desc :
 */

public class ListViewHolder implements ViewHelper<ListViewHolder> {
    private int layoutResourceId;
    private int position;
    private Context mContext;
    SparseArray<View> mViews;
    View rootView;

    public ListViewHolder(Context context, ViewGroup parent, int layoutResourceId, int position) {
        this.mContext = context;
        this.layoutResourceId = layoutResourceId;
        this.position = position;
        rootView = UIUtils.inflate(layoutResourceId, parent);
        rootView.setTag(this);
        mViews = new SparseArray<>();
    }

    public static ListViewHolder getViewHolder(Context context, ViewGroup parent, View convertView, int layoutResourceId, int position) {
        ListViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ListViewHolder(context, parent, layoutResourceId, position);
        } else {
            viewHolder = (ListViewHolder) convertView.getTag();
            viewHolder.setPosition(position);
        }
        return viewHolder;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public <T extends View> T getViewById(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = rootView.findViewById(viewId);
            mViews.put(viewId, view);
        }

        return (T) view;
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public ListViewHolder setText(int viewId, String value) {
        TextView view = getViewById(viewId);
        view.setText(value);
        return this;
    }

    @Override
    public ListViewHolder setTextColor(int viewId, int color) {
        TextView view = getViewById(viewId);
        view.setTextColor(color);
        return this;
    }

    @Override
    public ListViewHolder setTextColorRes(int viewId, int colorRes) {
        TextView view = getViewById(viewId);
        view.setTextColor(mContext.getResources().getColor(colorRes));
        return this;
    }

    @Override
    public ListViewHolder setImageResource(int viewId, int imgResId) {
        ImageView view = getViewById(viewId);
        view.setImageResource(imgResId);
        return this;
    }

    @Override
    public ListViewHolder setBackgroundColor(int viewId, int color) {
        View view = getViewById(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    @Override
    public ListViewHolder setBackgroundColorRes(int viewId, int colorRes) {
        View view = getViewById(viewId);
        view.setBackgroundResource(colorRes);
        return this;
    }

    @Override
    public ListViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getViewById(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    @Override
    public ListViewHolder loadImageFromURL(int viewId, String url) {
        View view = getViewById(viewId);
        ImageManager.load(mContext, url, view);
        return this;
    }


    @Override
    public ListViewHolder setImageDrawableRes(int viewId, int drawableRes) {
        Drawable drawable = mContext.getResources().getDrawable(drawableRes);
        return setImageDrawable(viewId, drawable);
    }

    @Override
    public ListViewHolder setImageUrl(int viewId, String imgUrl) {
        return null;
    }

    @Override
    public ListViewHolder setImageBitmap(int viewId, Bitmap imgBitmap) {
        ImageView view = getViewById(viewId);
        view.setImageBitmap(imgBitmap);
        return this;
    }

    @Override
    public ListViewHolder setVisible(int viewId, boolean visible) {
        View view = getViewById(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    @Override
    public ListViewHolder setViewGone(int viewId) {
        View view = getViewById(viewId);
        view.setVisibility(View.GONE);
        return this;
    }

    @Override
    public ListViewHolder setViewVisible(int viewId) {
        View view = getViewById(viewId);
        view.setVisibility(View.VISIBLE);
        return this;
    }

    @Override
    public ListViewHolder setViewInVisible(int viewId) {
        View view = getViewById(viewId);
        view.setVisibility(View.INVISIBLE);
        return this;
    }


    @Override
    public ListViewHolder setTag(int viewId, Object tag) {
        View view = getViewById(viewId);
        view.setTag(tag);
        return this;
    }

    @Override
    public ListViewHolder setTag(int viewId, int key, Object tag) {
        View view = getViewById(viewId);
        view.setTag(key, tag);
        return this;
    }

    @Override
    public ListViewHolder setChecked(int viewId, boolean checked) {
        Checkable view = getViewById(viewId);
        view.setChecked(checked);
        return this;
    }

    @Override
    public ListViewHolder setAlpha(int viewId, float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getViewById(viewId).setAlpha(value);
        } else {
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getViewById(viewId).startAnimation(alpha);
        }
        return this;
    }

    @Override
    public ListViewHolder setTypeface(int viewId, Typeface typeface) {
        TextView view = getViewById(viewId);
        view.setTypeface(typeface);
        view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        return this;
    }

    @Override
    public ListViewHolder setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = getViewById(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }


    @Override
    public ListViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getViewById(viewId);
        view.setOnClickListener(listener);
        return this;
    }
}
