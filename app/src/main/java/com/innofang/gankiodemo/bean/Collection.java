package com.innofang.gankiodemo.bean;

/**
 * Author: Inno Fang
 * Time: 2017/2/10 18:14
 * Description:
 */

public class Collection {

    private String mDesc;
    private String mType;
    private String mWho;
    private String mPublishAt;
    private String mUrl;

    public Collection(String desc, String type, String who, String publishAt, String url) {
        mDesc = desc;
        mType = type;
        mWho = who;
        mPublishAt = publishAt;
        mUrl = url;
    }

    public String getDesc() {
        return mDesc;
    }

    public String getType() {
        return mType;
    }

    public String getWho() {
        return mWho;
    }

    public String getPublishAt() {
        return mPublishAt;
    }

    public String getUrl() {
        return mUrl;
    }

    @Override
    public String toString() {
        return "Collection{" +
                "mDesc='" + mDesc + '\'' +
                ", mType='" + mType + '\'' +
                ", mWho='" + mWho + '\'' +
                ", mPublishAt='" + mPublishAt + '\'' +
                '}';
    }
}
