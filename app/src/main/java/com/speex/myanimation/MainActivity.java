package com.speex.myanimation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void frameAnim(View view) {
        //帧动画
        Intent intent = new Intent();
        intent.setClass(this, FrameAnimationActivity.class);
        startActivity(intent);
    }

    public void tweenAnim(View view) {
        //补间动画
        Intent intent = new Intent();
        intent.setClass(this, TweenAnimationActivity.class);
        startActivity(intent);
    }

    /**
     * 自定义插值器
     *
     * @param view
     */
    public void evaluator(View view) {
        Intent intent = new Intent();
        intent.setClass(this, TypeEvaluatorActivity.class);
        startActivity(intent);
    }

    /**
     * 属性动画
     *
     * @param view
     */
    public void propertyAnim(View view) {
        Intent intent = new Intent();
        intent.setClass(this, PropertyAnimtionActivity.class);
        startActivity(intent);
    }
}
