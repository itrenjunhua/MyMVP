package com.renj.mvp;

import android.support.test.runner.AndroidJUnit4;

import com.renj.mvp.mode.bean.data.GeneralListBean;
import com.renj.mvp.mode.db.DBHelper;

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
    @Test
    public void useAppContext() throws Exception {
        DBHelper dbHelper = new DBHelper();

        GeneralListBean bean = new GeneralListBean();
        bean.pid = 5;
        bean.id = 1;
        bean.title = "AAA";
        bean.content = "Content AAA";
        bean.url = "http:url";
        bean.images = new ArrayList<>();
        dbHelper.addData(bean);
    }
}
