package com.innofang.gankiodemo.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.util.Log;

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

    private static final String TAG = "CCW";

    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public CollectionCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Collection getCollection(){
        Log.i(TAG, "getCollection: desc.getColumnIndex = " + getColumnIndex(DESC));
        String desc = getString(getColumnIndex(DESC));
        String type = getString(getColumnIndex(TYPE));
        String who = getString(getColumnIndex(WHO));
        String publishAt = getString(getColumnIndex(PUBLISH_AT));
        String url = getString(getColumnIndex(URL));
        Collection collection = new Collection(desc, type, who, publishAt, url);
        Log.i(TAG, "getCollection: " + collection.toString());
        return collection;
    }
}
