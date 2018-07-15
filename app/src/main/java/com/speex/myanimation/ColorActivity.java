package com.speex.myanimation;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.speex.myanimation.Interpolator.ColorEvaluator;
import com.speex.myanimation.view.ColorView;

public class ColorActivity extends AppCompatActivity {

    private ColorView mColorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);

        mColorView = (ColorView) findViewById(R.id.color_view);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 设置自定义View对象、背景颜色属性值 & 颜色估值器
        // 本质逻辑：
        // 步骤1：根据颜色估值器不断 改变 值
        // 步骤2：调用set（）设置背景颜色的属性值（实际上是通过画笔进行颜色设置）
        // 步骤3：调用invalidate()刷新视图，即调用onDraw（）重新绘制，从而实现动画效果
        ObjectAnimator animator = ObjectAnimator.ofObject(mColorView, "color", new ColorEvaluator(), "#0000ff", "#ff0000");
        animator.setDuration(2000);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(6);
        animator.start();
    }
}
