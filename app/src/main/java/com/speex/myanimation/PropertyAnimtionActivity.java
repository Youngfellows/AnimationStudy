package com.speex.myanimation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.speex.myanimation.utils.ViewWrapper;

public class PropertyAnimtionActivity extends AppCompatActivity {
    private String TAG = this.getClass().getSimpleName();
    private ImageView mImgCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animtion);
        mImgCat = (ImageView) findViewById(R.id.img_cat);


    }

    @Override
    protected void onResume() {
        super.onResume();
        getImageWithAndHeight();
    }

    private void getImageWithAndHeight() {
        //方式2获取控件的宽高
        mImgCat.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressLint("NewApi")
            @Override
            public void onGlobalLayout() {
                mImgCat.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int with = mImgCat.getMeasuredWidth();
                int height = mImgCat.getMeasuredHeight();
                Log.i(TAG, "onGlobalLayout with: " + with + " ,height: " + height);
            }
        });

        //方式3获取控件的宽高
        mImgCat.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mImgCat.getViewTreeObserver().removeOnPreDrawListener(this);
                int with = mImgCat.getMeasuredWidth();
                int height = mImgCat.getMeasuredHeight();
                Log.i(TAG, "onPreDraw with: " + with + " ,height: " + height);
                return true;
            }
        });
    }

    /**
     * ValueAnimator改变宽度
     *
     * @param view
     */
    public void valueAnimatorChangeWith(View view) {
        //(方式1)获取控件的宽高
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mImgCat.measure(w, h);
        int width = mImgCat.getMeasuredWidth();
        int height = mImgCat.getMeasuredHeight();
        Log.i(TAG, "1 width: " + width + " ,height: " + height);

        //创建动画作用对象
        //步骤1：设置属性数值的初始值 & 结束值
//        width = mImgCat.getLayoutParams().width;
//        height = mImgCat.getLayoutParams().height;
        Log.i(TAG, "2 width: " + width + " ,height: " + height);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(width, width * 2);

        // 步骤2：设置动画的播放各种属性
        valueAnimator.setDuration(2000);

        // 步骤3：将属性数值手动赋值给对象的属性:此处是将 值 赋给 图片的宽度
        // 设置更新监听器：即数值每次变化更新都会调用该方法
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 获得每次变化后的属性值
                int currentValue = (int) animation.getAnimatedValue();
                Log.i(TAG, "currentValue: " + currentValue);

                // 每次值变化时，将值手动赋值给对象的属性
                // 即将每次变化后的值 赋 给图片的宽度，这样就实现了按钮宽度属性的动态变化
                ViewGroup.LayoutParams layoutParams = mImgCat.getLayoutParams();
                layoutParams.width = currentValue;
                layoutParams.height = currentValue;

                // 步骤4：刷新视图，即重新绘制，从而实现动画效果
//                mImgCat.setLayoutParams(layoutParams);
                mImgCat.requestLayout();
            }
        });

        // 启动动画
        valueAnimator.start();
    }

    /**
     * 将初始值 以对象的形式 过渡到结束值
     *
     * @param view
     */
    public void alueAnimatorofObject(View view) {
        Intent intent = new Intent();
        intent.setClass(this, ValueAnimatorOfObjectActivity.class);
        startActivity(intent);
    }


    /**
     * 透明度动画
     *
     * @param view
     */
    public void alpha(View view) {
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mImgCat, "alpha", 1f, 0.5f, 0f, 0.5f, 1f);
        alphaAnimator.setDuration(500 * 2 * 2);
        alphaAnimator.setRepeatMode(ValueAnimator.REVERSE);
        alphaAnimator.setRepeatCount(6);
        alphaAnimator.start();
    }

    /**
     * 旋转动画
     *
     * @param view
     */
    public void rotationAnim(View view) {
        ObjectAnimator rotationAnim = ObjectAnimator.ofFloat(mImgCat, "rotation", 0f, 360f);
        rotationAnim.setDuration(500 * 2);
        rotationAnim.setRepeatMode(ValueAnimator.RESTART);
        rotationAnim.setRepeatCount(5);
        rotationAnim.start();
        rotationAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.i(TAG, "onAnimationStart: ");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.i(TAG, "onAnimationEnd: ");
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.i(TAG, "onAnimationCancel: ");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.i(TAG, "onAnimationRepeat: ");
            }
        });
    }

    /**
     * 平移动画
     *
     * @param view
     */
    public void translationX(View view) {
        float currentTranslationX = mImgCat.getTranslationX();
        ObjectAnimator translationAnim = ObjectAnimator.ofFloat(mImgCat, "translationX", currentTranslationX, 300, currentTranslationX);
        translationAnim.setDuration(100 * 10);
        translationAnim.setRepeatMode(ValueAnimator.REVERSE);
        translationAnim.setRepeatCount(5);
        translationAnim.start();
    }

    /**
     * 平移动画
     *
     * @param view
     */
    public void translationY(View view) {
        float currentTranslationY = mImgCat.getTranslationY();
        ObjectAnimator translationAnim = ObjectAnimator.ofFloat(mImgCat, "translationY", currentTranslationY, 300, currentTranslationY);
        translationAnim.setDuration(100 * 10);
        translationAnim.setRepeatMode(ValueAnimator.REVERSE);
        translationAnim.setRepeatCount(5);
        translationAnim.start();
    }

    /**
     * 缩放动画
     *
     * @param view
     */
    public void scaleAnim(View view) {
        ObjectAnimator scaleAnimX = ObjectAnimator.ofFloat(mImgCat, "scaleX", 1f, 3f, 1f);
        ObjectAnimator scaleAnimY = ObjectAnimator.ofFloat(mImgCat, "scaleY", 1f, 3f, 1f);
        scaleAnimX.setRepeatMode(ValueAnimator.REVERSE);
        scaleAnimY.setRepeatMode(ValueAnimator.REVERSE);
        scaleAnimX.setRepeatCount(6);
        scaleAnimY.setRepeatCount(6);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleAnimX).with(scaleAnimY);
        animatorSet.setDuration(500 * 2);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.start();
    }

    /**
     * 自定义对象属性实现动画效果
     *
     * @param view
     */
    public void customAnim(View view) {
        Intent intent = new Intent();
        intent.setClass(this, ColorActivity.class);
        startActivity(intent);
    }

    /**
     * 装饰模式包装原始对象实现动画
     *
     * @param view
     */
    public void decorativePattern(View view) {
        //(方式1)获取控件的宽高
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mImgCat.measure(w, h);
        int width = mImgCat.getMeasuredWidth();
        int height = mImgCat.getMeasuredHeight();
        Log.i(TAG, "1 width: " + width + " ,height: " + height);

        // 创建包装类,并传入动画作用的对象
        ViewWrapper wrapper = new ViewWrapper(mImgCat);

        ObjectAnimator animator = ObjectAnimator.ofInt(wrapper, "width", width, width * 2, width / 2);
        animator.setDuration(300 * 1);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(6);
        animator.start();
    }

    /**
     * 组合动画
     * AnimatorSet.play(Animator anim)   ：播放当前动画
     * AnimatorSet.after(long delay)   ：将现有动画延迟x毫秒后执行
     * AnimatorSet.with(Animator anim)   ：将现有动画和传入的动画同时执行
     * AnimatorSet.after(Animator anim)   ：将现有动画插入到传入的动画之后执行
     * AnimatorSet.before(Animator anim) ：  将现有动画插入到传入的动画之前执行
     *
     * @param view
     */
    public void animationSet(View view) {
        //(方式1)获取控件的宽高
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mImgCat.measure(w, h);
        int width = mImgCat.getMeasuredWidth();
        int height = mImgCat.getMeasuredHeight();
        Log.i(TAG, "1 width: " + width + " ,height: " + height);

        // 步骤1：设置需要组合的动画效果
        ObjectAnimator translation = ObjectAnimator.ofFloat(mImgCat, "translationX", width, width * 2, width);
        ObjectAnimator rotate = ObjectAnimator.ofFloat(mImgCat, "rotation", 0f, 360f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(mImgCat, "alpha", 1f, 0f, 1f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mImgCat, "scaleX", 1f, 2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mImgCat, "scaleY", 1f, 2f, 1f);

        // 步骤2：创建组合动画的对象
        AnimatorSet animatorSet = new AnimatorSet();

        // 步骤3：根据需求组合动画
//        animatorSet.play(translation).with(rotate).with(alpha).with(scaleX).with(scaleY);
        animatorSet.play(rotate).with(alpha).with(scaleX).with(scaleY);
//        animatorSet.playSequentially(translation, rotate, alpha, scaleX, scaleY);
        animatorSet.setDuration(2000);
        animatorSet.start();
    }

    /**
     * XML组合属性动画
     *
     * @param view
     */
    public void animationSet2(View view) {
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.anim_set_properity);
        animator.setTarget(mImgCat);
        animator.start();
    }


    /**
     * 组合动画3
     *
     * @param view
     */
    public void animationSet3(View view) {
        ObjectAnimator moveIn = ObjectAnimator.ofFloat(mImgCat, "translationX", -500f, 0f);
        ObjectAnimator rotate = ObjectAnimator.ofFloat(mImgCat, "rotation", 0f, 360f);
        ObjectAnimator fadeInOut = ObjectAnimator.ofFloat(mImgCat, "alpha", 1f, 0f, 1f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(rotate).with(fadeInOut).after(moveIn);
        animSet.setDuration(5000);
        animSet.start();
    }

    /**
     * ViewPropertyAnimation动画
     *
     * @param view
     */
    public void viewPropertyAnimation(View view) {
        mImgCat.animate().x(500).y(500).alpha(0).setDuration(2 * 1000);
    }


}
