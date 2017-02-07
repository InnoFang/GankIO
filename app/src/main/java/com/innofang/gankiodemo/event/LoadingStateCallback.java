package com.innofang.gankiodemo.event;

import java.util.List;

/**
 * Author: Inno Fang
 * Time: 2017/2/5 16:33
 * Description:
 */


public interface LoadingStateCallback<T> {
    void onSuccess(List<T> list);
    void onFailure(String error);
}
