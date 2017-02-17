package com.quinn.imageloader.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * 内存缓存
 */

public class MemoryCacheI implements IBaseCache {

    private LruCache<String, Bitmap> lruCache;

    public MemoryCacheI() {
        initImageCache();
    }

    private void initImageCache() {
        //计算可使用的最大内存
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        //取四分之一的可用内存作为缓存
        final int cacheSize = maxMemory / 4;

        lruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        if (getBitmap(url) == null)
            lruCache.put(url, bitmap);
    }

    @Override
    public Bitmap getBitmap(String url) {
        return lruCache.get(url);
    }
}
