package com.colpencil.redwood.function.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.TextView;

import com.colpencil.redwood.R;

/**
 * Created by lcodecore on 16/1/29.
 */
public class ExpandTextView extends TextView {

    private static final int MAX_COLLAPSED_LINES = 8;// The default number of lines 默认显示行数为8行
    private static final int DEFAULT_ANIM_DURATION = 300; // The default animation duration 默认动画时长为300ms
    private static final float DEFAULT_ANIM_ALPHA_START = 0.7f;// The default alpha value when the animation starts

    private int mMaxCollapsedLines;//最大显示行数
    private int mAnimationDuration;
    private float mAnimAlphaStart;
    private Drawable mExpandDrawable;//展开前显示图片
    private Drawable mCollapseDrawable;//展开后图片

    private int mCollapsedHeight;
    private int mTextHeightWithMaxLines;

    private boolean mCollapsed = true; // Show short version as default.标示现在所处的折叠状态
    private boolean mAnimating;
    private boolean needCollapse = true; //标示是否需要折叠已显示末尾的图标

    private SparseBooleanArray mCollapsedStatus;/* For saving collapsed status when used in ListView */
    public int mPosition;

    /* Listener for callback */
    private OnExpandStateChangeListener mListener;


    public ExpandTextView(Context context) {
        this(context, null);
    }

    public ExpandTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandTextView, defStyleAttr, 0);
        mMaxCollapsedLines = typedArray.getInt(R.styleable.ExpandTextView_maxCollapsedLines, MAX_COLLAPSED_LINES);
        mAnimationDuration = typedArray.getInt(R.styleable.ExpandTextView_animDuration, DEFAULT_ANIM_DURATION);
        mAnimAlphaStart = typedArray.getFloat(R.styleable.ExpandTextView_animAlphaStart, DEFAULT_ANIM_ALPHA_START);
        mExpandDrawable = typedArray.getDrawable(R.styleable.ExpandTextView_expandDrawable);
        mCollapseDrawable = typedArray.getDrawable(R.styleable.ExpandTextView_collapseDrawable);

        typedArray.recycle();

        if (mExpandDrawable == null) {
//            mExpandBm = BitmapFactory.decodeResource(getResources(),R.drawable.ic_expand_small_holo_light);
            mExpandDrawable = getDrawable(getContext(), R.mipmap.msg_open);
        }
        if (mCollapseDrawable == null) {
//            mCollapseBm = BitmapFactory.decodeResource(getResources(),R.drawable.ic_collapse_small_holo_light);
            mCollapseDrawable = getDrawable(getContext(), R.mipmap.msg_close);
        }

        setClickable(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getVisibility() == GONE) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        setMaxLines(Integer.MAX_VALUE);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        System.out.println("行数:" + getLineCount());
        if (getLineCount() <= mMaxCollapsedLines) {
            //不需要折叠
            needCollapse = false;
            return;
        }

        needCollapse = true;

        mTextHeightWithMaxLines = getRealTextViewHeight(this);
        System.out.println("总行数:" + mTextHeightWithMaxLines);
        if (mCollapsed) {
            setMaxLines(mMaxCollapsedLines);
        }

        //设置完成后重新测量
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mCollapsed) {
            // Saves the collapsed height of this ViewGroup
            mCollapsedHeight = getMeasuredHeight();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    public void onClickTextView() {
        System.out.println("文字输出:" + getText().toString());
        if (!needCollapse) {
            return;//行数不足,不响应点击事件
        }
        mCollapsed = !mCollapsed;
        Bitmap collapseBM = Bitmap.createBitmap(mCollapseDrawable.getIntrinsicWidth(), mCollapseDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas cv2 = new Canvas(collapseBM);
        mCollapseDrawable.setBounds(0, 0, mCollapseDrawable.getIntrinsicWidth(), mCollapseDrawable.getIntrinsicHeight());
        mCollapseDrawable.draw(cv2);

        ImageSpan isExpand = new ImageSpan(mExpandDrawable);
        ImageSpan isCollapse = new ImageSpan(getContext(), collapseBM);

        SpannableString spannableString = new SpannableString("icon");
        spannableString.setSpan(mCollapsed ? isExpand : isCollapse, 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (mCollapsedStatus != null) {
            mCollapsedStatus.put(mPosition, mCollapsed);
        }

        // mark that the animation is in progress
        mAnimating = true;

        Animation animation;
        if (mCollapsed) {
            animation = new ExpandCollapseAnimation(this, getHeight(), mCollapsedHeight);
        } else {
            System.out.println("展开动画");
            animation = new ExpandCollapseAnimation(this, getHeight(), mTextHeightWithMaxLines);
        }

        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                applyAlphaAnimation(ExpandTextView.this, mAnimAlphaStart);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // clear animation here to avoid repeated applyTransformation() calls
                clearAnimation();
                // clear the animation flag
                mAnimating = false;

                // notify the listener
                if (mListener != null) {
                    mListener.onExpandStateChanged(ExpandTextView.this, !mCollapsed);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        clearAnimation();
        startAnimation(animation);
    }

    class ExpandCollapseAnimation extends Animation {
        private final View mTargetView;
        private final int mStartHeight;
        private final int mEndHeight;

        public ExpandCollapseAnimation(View view, int startHeight, int endHeight) {
            mTargetView = view;
            mStartHeight = startHeight;
            mEndHeight = endHeight;
            setDuration(mAnimationDuration);
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            final int newHeight = (int) ((mEndHeight - mStartHeight) * interpolatedTime + mStartHeight);
            setMaxHeight(newHeight);
            if (Float.compare(mAnimAlphaStart, 1.0f) != 0) {
                applyAlphaAnimation(ExpandTextView.this, mAnimAlphaStart + interpolatedTime * (1.0f - mAnimAlphaStart));
            }
            mTargetView.getLayoutParams().height = newHeight;
            mTargetView.requestLayout();
        }

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }
    }

    ;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static Drawable getDrawable(@NonNull Context context, @DrawableRes int resId) {
        Resources resources = context.getResources();
        if (isPostLolipop()) {
            return resources.getDrawable(resId, context.getTheme());
        } else {
            return resources.getDrawable(resId);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static void applyAlphaAnimation(View view, float alpha) {
        if (isPostHoneycomb()) {
            view.setAlpha(alpha);
        } else {
            AlphaAnimation alphaAnimation = new AlphaAnimation(alpha, alpha);
            // make it instant
            alphaAnimation.setDuration(0);
            alphaAnimation.setFillAfter(true);
            view.startAnimation(alphaAnimation);
        }
    }

    private static boolean isPostHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    private static boolean isPostLolipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    private static int getRealTextViewHeight(@NonNull TextView textView) {
        int textHeight = textView.getLayout().getLineTop(textView.getLineCount());
        int padding = textView.getCompoundPaddingTop() + textView.getCompoundPaddingBottom();
        return textHeight + padding;
    }

    private int sp2dp(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }


    public interface OnExpandStateChangeListener {
        /**
         * Called when the expand/collapse animation has been finished
         *
         * @param textView   - TextView being expanded/collapsed
         * @param isExpanded - true if the TextView has been expanded
         */
        void onExpandStateChanged(TextView textView, boolean isExpanded);
    }

    public void setOnExpandStateChangeListener(@Nullable OnExpandStateChangeListener listener) {
        mListener = listener;
    }

    public boolean ismCollapsed() {
        return mCollapsed;
    }
}
