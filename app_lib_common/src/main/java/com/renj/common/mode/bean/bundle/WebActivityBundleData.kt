package com.renj.common.mode.bean.bundle

import android.os.Parcel
import android.os.Parcelable

/**
 * ======================================================================
 *
 * 作者：Renj
 *
 * 创建时间：2019-07-25   1:40
 *
 * 描述：跳转到 WebView 页面需要传递的数据
 *
 * 修订历史：
 *
 * ======================================================================
 */
data class WebActivityBundleData(var pid: Int, var id: Int, var title: String, var content: String,
                                 var url: String, var images: List<String>, var type: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.createStringArrayList(),
            parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(pid)
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(content)
        parcel.writeString(url)
        parcel.writeStringList(images)
        parcel.writeInt(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WebActivityBundleData> {
        override fun createFromParcel(parcel: Parcel): WebActivityBundleData {
            return WebActivityBundleData(parcel)
        }

        override fun newArray(size: Int): Array<WebActivityBundleData?> {
            return arrayOfNulls(size)
        }
    }

}