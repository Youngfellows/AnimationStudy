package com.speex.myanimation.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import com.speex.myanimation.Interpolator.ColorEvaluator;
import com.speex.myanimation.Interpolator.PointEvaluator;
import com.speex.myanimation.bean.Point;

public class TranslateCircleView extends View {
    // 设置需要用到的变量
    public static final float RADIUS = 70f;// 圆的半径 = 70
    private Point currentPoint;// 当前点坐标
    private Paint mPaint;// 绘图画笔
    private int mWidth;
    private int mHeight;
    // 设置背景颜色属性
    private String color;

    // 设置背景颜色的get() & set()方法
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        mPaint.setColor(Color.parseColor(color));
        // 将画笔的颜色设置成方法参数传入的颜色
        invalidate();
        // 调用了invalidate()方法,即画笔颜色每次改变都会刷新视图，然后调用onDraw()方法重新绘制圆
        // 而因为每次调用onDraw()方法时画笔的颜色都会改变,所以圆的颜色也会改变
    }

    public TranslateCircleView(Context context) {
        super(context);
        init(context);
    }

    // 构造方法(初始化画笔)
    public TranslateCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TranslateCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        // 初始化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);

        //获取屏幕宽高
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mWidth = manager.getDefaultDisplay().getWidth();
        mHeight = manager.getDefaultDisplay().getHeight();
    }

    // 复写onDraw()从而实现绘制逻辑
    // 绘制逻辑:先在初始点画圆,通过监听当前坐标值(currentPoint)的变化,每次变化都调用onDraw()重新绘制圆,从而实现圆的平移动画效果
    @Override
    protected void onDraw(Canvas canvas) {
        // 如果当前点坐标为空(即第一次)
        if (currentPoint == null) {
            currentPoint = new Point(RADIUS, RADIUS);
            // 创建一个点对象(坐标是(70,70))
            drawCircle(canvas);
            startAnimation();
        } else {
            // 如果坐标值不为0,则画圆
            // 所以坐标值每改变一次,就会调用onDraw()一次,就会画一次圆,从而实现动画效果
            // 在该点画一个圆:圆心 = (30,30),半径 = 30
            drawCircle(canvas);
        }
    }

    private void startAnimation() {
        // (重点关注)将属性动画作用到View中
        // 步骤1:创建初始动画时的对象点  & 结束动画时的对象点
        Point startPoint = new Point(RADIUS, RADIUS);// 初始点为圆心(70,70)
//            Point endPoint = new Point(700, 1000);// 结束点为(700,1000)
        Point endPoint = new Point(mWidth - RADIUS, mHeight - RADIUS);// 结束点为(700,1000)

        // 步骤2:创建动画对象 & 设置初始值 和 结束值
        ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
        // 参数说明
        // 参数1：TypeEvaluator 类型参数 - 使用自定义的PointEvaluator(实现了TypeEvaluator接口)
        // 参数2：初始动画的对象点
        // 参数3：结束动画的对象点

        // 步骤3：设置动画参数
//        anim.setDuration(5000);
        // 设置动画时长

        // 步骤3：通过 值 的更新监听器，将改变的对象手动赋值给当前对象
        // 此处是将 改变后的坐标值对象 赋给 当前的坐标值对象
        // 设置 值的更新监听器
        // 即每当坐标值（Point对象）更新一次,该方法就会被调用一次
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint = (Point) animation.getAnimatedValue();
                // 将每次变化后的坐标值（估值器PointEvaluator中evaluate（）返回的Piont对象值）到当前坐标值对象（currentPoint）
                // 从而更新当前坐标值（currentPoint）

                // 步骤4：每次赋值后就重新绘制，从而实现动画效果
                invalidate();
                // 调用invalidate()后,就会刷新View,即才能看到重新绘制的界面,即onDraw()会被重新调用一次
                // 所以坐标值每改变一次,就会调用onDraw()一次
            }
        });

        // 启动动画
        //anim.start();

        // TODO: 2018/7/17 设置颜色过度动画
        ObjectAnimator colorAnimator = ObjectAnimator.ofObject(this, "color", new ColorEvaluator(),
                "#0000ff", "#ff0000");
        ObjectAnimator rotateAnim = ObjectAnimator.ofFloat(this, "rotation", 0f, 360f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(anim).with(colorAnimator).with(rotateAnim);
        animatorSet.setDuration(5 * 1000);
        animatorSet.start();
    }

    /**
     * 画圆
     *
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
        // 在该点画一个圆:圆心 = (70,70),半径 = 70
        float x = currentPoint.getX();
        float y = currentPoint.getY();
        canvas.drawCircle(x, y, RADIUS, mPaint);
    }
}
