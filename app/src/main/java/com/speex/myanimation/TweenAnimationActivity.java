package com.speex.myanimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.speex.myanimation.Interpolator.DecelerateAccelerateInterpolator;

public class TweenAnimationActivity extends AppCompatActivity {
    private String TAG = this.getClass().getSimpleName();
    private ImageView mImgTweenAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tween_animation);

        //1.创建 需要设置动画的 视图View
        mImgTweenAnim = (ImageView) findViewById(R.id.img_tween_anim);
    }

    /**
     * 平移动画 -->> 补间动画 -->> 平移动画
     */
    public void translate(View view) {
        //2.创建 动画对象 并传入设置的动画效果xml文件
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate_anim_tween);
        //3.播放动画
        mImgTweenAnim.startAnimation(animation);
    }

    /**
     * 平移动画 -->> 补间动画 -->> 平移动画
     * 补间动画使用小结
     *
     * @param view
     */
    public void translateJava(View view) {
        //平移动画
        //Animation.RELATIVE_TO_PARENT：相对于父控件身的坐标
        //Animation.RELATIVE_TO_SELF：相对于自身的坐标
//        TranslateAnimation translateAnimation = new TranslateAnimation(
//                Animation.RELATIVE_TO_SELF, 0,
//                Animation.RELATIVE_TO_SELF, 50,
//                Animation.RELATIVE_TO_SELF, 0,
//                Animation.RELATIVE_TO_SELF, 50);

        TranslateAnimation translateAnimation = new TranslateAnimation(0, 300, 0, 300);
        translateAnimation.setDuration(1000);
        translateAnimation.setRepeatMode(Animation.REVERSE);//重最后开始执行
        translateAnimation.setRepeatCount(4);//重复5次
        translateAnimation.setFillAfter(true);//停在最后位置
        mImgTweenAnim.startAnimation(translateAnimation);//执行动画
    }

    /**
     * 缩放动画
     *
     * @param view
     */
    public void scale(View view) {
        //加载动画
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_anim_tween);
        //启动动画
        mImgTweenAnim.startAnimation(animation);
    }


    /**
     * 缩放动画
     *
     * @param view
     */
    public void scaleJava(View view) {
        ScaleAnimation animation = new ScaleAnimation(
                1, 0.5f,
                1, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5F,
                Animation.RELATIVE_TO_SELF, 0.5F);
        Interpolator interpolator = new OvershootInterpolator();
        animation.setInterpolator(interpolator);
        animation.setDuration(500);
        animation.setFillAfter(true);
        animation.setRepeatCount(5);
        animation.setRepeatMode(Animation.REVERSE);
        mImgTweenAnim.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.i(TAG, "onAnimationStart: ");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.i(TAG, "onAnimationEnd: ");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.i(TAG, "onAnimationRepeat: ");
            }
        });
    }

    /**
     * 旋转动画
     *
     * @param view
     */
    public void rotate(View view) {
        //加载动画
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate_anim_tween);
        Interpolator interpolator = new DecelerateAccelerateInterpolator();
        animation.setInterpolator(interpolator);
        //启动动画
        mImgTweenAnim.startAnimation(animation);
    }

    /**
     * 旋转动画
     *
     * @param view
     */
    public void rotateJava(View view) {
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(500);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setRepeatMode(Animation.RESTART);
        rotateAnimation.setRepeatCount(10);
        mImgTweenAnim.startAnimation(rotateAnimation);
    }

    /**
     * 透明度动画
     *
     * @param view
     */
    public void alpha(View view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha_anim_tween);
        mImgTweenAnim.startAnimation(animation);
    }

    /**
     * 透明度动画
     *
     * @param view
     */
    public void alphaJava(View view) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0);
        alphaAnimation.setDuration(500 * 2);
        alphaAnimation.setRepeatMode(Animation.RESTART);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setRepeatCount(10);
        mImgTweenAnim.startAnimation(alphaAnimation);
    }


    /**
     * 组合动画
     *
     * @param view
     */
    public void animationSet(View view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_set_tween);
        mImgTweenAnim.startAnimation(animation);
    }

    /**
     * 组合动画
     *
     * @param view
     */
    public void animationSetJava(View view) {
        //组合动画设置
        AnimationSet animationSet = new AnimationSet(true);

        //设置组合动画的属性
        // 特别说明以下情况
        // 因为在下面的旋转动画设置了无限循环(RepeatCount = INFINITE)
        // 所以动画不会结束，而是无限循环
        // 所以组合动画的下面两行设置是无效的
        //        setAnimation.setRepeatMode(Animation.RESTART);
        //        setAnimation.setRepeatCount(1);// 设置了循环一次,但无效

        //步骤3:逐个创建子动画(方式同单个动画创建方式,此处不作过多描述)
        // 子动画1:旋转动画
        Animation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1000);
        rotate.setRepeatMode(Animation.RESTART);
        rotate.setRepeatCount(Animation.INFINITE);

        // 子动画2:平移动画
        Animation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_PARENT, -0.5f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0.5f,
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 0);
        translate.setDuration(1000);
        translate.setStartOffset(1000);

        // 子动画3:透明度动画
        Animation alpha = new AlphaAnimation(1, 0);
        alpha.setDuration(3000);
        alpha.setStartOffset(2000);

        // 子动画4:缩放动画
        Animation scale1 = new ScaleAnimation(
                1, 0.5f,
                1, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scale1.setDuration(1000);
        scale1.setStartOffset(1000);

        // 步骤4:将创建的子动画添加到组合动画里
        animationSet.addAnimation(alpha);
        animationSet.addAnimation(rotate);
        animationSet.addAnimation(translate);
        animationSet.addAnimation(scale1);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mImgTweenAnim.startAnimation(animationSet);
    }

}
