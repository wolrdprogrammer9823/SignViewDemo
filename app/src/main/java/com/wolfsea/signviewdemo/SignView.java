package com.wolfsea.signviewdemo;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *@desc 用户签名类
 *@author:liuliheng
 *@time: 2020/10/24 0:10
**/
public class SignView extends View {

    private Paint paint;

    private CopyOnWriteArrayList<Float> x_axis;
    private CopyOnWriteArrayList<Float> y_axis;

    private ArrayList<CopyOnWriteArrayList<Float>> total_x_axis;
    private ArrayList<CopyOnWriteArrayList<Float>> total_y_axis;

    public SignView(Context context) {
        super(context);
        init();
    }

    public SignView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SignView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        drawTotalSign(total_x_axis, total_y_axis, canvas);
        drawSign(x_axis, y_axis, canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        final int ACTION = event.getAction();
        switch (ACTION) {
            case MotionEvent.ACTION_DOWN: {

                performClick();

                total_x_axis.add(new CopyOnWriteArrayList<>(x_axis));
                total_y_axis.add(new CopyOnWriteArrayList<>(y_axis));

                x_axis.clear();
                y_axis.clear();

                x_axis.add(event.getX());
                y_axis.add(event.getY());
                break;
            }
            case MotionEvent.ACTION_MOVE: {

                x_axis.add(event.getX());
                y_axis.add(event.getY());

                invalidate();
                break;
            }
            default:
                break;
        }

        return true;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    /**
     *@desc 绘制单一轨迹
     *@author:liuliheng
     *@time: 2020/10/24 0:27
    **/
    private void drawSign(CopyOnWriteArrayList<Float> x_axis,
                          CopyOnWriteArrayList<Float> y_axis, Canvas canvas) {

        final int SIZE = x_axis.size() - 1;

        for (int i = 0; i < SIZE; i++) {

            float x0 = x_axis.get(i);
            float y0 = y_axis.get(i);

            float x1 = x_axis.get(i + 1);
            float y1 = y_axis.get(i + 1);

            canvas.drawLine(x0, y0, x1, y1, paint);
        }
    }

    /**
     *@desc 绘制所有的轨迹
     *@author:liuliheng
     *@time: 2020/10/24 0:27
    **/
    private void drawTotalSign(ArrayList<CopyOnWriteArrayList<Float>> total_x_axis,
                               ArrayList<CopyOnWriteArrayList<Float>> total_y_axis, Canvas canvas) {

        final int SIZE = total_x_axis.size();

        for (int i = 0; i < SIZE; i++) {

            drawSign(total_x_axis.get(i), total_y_axis.get(i), canvas);
        }
    }

    /**
     *@desc 重绘
     *@author:liuliheng
     *@time: 2020/10/24 0:27
    **/
    public void redraw() {

        x_axis.clear();
        y_axis.clear();

        total_x_axis.clear();
        total_y_axis.clear();

        invalidate();
    }

    /**
     *@desc 把轨迹数据转换成Bitmap
     *@author:liuliheng
     *@time: 2020/11/6 0:31
    **/
    public Bitmap convertDataToBitmap() {

        int width = this.getMeasuredWidth();
        int height = this.getMeasuredHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        this.draw(canvas);

        return bitmap;
    }

    /**
     *@desc 初始化方法
     *@author:liuliheng
     *@time: 2020/10/24 0:27
    **/
    private void init() {

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setDither(true);
        paint.setStrokeWidth(3.0f);
        paint.setColor(Color.BLACK);

        x_axis = new CopyOnWriteArrayList<>();
        y_axis = new CopyOnWriteArrayList<>();

        total_x_axis = new ArrayList<>();
        total_y_axis = new ArrayList<>();
    }
}
