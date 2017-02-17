package com.quinn.imageloader.cache;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017/2/17.
 */

public interface IBaseCache {

    void putBitmap(String url, Bitmap bitmap);

    Bitmap getBitmap(String url);
}
