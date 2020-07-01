package com.example.zhongyitizhi1.tuling;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseListAdapter<E> extends BaseAdapter {

    //创建了一个list，一个上下文，一个布局装载器
    private List<E> mList = new ArrayList<E>();
    protected Context mContext;
    protected LayoutInflater mInflater;
    //设定上下文，并根据上下文创建
    public BaseListAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }
    //赋值list
    public BaseListAdapter(Context context, List<E> list) {
        this(context);
        mList = list;
    }
    //返回list的尺寸
    @Override
    public int getCount() {
        return mList.size();
    }
    //清除list
    public void clearAll() {
        mList.clear();
    }
    //先清空再赋值
    public void setData(List<E> list) {
        clearAll();
        addALL(list);
    }
    //返回list
    public List<E> getData() {
        return mList;
    }
    //一直为向list添加列表项
    public void addALL(List<E> list){
        if(list == null || list.size() == 0){
            return ;
        }
        mList.addAll(list);
    }
    //为list添加列表项
    public void add(E item){
        mList.add(item);
    }
    //根据位置从list中返回数据
    @Override
    public E getItem(int position) {
        return (E) mList.get(position);
    }
    //这个我就不懂了，传入一个position，然后返回一个position是干嘛的
    @Override
    public long getItemId(int position) {
        return position;
    }

    public void removeEntity(E e){
        mList.remove(e);
    }

}
