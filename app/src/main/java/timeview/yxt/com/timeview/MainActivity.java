package timeview.yxt.com.timeview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import timeview.yxt.com.timeview.utils.WallpaperUtil;
import timeview.yxt.com.timeview.view.TimeView;

public class MainActivity extends AppCompatActivity {

    private final static int REQUEST_CODE_SET_WALLPAPER = 0x001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        TimeView tv = findViewById(R.id.aaa);
//        tv.setTextSize(12);
    }

    public void onClick(View view){

        switch (view.getId()){
            case R.id.button:

                WallpaperUtil.setLiveWallpaper(this.getApplicationContext(), MainActivity.this, MainActivity.REQUEST_CODE_SET_WALLPAPER,TimeWallpaperService.class);
                break;
        }
    }
}
