package com.quinn.imageloader.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * sd卡缓存
 */

public class DiskCacheI implements IBaseCache {

    private String cacheDir = "/sdcard/Android/data/com.hsgn.cameramanage/cache/";

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        File file = new File(cacheDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(cacheDir+hashKeyForDisk(url));
            bitmap.compress(Bitmap.CompressFormat.PNG,100,out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                if (out!=null) out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Bitmap getBitmap(String url) {
        File file = new File(cacheDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return BitmapFactory.decodeFile(cacheDir+hashKeyForDisk(url));
    }

    /**
     * 使用MD5算法对传入的key进行加密并返回。
     */
    public String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
