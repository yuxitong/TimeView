package timeview.yxt.com.timeview;

import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;

import timeview.yxt.com.timeview.view.Time;

public class TimeWallpaperService extends WallpaperService {
    private TimeWallpaperEngine twe;
    private final static long REFLESH_GAP_TIME = 20L;//如果想播放的流畅，需满足1s 16帧   62ms切换间隔时间

    @Override
    public Engine onCreateEngine() {
        this.twe = new TimeWallpaperEngine();
        return this.twe;
    }


    private class TimeWallpaperEngine extends TimeWallpaperService.Engine {

        private Runnable viewRunnable;
        private Handler handler;
        private Time tsv;
        private final SurfaceHolder surfaceHolder;
        private int screenWidth;
        private int screenHeight;

        public TimeWallpaperEngine() {
            this.surfaceHolder = getSurfaceHolder();
            this.tsv = new Time();
            this.handler = new Handler();
            this.initRunnable();
            this.handler.post(this.viewRunnable);
            DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
            screenWidth = dm.widthPixels;
            screenHeight = dm.heightPixels;
        }

        private void initRunnable() {
            this.viewRunnable = new Runnable() {
                @Override
                public void run() {
                    TimeWallpaperEngine.this.drawView();
                }
            };
        }

        private void drawView() {
            if (this.tsv == null) {
                return;
            } else {
                this.handler.removeCallbacks(this.viewRunnable);
                this.tsv.onDrawTime(this.surfaceHolder, screenWidth, screenHeight);
                if (!(isVisible())) {
                    return;
                } else {
                    this.handler.postDelayed(this.viewRunnable, REFLESH_GAP_TIME);
                }
            }
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            this.drawView();
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
            this.drawView();
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            if (this.handler != null) {
                if (visible) {
                    this.handler.removeCallbacks(this.viewRunnable);
                    this.handler.post(this.viewRunnable);
                } else {
                    this.handler.removeCallbacks(this.viewRunnable);
                }
            }
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            if (this.handler != null) {
                this.handler.removeCallbacks(this.viewRunnable);
            }
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            if (this.handler != null) {
                this.handler.removeCallbacks(this.viewRunnable);
            }
        }
    }
}
