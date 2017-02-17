package com.quinn.imageloader.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.widget.AppCompatImageView;
import android.widget.ImageView;

import com.quinn.imageloader.cache.IBaseCache;
import com.quinn.imageloader.cache.MemoryCacheI;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 图片加载类
 */

public class ImageLoader implements IBaseImageLoader {

    private IBaseCache cache = new MemoryCacheI();

    @Override
    public void setCache(IBaseCache cache) {
        this.cache = cache;
    }

    @Override
    public void displayImage(String url, AppCompatImageView imageView) {
        Bitmap bitmap = cache.getBitmap(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }
        new LoaderTask(imageView,url).execute(url);
    }

    class LoaderTask extends AsyncTask<String, Void, Bitmap>{

        private ImageView imageView;
        private String url;

        public LoaderTask(ImageView imageView,String url) {
            this.imageView = imageView;
            this.url = url;
            imageView.setTag(url);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return downloadImage(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                if (url.equals(imageView.getTag())) {
                    imageView.setImageBitmap(bitmap);
                }
                cache.putBitmap(url, bitmap);
            }
        }
    }

    protected Bitmap downloadImage(String ImageUrl) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(ImageUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10 * 1000);
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 根据传入的uniqueName获取硬盘缓存的路径地址。
     */
    public java.io.File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new java.io.File(cachePath + java.io.File.separator + uniqueName);
    }
}
