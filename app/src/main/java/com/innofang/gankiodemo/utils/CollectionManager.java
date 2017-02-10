package com.innofang.gankiodemo.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.innofang.gankiodemo.bean.Collection;
import com.innofang.gankiodemo.database.CollectionCursorWrapper;
import com.innofang.gankiodemo.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

import static com.innofang.gankiodemo.constant.DbSchema.GankTable;

/**
 * Author: Inno Fang
 * Time: 2017/2/10 18:34
 * Description:
 */

public class CollectionManager {

    private static CollectionManager sCollectionManager;
    private SQLiteDatabase mDatabase;
    private Context mContext;

    private CollectionManager(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new DatabaseHelper(mContext)
                .getWritableDatabase();
    }

    public static CollectionManager getInstance(Context context) {
        if (null == sCollectionManager) {
            sCollectionManager = new CollectionManager(context);
        }
        return sCollectionManager;
    }

    public void addCollection(Collection collection) {
        ContentValues values = getContentValues(collection);
        mDatabase.insert(GankTable.NAME, null, values);
    }

    public List<Collection> getCollections() {
        List<Collection> collections = new ArrayList<>();
        CollectionCursorWrapper cursor = queryCollections(null, null);

        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                collections.add(cursor.getCollection());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return collections;
    }

    public Collection getCollection(String url) {
        CollectionCursorWrapper cursor = queryCollections(
                GankTable.Cols.URL + " = ?",
                new String[]{url}
        );

        try{
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getCollection();
        } finally {
            cursor.close();
        }
    }

    public void deleteCollection(Collection collection) {
        String url = collection.getUrl();
        mDatabase.delete(GankTable.NAME,
                GankTable.Cols.URL + " = ?",
                new String[]{url});
    }

    private static ContentValues getContentValues(Collection collection) {
        ContentValues values = new ContentValues();
        values.put(GankTable.Cols.DESC, collection.getDesc());
        values.put(GankTable.Cols.TYPE, collection.getType());
        values.put(GankTable.Cols.WHO, collection.getWho());
        values.put(GankTable.Cols.PUBLISH_AT, collection.getPublishAt());

        return values;
    }

    private CollectionCursorWrapper queryCollections(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                GankTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new CollectionCursorWrapper(cursor);
    }
}
