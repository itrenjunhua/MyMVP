package com.renj.mvp;

import android.support.test.runner.AndroidJUnit4;

import com.renj.common.mode.bean.data.GeneralListBean;
import com.renj.common.mode.bean.dp.ListSeeAndCollectionDB;
import com.renj.common.mode.db.DBHelper;
import com.renj.utils.common.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    DBHelper dbHelper = new DBHelper();

    @Test
    public void addData() throws Exception {
        GeneralListBean bean = new GeneralListBean();
        bean.pid = 5;
        bean.id = 1;
        bean.title = "AAA";
        bean.content = "Content AAA";
        bean.url = "http:url";
        bean.images = new ArrayList<>();
        dbHelper.addData(bean);
    }


    @Test
    public void getSeeList() {
//        ListSeeAndCollectionRDB seeList = dbHelper.getSeeList(1, 10);
//
//        Logger.i("total = " + seeList.total + "; page = " + seeList.page);
//        for (ListSeeAndCollectionDB listSeeAndCollectionDB : seeList.list) {
//            print(listSeeAndCollectionDB);
//        }
    }

    @Test
    public void getCollectionList() {
//        ListSeeAndCollectionRDB collectionList = dbHelper.getCollectionList(1, 10);
//
//        Logger.i("total = " + collectionList.total + "; page = " + collectionList.page);
//        for (ListSeeAndCollectionDB listSeeAndCollectionDB : collectionList.list) {
//            print(listSeeAndCollectionDB);
//        }
    }

    @Test
    public void changeCollectionStatus() {
        dbHelper.changeCollectionStatus(5, 1, true);
        getCollectionList();
    }


    @Test
    public void addSeeCount() {
        dbHelper.addSeeCount(5, 1);
        getSeeList();
    }

    private void print(ListSeeAndCollectionDB listSeeAndCollectionDB) {
        Logger.i(
                "[ " + listSeeAndCollectionDB.getPid() + ", " +
                        listSeeAndCollectionDB.getDataId() + ", " +
                        listSeeAndCollectionDB.getTitle() + ", " +
                        listSeeAndCollectionDB.getSeeCount() + ", " +
                        listSeeAndCollectionDB.getCollection() + ", " +
                        listSeeAndCollectionDB.getImages() + " ]"
        );
    }
}
