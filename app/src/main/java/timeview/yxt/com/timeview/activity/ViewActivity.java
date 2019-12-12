package timeview.yxt.com.timeview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import timeview.yxt.com.timeview.R;
import timeview.yxt.com.timeview.TimeWallpaperService;
import timeview.yxt.com.timeview.utils.WallpaperUtil;

public class ViewActivity extends AppCompatActivity {
    private final static int REQUEST_CODE_SET_WALLPAPER = 0x001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
    }

    public void onClick(View view){

        switch (view.getId()){
            case R.id.button:
                WallpaperUtil.setLiveWallpaper(
                        this.getApplicationContext(),
                        ViewActivity.this,
                        ViewActivity.REQUEST_CODE_SET_WALLPAPER,
                        TimeWallpaperService.class);
                break;
        }
    }
}
