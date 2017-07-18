package com.innofang.gankiodemo.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author: Inno Fang
 * Time: 2017/2/7 18:27
 * Description:
 */

public class Gank implements Serializable{

    /**
     * error : false
     * results : [{"_id":"58968789421aa970bb154886","createdAt":"2017-02-05T10:01:45.180Z","desc":"又一款简洁、漂亮的干货集中营Android客户端","publishedAt":"2017-02-07T11:37:03.683Z","source":"web","type":"Android","url":"https://github.com/sfsheng0322/RxGank","used":true,"who":"孙福生"},{"_id":"58983094421aa970bed462d0","createdAt":"2017-02-06T16:15:16.450Z","desc":"其实最初是准备写一本电子书然后免费开放给大家的，可惜啊可惜毅力不够，坚持不下来，所以还是当成博客来写，写好了再出电子书吧。 ","images":["http://img.gank.io/b6f7be81-cdb8-4631-a194-db9e40ae8b0b"],"publishedAt":"2017-02-07T11:37:03.683Z","source":"web","type":"Android","url":"http://kymjs.com/code/2017/02/03/01/","used":true,"who":"张涛"},{"_id":"589830c3421aa970b84523a4","createdAt":"2017-02-06T16:16:03.322Z","desc":"前三章的内容是为方便想在短时间内马上用上Kotlin的人，例如作为一个刚入职的新人，公司的代码已经是用Kotlin编写了，你应该如何更快的融入与适应。","publishedAt":"2017-02-07T11:37:03.683Z","source":"web","type":"Android","url":"http://kymjs.com/code/2017/02/04/01/","used":true,"who":"张涛"},{"_id":"58992ffc421aa970b84523a7","createdAt":"2017-02-07T10:25:00.756Z","desc":"Android 实现步骤管理的组件。","images":["http://img.gank.io/6704b204-ccd4-44a3-aae9-d2ac02b33be5"],"publishedAt":"2017-02-07T11:37:03.683Z","source":"chrome","type":"Android","url":"https://github.com/stepstone-tech/android-material-stepper","used":true,"who":"代码家"},{"_id":"5895d845421aa970b845238c","createdAt":"2017-02-04T21:33:57.847Z","desc":" TabLayout 和 CoordinatorLayout 相结合的折叠控件","publishedAt":"2017-02-06T11:36:12.36Z","source":"web","type":"Android","url":"https://github.com/hugeterry/CoordinatorTabLayout","used":true,"who":null},{"_id":"58971929421aa970b8452390","createdAt":"2017-02-05T20:23:05.494Z","desc":"事件总线EventBus源码解析","images":["http://img.gank.io/97c303f3-8ba0-4396-8c67-ce67f581f52b","http://img.gank.io/910ba3c9-de22-45c9-ba47-a30791d236ec"],"publishedAt":"2017-02-06T11:36:12.36Z","source":"web","type":"Android","url":"http://shaohui.me/2017/01/20/android-messaging-2-eventbus/","used":true,"who":"邵辉Vista"},{"_id":"5897d7e3421aa970bed462c8","createdAt":"2017-02-06T09:56:51.893Z","desc":"无需 Root，利用 Android 工作模式，冻结不常用的 App。","images":["http://img.gank.io/46fbfa72-78b1-4f83-822c-fbcd1d7cc735"],"publishedAt":"2017-02-06T11:36:12.36Z","source":"chrome","type":"Android","url":"https://github.com/mthli/Mount","used":true,"who":"代码家"},{"_id":"5885c080421aa95ea7cbcf0d","createdAt":"2017-01-23T16:36:16.600Z","desc":"一个比较完整的 Rect Native 使用 Android 端的自定义下拉刷新组件控件的教程","images":["http://img.gank.io/20e8af68-f8bf-48e8-a68a-cee0320cfffc"],"publishedAt":"2017-02-04T11:47:42.336Z","source":"web","type":"Android","url":"http://solart.cc/2017/01/23/react-native-custom-view/","used":true,"who":null},{"_id":"5886b026421aa95ea7cbcf11","createdAt":"2017-01-24T09:38:46.602Z","desc":"Execute shell commands on Android","publishedAt":"2017-02-04T11:47:42.336Z","source":"chrome","type":"Android","url":"https://github.com/jrummyapps/android-shell","used":true,"who":"蒋朋"},{"_id":"5893f9ef421aa90e88f0a56c","createdAt":"2017-02-03T11:33:03.268Z","desc":"greenrobot出品的对象数据库","publishedAt":"2017-02-04T11:47:42.336Z","source":"web","type":"Android","url":"http://greenrobot.org/objectbox/","used":true,"who":"color"}]
     */

    private boolean error;
    private List<ResultsBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * _id : 58968789421aa970bb154886
         * createdAt : 2017-02-05T10:01:45.180Z
         * desc : 又一款简洁、漂亮的干货集中营Android客户端
         * publishedAt : 2017-02-07T11:37:03.683Z
         * source : web
         * type : Android
         * url : https://github.com/sfsheng0322/RxGank
         * used : true
         * who : 孙福生
         * images : ["http://img.gank.io/b6f7be81-cdb8-4631-a194-db9e40ae8b0b"]
         */

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;
        private List<String> images;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
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

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        @Override
        public String toString() {
            return "ResultsBean{" +
                    "_id='" + _id + '\'' +
                    ", createdAt='" + createdAt + '\'' +
                    ", desc='" + desc + '\'' +
                    ", publishedAt='" + publishedAt + '\'' +
                    ", source='" + source + '\'' +
                    ", type='" + type + '\'' +
                    ", url='" + url + '\'' +
                    ", used=" + used +
                    ", who='" + who + '\'' +
                    ", images=" + images +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Gank{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }
}
