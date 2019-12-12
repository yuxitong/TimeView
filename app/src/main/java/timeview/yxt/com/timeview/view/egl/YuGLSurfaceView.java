package timeview.yxt.com.timeview.view.egl;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;

import timeview.yxt.com.timeview.view.egl.render.CameraRender;

public class YuGLSurfaceView extends YUEGLSurface {
    private Context context;
    CameraRender bitmapRender;

    public YuGLSurfaceView(Context context) {
        this(context,null);
    }

    public YuGLSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public YuGLSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
        this.context = context;
    }

    public YuGLSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        super.surfaceChanged(holder,format,width,height);
//        bitmapRender.setWidthAndHeight(width,height);

        bitmapRender =  new CameraRender(context,width,height);
        setRender(bitmapRender);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        super.surfaceDestroyed(holder);
        bitmapRender.onDeleteTextureId();
        bitmapRender.release();
        bitmapRender = null;
    }
}
