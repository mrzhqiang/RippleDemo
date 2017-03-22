package cn.qiang.zhang.rippledemo.widgets;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import cn.qiang.zhang.rippledemo.R;

/**
 * <p>
 * Created by mrZQ on 2017/3/21.
 */
public class LakeLayout extends RelativeLayout {

    private static final int DEFAULT_DRAGONFLY_COUNT = 6;
    private static final int DEFAULT_DURATION = 3000;
    private static final float DEFAULT_SPREAD_SCALE = 2.0f;
    private static final int DEFAULT_FILL_TYPE = 0;

    private float rippleRadius;
    private float rippleStrokeWidth;

    private Paint paint;

    private boolean animationRunning = false;
    private AnimatorSet animatorSet;
    private ArrayList<DragonflyView> list = new ArrayList<>();

    public LakeLayout(Context context) {
        super(context);
    }

    public LakeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LakeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(final Context context, final AttributeSet attrs) {
        if (isInEditMode()) { return; }

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LakeLayout);

        rippleRadius = typedArray.getDimension(
                R.styleable.LakeLayout_rb_radius, getResources().getDimension(R.dimen.rippleRadius));
        rippleStrokeWidth = typedArray.getDimension(
                R.styleable.LakeLayout_rb_strokeWidth, getResources().getDimension(R.dimen.rippleStrokeWidth));
        int rippleColor = typedArray.getColor(
                R.styleable.LakeLayout_rb_color, ContextCompat.getColor(context, R.color.colorPrimary));

        int rippleDuration = typedArray.getInt(R.styleable.LakeLayout_rb_duration, DEFAULT_DURATION);
        if (rippleDuration < 500) {
            rippleDuration = 500;
        }
        int dragonflyCount = typedArray.getInt(R.styleable.LakeLayout_rb_rippleAmount, DEFAULT_DRAGONFLY_COUNT);

        int rippleDelay = typedArray.getInt(R.styleable.LakeLayout_rb_delay, 0);
        if (rippleDelay == 0) {
            if (dragonflyCount > 0) {
                rippleDelay = rippleDuration / dragonflyCount;
            } else {
                rippleDelay = rippleDuration;
            }
        }

        if (rippleDelay < 100) {
            rippleDelay = 100;
        }

        float spreadScale = typedArray.getFloat(R.styleable.LakeLayout_rb_scale, DEFAULT_SPREAD_SCALE);
        int rippleType = typedArray.getInt(R.styleable.LakeLayout_rb_type, DEFAULT_FILL_TYPE);

        typedArray.recycle();

        paint = new Paint();
        paint.setAntiAlias(true);
        if (rippleType == DEFAULT_FILL_TYPE) {
            rippleStrokeWidth = 0;
            paint.setStyle(Paint.Style.FILL);
        } else {
            paint.setStyle(Paint.Style.STROKE);
        }
        paint.setColor(rippleColor);

        LayoutParams params = new LayoutParams((int) (2 * (rippleRadius + rippleStrokeWidth)),
                                               (int) (2 * (rippleRadius + rippleStrokeWidth)));
        params.addRule(CENTER_IN_PARENT, TRUE);

        animatorSet = new AnimatorSet();
        Interpolator interpolator = new LinearInterpolator();

        animatorSet.setInterpolator(interpolator);

        ArrayList<Animator> animators = new ArrayList<>();

        for (int i = 0; i < dragonflyCount; i++) {
            DragonflyView dragonflyView = new DragonflyView(context);
            addView(dragonflyView, params);
            list.add(dragonflyView);

            final ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(dragonflyView, "ScaleX", 1.0f, spreadScale);
            scaleXAnimator.setRepeatCount(ObjectAnimator.INFINITE);
            scaleXAnimator.setRepeatMode(ObjectAnimator.RESTART);
            scaleXAnimator.setStartDelay(i * rippleDelay);
            scaleXAnimator.setDuration(rippleDuration);
            scaleXAnimator.setInterpolator(new AccelerateInterpolator());
            animators.add(scaleXAnimator);
            final ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(dragonflyView, "ScaleY", 1.0f, spreadScale);
            scaleYAnimator.setRepeatCount(ObjectAnimator.INFINITE);
            scaleYAnimator.setRepeatMode(ObjectAnimator.RESTART);
            scaleYAnimator.setStartDelay(i * rippleDelay);
            scaleYAnimator.setDuration(rippleDuration);
            scaleXAnimator.setInterpolator(new AccelerateInterpolator());
            animators.add(scaleYAnimator);
            final ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(dragonflyView, "Alpha", 1.0f, 0f);
            alphaAnimator.setRepeatCount(ObjectAnimator.INFINITE);
            alphaAnimator.setRepeatMode(ObjectAnimator.RESTART);
            alphaAnimator.setStartDelay(i * rippleDelay);
            alphaAnimator.setDuration(rippleDuration);
            scaleXAnimator.setInterpolator(new DecelerateInterpolator());
            animators.add(alphaAnimator);
        }

        animatorSet.playTogether(animators);
    }

    public void startRippleAnimation() {
        if (!isRippleAnimationRunning()) {
            for (DragonflyView dragonflyView : list) {
                dragonflyView.setVisibility(VISIBLE);
            }
            animatorSet.start();
            animationRunning = true;
        }
    }

    public void stopRippleAnimation() {
        if (isRippleAnimationRunning()) {
            animatorSet.end();
            animationRunning = false;
        }
    }

    public boolean isRippleAnimationRunning() {
        return animationRunning;
    }

    private class DragonflyView extends View {

        public DragonflyView(Context context) {
            super(context);
            this.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawCircle(rippleRadius, rippleRadius, rippleRadius - rippleStrokeWidth, paint);
        }
    }

}
