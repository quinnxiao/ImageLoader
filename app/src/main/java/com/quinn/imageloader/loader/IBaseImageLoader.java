package com.quinn.imageloader.loader;

import android.support.v7.widget.AppCompatImageView;

import com.quinn.imageloader.cache.IBaseCache;

/**
 * Created by Administrator on 2017/2/17.
 */

public interface IBaseImageLoader {

    void setCache(IBaseCache cache);

    void displayImage(String url, AppCompatImageView imageView);
}
