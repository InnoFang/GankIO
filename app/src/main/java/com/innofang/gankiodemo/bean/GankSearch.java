package com.innofang.gankiodemo.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Author: Inno Fang
 * Time: 2017/2/10 16:48
 * Description:
 */

public class GankSearch {

    @SerializedName("count")
    private int _$Count111; // FIXME check this code
    private boolean error;
    private List<ResultsBean> results;

    public int get_$Count111() {
        return _$Count111;
    }

    public void set_$Count111(int _$Count111) {
        this._$Count111 = _$Count111;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "GankSearch{" +
                "_$Count111=" + _$Count111 +
                ", error=" + error +
                ", results=" + results +
                '}';
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * desc : 直接播放帧动画的ImageView
         * ganhuo_id : 56cc6d1d421aa95caa707559
         * publishedAt : 2015-10-26T03:52:58.756000
         * readability :
         * type : Android
         * url : https://github.com/skyfe79/FAImageView
         * who : Jason
         */

        private String desc;
        private String ganhuo_id;
        private String publishedAt;
        private String readability;
        private String type;
        private String url;
        private String who;

        @Override
        public String toString() {
            return "ResultsBean{" +
                    "desc='" + desc + '\'' +
                    ", ganhuo_id='" + ganhuo_id + '\'' +
                    ", publishedAt='" + publishedAt + '\'' +
                    ", readability='" + readability + '\'' +
                    ", type='" + type + '\'' +
                    ", url='" + url + '\'' +
                    ", who='" + who + '\'' +
                    '}';
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getGanhuo_id() {
            return ganhuo_id;
        }

        public void setGanhuo_id(String ganhuo_id) {
            this.ganhuo_id = ganhuo_id;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getReadability() {
            return readability;
        }

        public void setReadability(String readability) {
            this.readability = readability;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }
    }
}
