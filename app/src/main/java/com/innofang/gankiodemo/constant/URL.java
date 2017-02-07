package com.innofang.gankiodemo.constant;

/**
 * Author: Inno Fang
 * Time: 2017/2/5 16:43
 * Description:
 */

public class URL {

    // 每日数据请求URL(须在后面添加日期)
    public static final String DAILY_DATA = "http://gank.io/api/day/";

    // 请求图片时，添加后缀获取合适的图片
    public static final String REQUEST_IMAGE_POSTFIX = "?imageView2/0/w/220";

    // 发过干货日期接口
    public static final String HISTORY = "http://gank.io/api/day/history";

    // 分类数据，请求福利图片个数：100 页数：1
    public static final String DATA_LUCK = "http://gank.io/api/data/福利/100/1";

}
