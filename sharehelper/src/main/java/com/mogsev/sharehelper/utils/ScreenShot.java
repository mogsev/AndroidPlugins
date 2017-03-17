package com.mogsev.sharehelper.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Eugene Sikaylo (mogsev@gmail.com)
 */
public final class ScreenShot {
    private static final String TAG = ScreenShot.class.getSimpleName();

    public static Bitmap takeScreenShot(final Context context) {
        if (context == null) {
            throw new NullPointerException("Context cannot be null");
        }

        View view = ((Activity) context).getWindow().getDecorView().getRootView();
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return bitmap;
    }

    public static Uri saveBitmapToCache(final Context context, Bitmap bitmap) {
        File outputDir = context.getCacheDir(); // context being the Activity pointer
        File outputFile = null;
        try {
            outputFile = File.createTempFile("prefix", "extension", outputDir);
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Uri.fromFile(outputFile);
    }
}
