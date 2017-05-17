package com.renj.mvp.test.extend;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-15   18:26
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class Preson {
    public String name;
    public int age;

    @Override
    public String toString() {
        return "[ name:" + name + "; age:" + age + " ]";
    }
}
