package com.taxidriver.santos.view.adapter;

import java.util.List;

/**
 * Author : WaterFlower.
 * Created on 2017/8/11.
 * Desc :
 */

interface ListDataHelper<T> {

    List<T> getDataSet();

    void updateAll(List<T> newData);

    void addAll(List<T> newData);

    void clear();

    void addHead(T data);

    void append(T data);

    void replace(int index, T data);

    void replace(T oldData, T newData);

    void insert(int index, T data);

    void remove(int index);

    void remove(T data);

    void removeHead();

    void removeTail();

    void removeAll(List<T> datas);
}

