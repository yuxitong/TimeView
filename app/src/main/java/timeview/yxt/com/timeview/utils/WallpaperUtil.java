package timeview.yxt.com.timeview.utils;

import android.app.Activity;
import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;


public class WallpaperUtil {

    /**
     * 跳转到系统设置壁纸界面
     *
     * @param context
     * @param paramActivity
     */
    public static void setLiveWallpaper(Context context, Activity paramActivity, int requestCode, Class T) {
        try {
            Intent localIntent = new Intent();
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {//ICE_CREAM_SANDWICH_MR1  15
                localIntent.setAction(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);//android.service.wallpaper.CHANGE_LIVE_WALLPAPER
                //android.service.wallpaper.extra.LIVE_WALLPAPER_COMPONENT
                localIntent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT
                        , new ComponentName(context.getApplicationContext().getPackageName()
                                , T.getCanonicalName()));
            } else {
                localIntent.setAction(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER);//android.service.wallpaper.LIVE_WALLPAPER_CHOOSER
            }
            paramActivity.startActivityForResult(localIntent, requestCode);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    /**
     * 判断是否是使用我们的壁纸
     *
     * @param paramContext
     * @return
     */
    public static boolean wallpaperIsUsed(Context paramContext, Class T) {
        WallpaperInfo localWallpaperInfo = WallpaperManager.getInstance(paramContext).getWallpaperInfo();
        return ((localWallpaperInfo != null) && (localWallpaperInfo.getPackageName().equals(paramContext.getPackageName())) &&
                (localWallpaperInfo.getServiceName().equals(T.getCanonicalName())));
    }

    public static Bitmap getDefaultWallpaper(Context paramContext) {
        Bitmap localBitmap;
        if (isLivingWallpaper(paramContext))
            localBitmap = null;
        do {
            localBitmap = ((BitmapDrawable) WallpaperManager.getInstance(paramContext).getDrawable()).getBitmap();
            return localBitmap;
        }
        while (localBitmap != null);
    }

    public static boolean isLivingWallpaper(Context paramContext) {
        return (WallpaperManager.getInstance(paramContext).getWallpaperInfo() != null);
    }
}
