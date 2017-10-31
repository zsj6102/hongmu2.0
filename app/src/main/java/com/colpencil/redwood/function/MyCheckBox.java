package com.colpencil.redwood.function;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.colpencil.redwood.R;

public class MyCheckBox extends CheckBox {
    private static final String TAG = "MyCheckBox";
    private int unCheckedResId;
    private int checkedResId;

    public MyCheckBox(Context context) {
        this(context, null);
    }

    public MyCheckBox(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyRadioButton, defStyleAttr, 0);
        unCheckedResId = ta.getResourceId(R.styleable.MyRadioButton_unCheckedResId, 0);
        checkedResId = ta.getResourceId(R.styleable.MyRadioButton_checkedResId, 0);
        updateView();
        this.setClickable(true);
        this.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateView();
            }
        });

    }

    private void updateView() {
        if (isChecked()) {
            this.setBackgroundResource(checkedResId);
        } else {
            this.setBackgroundResource(unCheckedResId);
        }
    }

    public void setIconResId(int unCheckedResId, int checkedResId) {
        this.unCheckedResId = unCheckedResId;
        this.checkedResId = checkedResId;
        updateView();
    }


}
