package com.innofang.gankiodemo.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.innofang.gankiodemo.bean.Collection;

import static com.innofang.gankiodemo.constant.DbSchema.GankTable.Cols.DESC;
import static com.innofang.gankiodemo.constant.DbSchema.GankTable.Cols.PUBLISH_AT;
import static com.innofang.gankiodemo.constant.DbSchema.GankTable.Cols.TYPE;
import static com.innofang.gankiodemo.constant.DbSchema.GankTable.Cols.URL;
import static com.innofang.gankiodemo.constant.DbSchema.GankTable.Cols.WHO;

/**
 * Author: Inno Fang
 * Time: 2017/2/10 18:17
 * Description:
 */

public class CollectionCursorWrapper extends CursorWrapper {


    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public CollectionCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Collection getCollection(){
        String desc = getString(getColumnIndex(DESC));
        String type = getString(getColumnIndex(TYPE));
        String who = getString(getColumnIndex(WHO));
        String publishAt = getString(getColumnIndex(PUBLISH_AT));
        String url = getString(getColumnIndex(URL));
        return new Collection(desc, type, who, publishAt, url);
    }
}
