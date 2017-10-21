package com.mustacheweather.android.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "PROVINCE".
*/
public class ProvinceDao extends AbstractDao<Province, Long> {

    public static final String TABLENAME = "PROVINCE";

    /**
     * Properties of entity Province.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property ProvinceName = new Property(1, String.class, "provinceName", false, "PROVINCE_NAME");
        public final static Property ProvinceCode = new Property(2, int.class, "provinceCode", false, "PROVINCE_CODE");
    }


    public ProvinceDao(DaoConfig config) {
        super(config);
    }
    
    public ProvinceDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PROVINCE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"PROVINCE_NAME\" TEXT," + // 1: provinceName
                "\"PROVINCE_CODE\" INTEGER NOT NULL );"); // 2: provinceCode
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PROVINCE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Province entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String provinceName = entity.getProvinceName();
        if (provinceName != null) {
            stmt.bindString(2, provinceName);
        }
        stmt.bindLong(3, entity.getProvinceCode());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Province entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String provinceName = entity.getProvinceName();
        if (provinceName != null) {
            stmt.bindString(2, provinceName);
        }
        stmt.bindLong(3, entity.getProvinceCode());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Province readEntity(Cursor cursor, int offset) {
        Province entity = new Province( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // provinceName
            cursor.getInt(offset + 2) // provinceCode
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Province entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setProvinceName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setProvinceCode(cursor.getInt(offset + 2));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Province entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Province entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Province entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
