package com.wolfsea.signviewdemo;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

/**
 * @author liuliheng
 * @desc  选择器按钮
 * @time 2020/11/8  10:08
 **/
public class SelectorAppCompatButton extends AppCompatButton {

    public SelectorAppCompatButton(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public SelectorAppCompatButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public SelectorAppCompatButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        final int ACTION = event.getAction();
        switch (ACTION) {
            case MotionEvent.ACTION_DOWN: {

                this.setBackgroundColor(getBackgroundColor(android.R.color.holo_blue_dark));
                break;
            }
            case MotionEvent.ACTION_UP: {

                this.setBackgroundColor(getBackgroundColor(android.R.color.holo_blue_light));
                break;
            }
            default:
                break;
        }
        return false;
    }

    private int getBackgroundColor(int colorRes) {
        return context.getResources().getColor(colorRes);
    }

    private Context context;

}
