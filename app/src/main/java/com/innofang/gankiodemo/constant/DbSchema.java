package com.innofang.gankiodemo.constant;

/**
 * Author: Inno Fang
 * Time: 2017/2/10 17:49
 * Description:
 */

public class DbSchema {

    public static final String DATABASE_NAME = "gank.db";
    public static final int VERSION = 1;

    public static final class GankTable {
        public static final String NAME = "gank";

        public static final class Cols {
            public static final String DESC = "desc";
            public static final String TYPE = "type";
            public static final String WHO = "who";
            public static final String PUBLISH_AT = "publish_at";
            public static final String URL = "url";
        }
    }

}
