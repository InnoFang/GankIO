package com.innofang.gankiodemo.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Author: Inno Fang
 * Time: 2017/2/16 19:19
 * Description:
 */

public class CloseUtils {

    public static void closeQuietly(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
