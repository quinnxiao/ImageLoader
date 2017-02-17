package com.quinn.imageloader.cache;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017/2/17.
 */

public class ImageCacheI implements IBaseCache {

    private static IBaseCache memoryCache = new MemoryCacheI();
    private static IBaseCache diskCache = new DiskCacheI();

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        memoryCache.putBitmap(url, bitmap);
        diskCache.putBitmap(url, bitmap);
    }

    @Override
    public Bitmap getBitmap(String url) {
        Bitmap bitmap = memoryCache.getBitmap(url);
        if (bitmap == null) {
            bitmap = diskCache.getBitmap(url);
        }
        return bitmap;
    }
}
