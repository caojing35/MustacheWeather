package com.mustacheweather.android.db;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by caojing on 2017/10/8.
 */

public class AreaData {


    @Inject
    public AreaData(){

    }

    public List<Province> getAllProvince(){
        return null;
    }

    public List<City> getCitiesByProvince(Province province){
        return null;
    }

    public List<County> getCountiesByCity(City city){
        return null;
    }

}
