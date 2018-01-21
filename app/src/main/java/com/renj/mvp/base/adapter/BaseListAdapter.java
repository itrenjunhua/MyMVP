package com.renj.mvp.base.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2018-01-21   21:34
 * <p>
 * 描述：ListView/GridView 控件 Adapter 的封装，减少重复代码的编写。泛型表示单个条目的数据类型
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {

    protected List<T> datas = new ArrayList<T>();
    protected Context context;

    public BaseListAdapter(@org.jetbrains.annotations.Nullable Context context) {
        this.context = context;
    }

    public BaseListAdapter(@org.jetbrains.annotations.Nullable Fragment fragment) {
        this.context = fragment.getActivity();
    }

    public BaseListAdapter(@org.jetbrains.annotations.Nullable Context context, @org.jetbrains.annotations.Nullable List<T> datas) {
        this.context = context;
        this.datas = datas;
    }

    public BaseListAdapter(@org.jetbrains.annotations.Nullable Fragment fragment, @org.jetbrains.annotations.Nullable List<T> datas) {
        this.context = fragment.getActivity();
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseListViewHolder<T> listViewHolder;
        if (convertView == null) {
            listViewHolder = getViewHolder(context, parent, position);
        } else {
            listViewHolder = (BaseListViewHolder<T>) convertView.getTag();
        }
        listViewHolder.setData(position, datas.get(position));
        return listViewHolder.getItemRootView();
    }

    /**
     * 获取ViewHolder，强制ListView/GridView都需要使用ViewHolder，该ViewHolder必须继承至 {@link BaseListViewHolder}
     *
     * @param context  上下文
     * @param parent   ViewGroup
     * @param position 当前位置
     * @return
     */
    protected abstract BaseListViewHolder getViewHolder(Context context, ViewGroup parent, int position);

    public abstract class BaseListViewHolder<T> {
        private View mItemRootView;

        public BaseListViewHolder(Context context, ViewGroup parent) {
            mItemRootView = LayoutInflater.from(context).inflate(getItemViewLayoutId(), parent, false);
            if (mItemRootView != null) {
                mItemRootView.setTag(this);
                ButterKnife.bind(this, mItemRootView);
            }
        }

        @org.jetbrains.annotations.Contract(pure = true)
        private View getItemRootView() {
            return mItemRootView;
        }

        /**
         * 子类重写，获取条目布局资源ID
         *
         * @return
         */
        public abstract int getItemViewLayoutId();

        /**
         * 设置条目的数据
         *
         * @param position 当前的位置
         * @param data     当前位置的数据
         */
        public abstract void setData(int position, T data);
    }
}
