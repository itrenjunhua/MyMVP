package com.renj.mvp.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-11   17:55
 * <p>
 * 描述：自定义注解，表示Activity的作用域
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ScopeActivity {
}
