package com.mustacheweather.android.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by caojing on 2017/10/6.
 */

@Entity
public class City {

    @Id(autoincrement = true)
    private Long id;

    private String cityName;

    private int cityCode;

    private long provinceId;

    @Generated(hash = 1814939759)
    public City(Long id, String cityName, int cityCode, long provinceId) {
        this.id = id;
        this.cityName = cityName;
        this.cityCode = cityCode;
        this.provinceId = provinceId;
    }

    @Generated(hash = 750791287)
    public City() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityName() {
        return this.cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCityCode() {
        return this.cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public long getProvinceId() {
        return this.provinceId;
    }

    public void setProvinceId(long provinceId) {
        this.provinceId = provinceId;
    }

}
