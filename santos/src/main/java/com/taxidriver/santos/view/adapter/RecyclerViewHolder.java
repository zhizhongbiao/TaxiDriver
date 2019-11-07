package com.taxidriver.santos.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;

import android.util.SparseArray;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.taxidriver.santos.manager.ImageManager;


/**
 * Author : WaterFlower.
 * Created on 2017/8/11.
 * Desc :
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder implements ViewHelper<RecyclerViewHolder> {
    private Context mContext;
    SparseArray<View> mViews;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        mViews = new SparseArray<>();
    }

    public <T extends View> T getViewById(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }

        return (T) view;
    }


    @Override
    public View getRootView() {
        return itemView;
    }

    @Override
    public RecyclerViewHolder setText(int viewId, String value) {
        TextView view = getViewById(viewId);
        view.setText(value);
        return this;
    }

    @Override
    public RecyclerViewHolder setTextColor(int viewId, int color) {
        TextView view = getViewById(viewId);
        view.setTextColor(color);
        return this;
    }

    @Override
    public RecyclerViewHolder setTextColorRes(int viewId, int colorRes) {
        TextView view = getViewById(viewId);
        view.setTextColor(mContext.getResources().getColor(colorRes));
        return this;
    }

    @Override
    public RecyclerViewHolder setImageResource(int viewId, int imgResId) {
        ImageView view = getViewById(viewId);
        view.setImageResource(imgResId);
        return this;
    }

    @Override
    public RecyclerViewHolder setBackgroundColor(int viewId, int color) {
        View view = getViewById(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    @Override
    public RecyclerViewHolder setBackgroundColorRes(int viewId, int colorRes) {
        View view = getViewById(viewId);
        view.setBackgroundResource(colorRes);
        return this;
    }

    @Override
    public RecyclerViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getViewById(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    @Override
    public RecyclerViewHolder loadImageFromURL(int viewId, String url) {
        View view = getViewById(viewId);
        ImageManager.load(mContext, url, view);
        return this;
    }


    @Override
    public RecyclerViewHolder setImageDrawableRes(int viewId, int drawableRes) {
        Drawable drawable = mContext.getResources().getDrawable(drawableRes);
        return setImageDrawable(viewId, drawable);
    }

    @Override
    public RecyclerViewHolder setImageUrl(int viewId, String imgUrl) {
        return null;
    }

    @Override
    public RecyclerViewHolder setImageBitmap(int viewId, Bitmap imgBitmap) {
        ImageView view = getViewById(viewId);
        view.setImageBitmap(imgBitmap);
        return this;
    }

    @Override
    public RecyclerViewHolder setVisible(int viewId, boolean visible) {
        View view = getViewById(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    @Override
    public RecyclerViewHolder setViewGone(int viewId) {
        View view = getViewById(viewId);
        view.setVisibility(View.GONE);
        return this;
    }

    @Override
    public RecyclerViewHolder setViewVisible(int viewId) {
        View view = getViewById(viewId);
        view.setVisibility(View.VISIBLE);
        return this;
    }

    @Override
    public RecyclerViewHolder setViewInVisible(int viewId) {
        View view = getViewById(viewId);
        view.setVisibility(View.INVISIBLE);
        return this;
    }


    @Override
    public RecyclerViewHolder setTag(int viewId, Object tag) {
        View view = getViewById(viewId);
        view.setTag(tag);
        return this;
    }

    @Override
    public RecyclerViewHolder setTag(int viewId, int key, Object tag) {
        View view = getViewById(viewId);
        view.setTag(key, tag);
        return this;
    }

    @Override
    public RecyclerViewHolder setChecked(int viewId, boolean checked) {
        Checkable view = getViewById(viewId);
        view.setChecked(checked);
        return this;
    }

    @Override
    public RecyclerViewHolder setAlpha(int viewId, float value) {
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
    public RecyclerViewHolder setTypeface(int viewId, Typeface typeface) {
        TextView view = getViewById(viewId);
        view.setTypeface(typeface);
        view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        return this;
    }

    @Override
    public RecyclerViewHolder setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = getViewById(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    @Override
    public RecyclerViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getViewById(viewId);
        view.setOnClickListener(listener);
        return this;
    }
}
