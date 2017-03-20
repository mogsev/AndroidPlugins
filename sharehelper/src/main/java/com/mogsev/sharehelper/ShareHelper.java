package com.mogsev.sharehelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.mogsev.sharehelper.utils.ScreenShot;

//import com.unity3d.player.UnityPlayer;

/**
 * @author Eugene Sikaylo (mogsev@gmail.com)
 */
public class ShareHelper {
    private static final String TAG = ShareHelper.class.getSimpleName();

    private static final String SHARE_MESSAGE = "Share message to...";
    private static final String SHARE_IMAGE = "Share image to...";
    private static final String SHARE_RESULT = "Share result to...";

    //private Activity mainActivity = UnityPlayer.currentActivity;


    /**
     * Send text content
     * @param subject
     * @param message
     */
    public static void shareMessage(final Context context, String subject, String message) {
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        runOnUiThread(context, intent, SHARE_MESSAGE);
    }

    /**
     * Send image content
     * @param subject
     * @param uri
     */
    public static void shareImage(final Context context, String subject, Uri uri) {
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image/*");

        runOnUiThread(context, intent, SHARE_IMAGE);
    }

    /**
     * Send result content
     * @param subject
     * @param message
     */
    public void setShareResult(final Context context, String subject, String message) {
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.setType("image/*");

        Bitmap bitmap = ScreenShot.takeScreenShot(context);

        /**
        Date date = new Date();
        String path = Environment.getExternalStorageDirectory().toString() + "/" + "temp" + ".jpg";
        File file = new File(path);
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri uri = Uri.fromFile(file);
         */
        Uri uri = ScreenShot.saveBitmapToCache(context, bitmap);
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        runOnUiThread(context, intent, SHARE_RESULT);
    }

    public static void runOnUiThread(final Context context, final Intent intent, final String share) {
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                context.startActivity(Intent.createChooser(intent, share));
            }
        });
    }
}

