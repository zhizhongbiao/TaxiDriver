package com.taxidriver.santos.view.hover_view;


import androidx.recyclerview.widget.RecyclerView;

/**
 * FileName :  ListBaseAdapter
 * Author   :  zhizhongbiao
 * Date     :  2018/11/16
 * Describe :
 */

public abstract class ListBaseAdapter<VH extends RecyclerView.ViewHolder>  extends RecyclerView.Adapter<VH>{
    private int hoveredPosition=-1;

    public void setHoveredPosition(boolean isRight){

        if (isRight){
            if (hoveredPosition < getItemCount()-1){
                hoveredPosition ++;
            }
        }else {
            if (hoveredPosition>0){
                hoveredPosition --;
            }
        }

        if (hoveredPosition>getItemCount()-1||hoveredPosition<0){
            hoveredPosition = 0;
        }
    }

    public int getHoveredPosition(){
        return hoveredPosition;
    }

    public void setHoveredPosition(int position){
        this.hoveredPosition = position;
    }
}
