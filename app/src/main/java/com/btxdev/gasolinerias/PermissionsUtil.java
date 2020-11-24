package com.btxdev.gasolinerias;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.pm.PackageInfoCompat;
import androidx.fragment.app.Fragment;

public class PermissionsUtil {

    public static String getVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException ignore) {}
        return null;
    }

    public static long getVersionCode(Context context) {
        try {
            return PackageInfoCompat.getLongVersionCode(context.getPackageManager().getPackageInfo(context.getPackageName(), 0));
        } catch (PackageManager.NameNotFoundException ignore) {}
        return 0;
    }




    public static boolean hasPermissions(Context context, String permission){
        return (ContextCompat.checkSelfPermission(context,
                permission) == PackageManager.PERMISSION_GRANTED);
    }

    public static boolean hasRWPermissions(Context context){
        return hasReadPermissions(context) && hasWritePermissions(context);
    }

    public static boolean hasLocationPermissions(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }else{
            return false;
        }
    }

    public static boolean hasBackgroundLocationPermissions(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }else{
            return false;
        }
    }

    public static boolean requestLocationPermissions(Activity activity, int requestCode) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
                    , Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                    requestCode);
        }else{
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    requestCode);
        }
        return true;
    }

    public static boolean onRequestPermissionsResult(int requestPermissionCode, int requestResultCode, String[] permissions, int[] grantResults){
        return requestPermissionCode == requestResultCode && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
    }


    public static boolean hasReadPermissions(Context context) {
        return (ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    public static boolean hasWritePermissions(Context context) {
        return (ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    public static boolean hasRecordAudioPermissions(Context context) {
        return (ContextCompat.checkSelfPermission(context,
                Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED);
    }

    public static boolean requestRecordAudioPermissions(Activity activity, int request_code) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return false;
        }
        ActivityCompat.requestPermissions(activity,
                new String[] {
                        Manifest.permission.RECORD_AUDIO}, request_code);
        return true;
    }

    public static boolean requestRecordAudioPermissions(Fragment fragment, int request_code) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return false;
        }

        fragment.requestPermissions(new String[] {
                        Manifest.permission.RECORD_AUDIO}, request_code);
        return true;
    }

    public static boolean requestRWPermissions(Activity activity, int request_code) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return false;
        }
        ActivityCompat.requestPermissions(activity,
                new String[] {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, request_code);
        return true;
    }


    public static boolean requestRWPermissions(Fragment fragment, int request_code) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return false;
        }
        fragment.requestPermissions(
                new String[] {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, request_code);
        return true;
    }


    public static boolean requestPermissions(Activity activity, int requestCode, String... permissions){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return false;
        }

        ActivityCompat.requestPermissions(activity, permissions, requestCode);
        return true;
    }

    public static boolean requestPermissions(Fragment fragment, int requestCode, String... permissions){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return false;
        }

        fragment.requestPermissions(permissions,requestCode);
        return true;
    }
}
