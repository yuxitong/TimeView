package timeview.yxt.com.timeview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import timeview.yxt.com.timeview.utils.TimeUtils;

public class TimeSurfaceView extends SurfaceView implements
        SurfaceHolder.Callback, Runnable {

    private Thread thread;
    //是否在运行
    private boolean isRunning = true;

    private SurfaceHolder holder;

    private Paint mPaint;
    private Paint mPaintSelect;
    //年
    private int year;
    //月
    private int month;
    //天
    private int day;
    //周
    private int week;
    //时
    private int hour;
    //分
    private int branch;
    //秒
    private int second;
    //当前有多少天
    private int dayCount;

    //秒针转动度数
    private float secondDegrees;
    //分针转动度数
    private float branchDegrees;
    //月份的转动度数
    private float monthDegrees;
    //天的转动度数
    private float dayDegrees;
    //小时的转动度数
    private float hourDegrees;
    //周的转动度数
    private float weekDegrees;


    private float yuan = 0f;

    //中心点X
    private float px;
    //中心店Y
    private float py;

    //默认字体大小15px
    private float textSize = 0f;
    //默认字体间距5px
    public float textSpacing = 5f;

    private String[] countTag = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二", "十三", "十四", "十五"
            , "十六", "十七", "十八", "十九", "二十", "二十一", "二十二", "二十三", "二十四", "二十五", "二十六", "二十七", "二十八", "二十九", "三十"
            , "三十一", "三十二", "三十三", "三十四", "三十五", "三十六", "三十七", "三十八", "三十九", "四十", "四十一", "四十二", "四十三", "四十四", "四十五"
            , "四十六", "四十七", "四十八", "四十九", "五十", "五十一", "五十二", "五十三", "五十四", "五十五", "五十六", "五十七", "五十八", "五十九", "零"};

    public TimeSurfaceView(Context context) {
        this(context,(AttributeSet)null);

    }

    public TimeSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public TimeSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(getHolder());
    }

    private void init(SurfaceHolder holder) {
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(textSize);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaintSelect = new Paint();
        mPaintSelect.setColor(Color.RED);
        mPaintSelect.setAntiAlias(true);
        mPaintSelect.setTextSize(textSize);
        mPaintSelect.setTextAlign(Paint.Align.CENTER);

        this.holder = holder;
        this.holder.addCallback(this);
        this.holder.setFormat(PixelFormat.RGBA_8888);

        year = Integer.valueOf(TimeUtils.getCurTimeString(new SimpleDateFormat("yyyy")));
        month = Integer.valueOf(TimeUtils.getCurTimeString(new SimpleDateFormat("MM")));
        day = Integer.valueOf(TimeUtils.getCurTimeString(new SimpleDateFormat("dd")));
        hour = Integer.valueOf(TimeUtils.getCurTimeString(new SimpleDateFormat("HH")));
        branch = Integer.valueOf(TimeUtils.getCurTimeString(new SimpleDateFormat("mm")));
        second = Integer.valueOf(TimeUtils.getCurTimeString(new SimpleDateFormat("ss")));
        week = TimeUtils.getWeek();
        Log.e("11111", week + "    " + month);
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 0);
        dayCount = cal.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if(thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning) {
            Canvas canvas = null;
            try {
                canvas = holder.lockCanvas();
                if (canvas != null) {
                    canvas.drawColor(Color.WHITE);
                    if (px == 0)
                        px = getWidth() / 2;
                    if (py == 0) {
                        py = getHeight() / 2;
                        if (textSize == 0f) {
                            float autoTextSize = (px > py ? py : px - textSpacing * 6) / 27f;
                            mPaint.setTextSize(autoTextSize);
                            mPaintSelect.setTextSize(autoTextSize);
                        }
                    }
                    if (yuan != 360f) {
                        yuan = yuan + 5;
                    } else {
                        year = Integer.valueOf(TimeUtils.getCurTimeString(new SimpleDateFormat("yyyy")));
                        month = Integer.valueOf(TimeUtils.getCurTimeString(new SimpleDateFormat("MM")));
                        day = Integer.valueOf(TimeUtils.getCurTimeString(new SimpleDateFormat("dd")));
                        hour = Integer.valueOf(TimeUtils.getCurTimeString(new SimpleDateFormat("HH")));
                        branch = Integer.valueOf(TimeUtils.getCurTimeString(new SimpleDateFormat("mm")));
                        second = Integer.valueOf(TimeUtils.getCurTimeString(new SimpleDateFormat("ss")));
                        week = TimeUtils.getWeek();
                    }

                    //绘制年
                    canvas.drawText("二零一九年", px, py, mPaintSelect);

                    //绘制月
                    float monthlen = mPaintSelect.measureText("二零一九年") / 2 + px + mPaintSelect.measureText("十二月") / 2 + textSpacing;
                    canvas.save();
                    monthDegrees = getCircumference(12, month, monthDegrees);
                    canvas.rotate(monthDegrees, px, py);
                    onDrawContent(canvas, "月", 12, monthlen, py, month - 1);
                    canvas.restore();

                    //绘制日
                    float dayLen = monthlen + mPaintSelect.measureText("三十一日") / 2 + mPaintSelect.measureText("十二月") / 2 + textSpacing;
                    canvas.save();
                    dayDegrees = getCircumference(dayCount, day, dayDegrees);
                    canvas.rotate(dayDegrees, px, py);
                    onDrawContent(canvas, "日", dayCount, dayLen, py, day - 1);
                    canvas.restore();

                    //绘制星期
                    float xingqiLen = dayLen + mPaintSelect.measureText("三十一日") / 2 + mPaintSelect.measureText("星期一") / 2 + textSpacing;
                    canvas.save();
                    weekDegrees = getCircumference(7, week, weekDegrees);
                    canvas.rotate(weekDegrees, px, py);
                    onDrawContent(canvas, "星期", 7, xingqiLen, py, week - 1);
                    canvas.restore();

                    //绘制时
                    float hourLen = xingqiLen + mPaintSelect.measureText("二十四时") / 2 + mPaintSelect.measureText("星期一") / 2 + textSpacing;
                    canvas.save();
                    hourDegrees = getCircumference(24, hour, hourDegrees);
                    Log.e("bbb", hour + " ");
                    canvas.rotate(hourDegrees, px, py);
                    onDrawContent(canvas, "时", 24, hourLen, py, hour - 1);
                    canvas.restore();

                    //绘制分
                    float branchLen = hourLen + mPaintSelect.measureText("二十四时") / 2 + mPaintSelect.measureText("五十九分") / 2 + textSpacing;
                    canvas.save();
                    branchDegrees = getCircumference(60, branch, branchDegrees);
                    canvas.rotate(branchDegrees, px, py);
                    onDrawContent(canvas, "分", 60, branchLen, py, branch - 1);
                    canvas.restore();

                    //绘制秒
                    float secondLen = branchLen + mPaintSelect.measureText("五十九秒") / 2 + mPaintSelect.measureText("五十九分") / 2 + textSpacing;
                    canvas.save();
                    secondDegrees = getCircumference(60, second, secondDegrees);
                    canvas.rotate(secondDegrees, px, py);
                    onDrawContent(canvas, "秒", 60, secondLen, py, second - 1);
                    canvas.restore();
                }
                Thread.sleep(20);
            } catch (Exception e) {

            } finally {
                if (canvas != null) {
                    holder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    /**
     * 返回当前圆周率
     *
     * @param number               一圈有多少个数字
     * @param arrive               当前在第几个数字
     * @param currentCircumference 当前圆周率
     * @return
     */
    private float getCircumference(int number, int arrive, float currentCircumference) {
        currentCircumference = currentCircumference > -(360f / number) * (arrive - 1) ? currentCircumference - 1 : -(360f / number) * (arrive - 1);
        return currentCircumference;
    }

    private void onDrawContent(Canvas canvas, String company, int count, float x, float y, int Selection) {
        canvas.save();
        if (Selection == -1)
            Selection = count - 1;
        for (int i = 0; i < count; i++) {
            String text = company.contains("星期") ? company + countTag[i] : countTag[i] + company;
            if (i == Selection) {
                if (text.equals("二十四时"))
                    text = "零时";
                canvas.drawText(text, x, y, mPaintSelect);
            } else {
                canvas.drawText(text, x, y, mPaint);
            }
            canvas.rotate(yuan / count, px, py);
        }
        canvas.restore();
    }

    //设置字体大小    px
    public void setTextSize(float textSize) {
        this.textSize = textSize;
        mPaint.setTextSize(textSize);
        mPaintSelect.setTextSize(textSize);
    }

    //设置不同单位之间的距离  px
    public void setTextSpacing(float textSpacing) {
        this.textSpacing = textSpacing;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            isRunning = false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
