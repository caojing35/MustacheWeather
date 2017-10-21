package com.mustacheweather.android.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.litepal.crud.DataSupport;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by caojing on 2017/10/6.
 */

@Entity
public class County {

    @Id(autoincrement = true)
    private Long id;

    private String countyName;

    private String weatherId;

    private long cityId;

    @Generated(hash = 1388544106)
    public County(Long id, String countyName, String weatherId, long cityId) {
        this.id = id;
        this.countyName = countyName;
        this.weatherId = weatherId;
        this.cityId = cityId;
    }

    @Generated(hash = 1991272252)
    public County() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountyName() {
        return this.countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getWeatherId() {
        return this.weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public long getCityId() {
        return this.cityId;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }


}
