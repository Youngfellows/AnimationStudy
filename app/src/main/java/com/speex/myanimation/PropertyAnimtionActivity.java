package com.speex.myanimation;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

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


}
