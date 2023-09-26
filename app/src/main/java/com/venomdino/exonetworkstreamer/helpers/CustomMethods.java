package com.venomdino.exonetworkstreamer.helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.venomdino.exonetworkstreamer.R;

import java.net.URL;

public class CustomMethods {

    public static String getVersionName(Context context) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
        return packageInfo.versionName;
    }

    public static void hideSoftKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static boolean isValidURL(String url) {

        String firstEightCharacters = url.substring(0, Math.min(url.length(), 8));

        if (firstEightCharacters.toLowerCase().startsWith("ftp://")){
            return true;
        } else if (firstEightCharacters.toLowerCase().startsWith("http://")){
            return true;
        }else if (firstEightCharacters.toLowerCase().startsWith("rtmp://")){
            return true;
        } else return firstEightCharacters.toLowerCase().startsWith("https://");
    }

    public static String getFileName(String url) {

        try{
            URL urlObj = new URL(url);
            String path = urlObj.getPath();
            int index = path.lastIndexOf("/");
            if (index != -1) {
                return path.substring(index + 1);
            } else {
                return null;
            }
        }
        catch (Exception e){
            return "Unknown";
        }
    }


    public static void errorAlert(Activity activity, String errorTitle, String errorBody, String actionButton, boolean shouldGoBack) {

        if (!activity.isFinishing()){
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle(errorTitle);
            builder.setMessage(errorBody);
            builder.setIcon(R.drawable.warning_24);
            builder.setPositiveButton(actionButton, (dialogInterface, i) -> {
                if (shouldGoBack){
                    activity.finish();
                }
                else {
                    dialogInterface.dismiss();
                }
            });
            builder.setNegativeButton("Copy", (dialog, which) -> {

                ClipboardManager clipboardManager = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("text", errorBody);
                clipboardManager.setPrimaryClip(clipData);

                new Handler().postDelayed(activity::finish,1000);
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }


    public static void hideSystemUI(Activity activity) {

        activity.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }
}
