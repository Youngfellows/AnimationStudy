package com.speex.myanimation;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class FrameAnimationActivity extends AppCompatActivity {
    private String TAG = this.getClass().getSimpleName();
    private ImageView mImgFrame;

    private AnimationDrawable mAnimationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_animation);
        mImgFrame = (ImageView) findViewById(R.id.img_frame);
    }

    /**
     * 开始帧动画
     *
     * @param view
     */
    public void startFrameAnim(View view) {
        //1.设置动画
        mImgFrame.setImageResource(R.drawable.frame_anim);
        //2.获取动画对象
        AnimationDrawable animationDrawable = (AnimationDrawable) mImgFrame.getDrawable();
        //3.启动动画
        animationDrawable.start();
    }

    /**
     * 停止帧动画
     *
     * @param view
     */
    public void stopFrameAnim(View view) {
        //1.设置动画
        mImgFrame.setImageResource(R.drawable.frame_anim);
        //2.获取动画对象
        AnimationDrawable animationDrawable = (AnimationDrawable) mImgFrame.getDrawable();
        //3.启动动画
        animationDrawable.stop();
    }

    /**
     * JAVA开始帧动画
     *
     * @param view
     */
    public void startFrameAnim2(View view) {
        //只播放一次
        mAnimationDrawable.setOneShot(true);
        //设置动画对象
        //获取资源对象
        mImgFrame.setImageDrawable(mAnimationDrawable);
        //特别注意：在动画start()之前要先stop()，不然在第一次动画之后会停在最后一帧，这样动画就只会触发一次
        mAnimationDrawable.stop();
        //启动动画
        mAnimationDrawable.start();
    }

    /**
     * JAVA代码停止帧动画
     *
     * @param view
     */
    public void stopFrameAnim2(View view) {
        mAnimationDrawable.setOneShot(true);
        mImgFrame.setImageDrawable(mAnimationDrawable);
        mAnimationDrawable.stop();
    }


    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        super.onResume();

        // 直接从drawable文件夹获取动画资源（图片）
        mAnimationDrawable = new AnimationDrawable();
        for (int i = 1; i <= 28; i++) {
//            int id = getResources().getIdentifier("icon" + i, "drawable", getPackageName());
            int id = getResources().getIdentifier("icon" + i, "mipmap", getPackageName());
            Log.i(TAG, i + " id: " + id);
            Drawable drawable = getResources().getDrawable(id);
//            Drawable drawable = ContextCompat.getDrawable(this, id);
            mAnimationDrawable.addFrame(drawable, 200);
        }

    }
}
