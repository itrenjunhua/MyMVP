package com.renj.common.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
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

    public BaseListAdapter(@NonNull Context context) {
        this.context = context;
    }

    public BaseListAdapter(@NonNull Fragment fragment) {
        this.context = fragment.getActivity();
    }

    public BaseListAdapter(@NonNull Context context, @NonNull List<T> datas) {
        this.context = context;
        this.datas = datas;
    }

    public BaseListAdapter(@NonNull Fragment fragment, @NonNull List<T> datas) {
        this.context = fragment.getActivity();
        this.datas = datas;
    }

    /**
     * 设置数据/重置数据
     *
     * @param datas 新的数据/重置的数据
     */
    public void setDatas(@NonNull List<T> datas) {
        if (this.datas != null) {
            this.datas.clear();
            this.datas.addAll(datas);
            notifyDataSetChanged();
        }
    }

    /**
     * 加载更多数据时调用设置更多数据
     *
     * @param datas 需要设置更多的数据
     */
    public void loadMore(@NonNull List<T> datas) {
        if (this.datas != null) {
            this.datas.addAll(datas);
            notifyDataSetChanged();
        }
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
        protected View mItemRootView;

        public BaseListViewHolder(Context context, ViewGroup parent) {
            mItemRootView = LayoutInflater.from(context).inflate(getItemViewLayoutId(), parent, false);
            if (mItemRootView != null) {
                mItemRootView.setTag(this);
                ButterKnife.bind(this, mItemRootView);
            }
        }

        @org.jetbrains.annotations.Contract(pure = true)
        protected View getItemRootView() {
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
