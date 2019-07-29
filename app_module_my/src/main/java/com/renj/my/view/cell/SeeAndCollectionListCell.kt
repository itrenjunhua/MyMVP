package com.renj.my.view.cell

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.renj.common.utils.aroute.ARouterPath
import com.renj.common.utils.aroute.ARouterUtils
import com.renj.common.mode.bean.bundle.WebActivityBundleData
import com.renj.common.mode.bean.bundle.WebActivityType
import com.renj.common.mode.bean.dp.ListSeeAndCollectionDB
import com.renj.my.R
import com.renj.view.recyclerview.adapter.RecyclerAdapter
import com.renj.view.recyclerview.adapter.RecyclerCell
import com.renj.view.recyclerview.adapter.RecyclerViewHolder

/**
 * ======================================================================
 *
 *
 * 作者：Renj
 * 邮箱：itrenjunhua@163.com
 *
 *
 * 创建时间：2019-06-14   14:27
 *
 *
 * 描述：收藏和查看List条目样式
 *
 *
 * 修订历史：
 *
 *
 * ======================================================================
 */
class SeeAndCollectionListCell(itemData: ListSeeAndCollectionDB, isSeeList: Boolean) : RecyclerCell<ListSeeAndCollectionDB>(itemData) {

    private var isSeeList = false

    init {
        this.isSeeList = isSeeList
    }

    override fun getRecyclerItemType(): Int {
        return IRecyclerCellType.COLLECTION_SEE_TYPE
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(context, parent, R.layout.cell_see_and_collection_list)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int, itemData: ListSeeAndCollectionDB) {
        holder.setText(R.id.tv_see_and_collection_title, itemData.title)

        var textView = holder.getView<TextView>(R.id.tv_see_and_collection_count)
        if (isSeeList) {
            textView.visibility = View.VISIBLE
            textView.text = "查看次数：" + itemData.seeCount
        } else {
            textView.visibility = View.GONE
        }
    }

    override fun onItemClick(context: Context, recyclerAdapter: RecyclerAdapter<*>,
                             itemView: View, position: Int, itemData: ListSeeAndCollectionDB) {
        val bundleData = WebActivityBundleData(itemData.pid, itemData.dataId, itemData.title, itemData.content, itemData.url, itemData.images.split(","), WebActivityType.TYPE_LIST)
        ARouterUtils.openActivity(ARouterPath.PATH_COMMON_ACTIVITY_WEB, "data", bundleData)
    }
}
