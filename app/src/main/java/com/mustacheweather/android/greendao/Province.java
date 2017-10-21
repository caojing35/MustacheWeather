package com.mustacheweather.android.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.litepal.crud.DataSupport;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by caojing on 2017/10/6.
 */

@Entity
public class Province {

    @Id(autoincrement = true)
    private Long id;

    private String provinceName;

    private int provinceCode;

    @Generated(hash = 1695957187)
    public Province(Long id, String provinceName, int provinceCode) {
        this.id = id;
        this.provinceName = provinceName;
        this.provinceCode = provinceCode;
    }

    @Generated(hash = 1309009906)
    public Province() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProvinceName() {
        return this.provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getProvinceCode() {
        return this.provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }

}
