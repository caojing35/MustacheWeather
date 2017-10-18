package com.mustacheweather.android.ui.area;

import com.mustacheweather.android.db.AreaData;
import com.mustacheweather.android.db.City;
import com.mustacheweather.android.db.County;
import com.mustacheweather.android.db.Province;
import com.mustacheweather.android.util.AndroidKeyUtil;

import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

/**
 * Created by caojing on 2017/10/8.
 */

public class AreaPresenter implements AreaConstract.Presenter {

    private AreaData areaData;

    private AreaConstract.View view;

    public AreaPresenter(AreaData areaData, AreaConstract.View view) {
        this.areaData = areaData;
        this.view = view;
    }

    @Override
    public void start() {
        List<Province> provinces = areaData.getAllProvince();
        view.setTitle("中国");
        view.showProvince(provinces);
    }

    @Override
    public void selectProvince(Province province) {

        List<City> cities = areaData.getCitiesByProvince(province);
        view.setTitle(province.getProvinceName());
        view.showCity(cities);
    }

    @Override
    public void selectCity(City city) {
        List<County> counties = areaData.getCountiesByCity(city);
        view.setTitle(city.getCityName());
        view.showCounty(counties);
    }

    @Override
    public void selectCounty(County county) {
        view.showWeatherDetail(county.getWeatherId());
    }
}
