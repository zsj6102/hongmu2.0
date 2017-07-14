package com.colpencil.redwood.function.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class ScrollListView extends ListView {

    private int downY;
    private ScroListener listener;

    public ScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                downY = (int) (downY - ev.getY());
                if (downY > 50 || downY < -50) {
                    if (listener != null) {
                        listener.scroll();
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    public void setListener(ScroListener listener) {
        this.listener = listener;
    }

    public interface ScroListener {
        void scroll();
    }
}
