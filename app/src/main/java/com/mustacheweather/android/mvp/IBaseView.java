package com.mustacheweather.android.mvp;

/**
 * Created by caojing on 2017/10/8.
 */

public interface IBaseView<T> {
    void setPresenter(T presenter);
}
