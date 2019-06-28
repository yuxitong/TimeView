package timeview.yxt.com.timeview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class TimeView extends View {
    private Paint mPaint;

    private int width = 100;

    private int rot;

    //计算月份前进的系数
    private int monthX = 0;
    private String[] month = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二", "十三", "十四", "十五"
            , "十六", "十七", "十八", "十九", "二十", "二十一", "二十二", "二十三", "二十四", "二十五", "二十六", "二十七", "二十八", "二十九", "三十"
            , "三十一", "三十二", "三十三", "三十四", "三十五", "三十六", "三十七", "三十八", "三十九", "四十", "四十一", "四十二", "四十三", "四十四", "四十五"
            , "四十六", "四十七", "四十八", "四十九", "五十", "五十一", "五十二", "五十三", "五十四", "五十五", "五十六", "五十七", "五十八", "五十九"};

    public TimeView(Context context) {
        this(context, null);
    }

    public TimeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public TimeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(100);
                        rot++;
                        postInvalidate();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        // 传入相同的数width，height,确保是正方形背景
//        setMeasuredDimension(measureSize(widthMeasureSpec), measureSize(heightMeasureSpec));
//    }
//
//    // 这里不用管测量模式是什么，因为咱们有屏幕短边保底，只取其中一个小值即可。测量宽高和屏幕短边作对比，返回最小值
//    private int measureSize(int measureSpec) {
//        int size = MeasureSpec.getSize(measureSpec);
//        width = Math.min(width, size);
//        return width;
//    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
//        canvas.rotate(90, 500 / 2, 500 / 2);

        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("二零一九年", 430, 300, mPaint);
        canvas.rotate(rot, 430, 300);
        for (int i = 0; i < 60; i++) {
            if (i % 5 == 0) {
//                mPaint.setStrokeWidth(SizeUtils.Dp2Px(getContext(), 1.5f));
//                mPaint.setColor(mColorLong);
//                lineWidth = 40;
                // 这里是字体的绘制
//                mPaint.setTextSize(mTextSize);
                monthX = monthX % 12;
                int a = i + monthX * 5;
                int index = (((a / 5) == 0 ? 12 - 1 : (a / 5) - 1));
                String strMonth = month[index > 11 ? index - 12 : index];
                String text =  strMonth.length() == 2 ? strMonth+"月":strMonth+"  月" ;
//                Rect textBound = new Rect();
//                mPaint.getTextBounds(text, 0, text.length(), textBound);
//                mPaint.setColor(Color.BLACK);
                canvas.drawText(text, 500, 300, mPaint);
            }
            canvas.rotate(6, 430, 300);

            if(i%(60/30) == 0){
                monthX = monthX % 30;
                int a = i + monthX * (60/30);
                int index = (((a / (60/30)) == 0 ? 30 - 1 : (a / (60/30)) - 1));
                String strMonth = month[index > 29 ? index - 30 : index];
                String text =  strMonth.length() == 2 ? strMonth+"日":strMonth+"日" ;
//                Rect textBound = new Rect();
//                mPaint.getTextBounds(text, 0, text.length(), textBound);
//                mPaint.setColor(Color.BLACK);
                canvas.drawText(text, 550, 300, mPaint);
            }

            if(i%(60/7) == 0){
                monthX = monthX % 7;
                int a = i + monthX * (60/7);
                int index = (((a / (60/7)) == 0 ? 7 - 1 : (a / (60/7)) - 1));
                String strMonth = month[index > 6 ? index - 7 : index];
                String text =  strMonth.length() == 2 ? "星期"+strMonth:"星期"+strMonth ;
//                Rect textBound = new Rect();
//                mPaint.getTextBounds(text, 0, text.length(), textBound);
//                mPaint.setColor(Color.BLACK);
                canvas.drawText(text, 600, 300, mPaint);
            }
            if(i%(60/24) == 0){
                monthX = monthX % 24;
                int a = i + monthX * (60/24);
                int index = (((a / (60/24)) == 0 ? 24 - 1 : (a / (60/24)) - 1));
                String strMonth = month[index > 23 ? index - 24 : index];
                String text =  strMonth.length() == 2 ? strMonth+"时":strMonth+"时" ;
//                Rect textBound = new Rect();
//                mPaint.getTextBounds(text, 0, text.length(), textBound);
//                mPaint.setColor(Color.BLACK);
                canvas.drawText(text, 650, 300, mPaint);
            }

        }
//        RectF f = new RectF(400,200,800,500);
//        int saveCount = canvas.saveLayer(f,mPaint);
//        canvas.drawText("二零一九年", 500, 300, mPaint);


//        canvas.translate(500,500);

//        Path path = new Path();
//        path.addCircle(100, 100, 50, Path.Direction.CW);
//        canvas.drawTextOnPath("aling a path", path, 0, 0, mPaint);

    }


}
