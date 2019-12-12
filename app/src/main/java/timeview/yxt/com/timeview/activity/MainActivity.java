package timeview.yxt.com.timeview.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import timeview.yxt.com.timeview.R;
import timeview.yxt.com.timeview.TimeWallpaperService;
import timeview.yxt.com.timeview.utils.WallpaperUtil;

public class MainActivity extends AppCompatActivity {

    private final static int REQUEST_CODE_SET_WALLPAPER = 0x001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        TimeView tv = findViewById(R.id.aaa);
//        tv.setTextSize(12);

//        YUEGLSurfaceView tv = findViewById(R.id.aaa);
//        tv.setRender(new BitmapRender(this));

    }

    public void onClick(View view){

        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.view:
                intent.setClass(this,ViewActivity.class);
                break;
            case R.id.surface:
                intent.setClass(this,SurfaceActivity.class);
                break;
            case R.id.glsurface:
                intent.setClass(this,GLSurfaceActivity.class);
                break;
        }

        startActivity(intent);
    }
}
