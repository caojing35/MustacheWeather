package com.mustacheweather.android.ui.area;

import com.mustacheweather.android.greendao.City;
import com.mustacheweather.android.greendao.County;
import com.mustacheweather.android.greendao.Province;
import com.mustacheweather.android.mvp.IBasePresenter;
import com.mustacheweather.android.mvp.IBaseView;

import java.util.List;

/**
 * Created by caojing on 2017/10/8.
 */

public interface AreaConstract {

    interface View extends IBaseView<Presenter> {

        void setTitle(String title);

        void showProvince(List<Province> provinces);

        void showCity(List<City> cities);

        void showCounty(List<County> counties);

        void showWeatherDetail(String weatherId);
    }

    interface Presenter extends IBasePresenter {

        void selectProvince(Province province);

        void selectCity(City city);

        void selectCounty(County county);


    }
}
