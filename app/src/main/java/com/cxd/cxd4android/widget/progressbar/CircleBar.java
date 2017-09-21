package com.cxd.cxd4android.widget.progressbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.cxd.cxd4android.R;

/**
 * 这是继承view的一个重新绘图的圆圈的一个类 Author: liyachao email:296777513@qq.com Date: 2015-1-5
 * Time: 下午2:39
 */
public class CircleBar extends View {

	private RectF mColorWheelRectangle = new RectF();// 圆圈的矩形范围
	private Paint mDefaultWheelPaint;// 绘制底部灰色圆圈的画笔
	private Paint mColorWheelPaint;// 绘制蓝色扇形的画笔
	private Paint textPaint;// 中间文字的画笔
	private Paint textPaint1;// 上下文字的画笔
	private float mColorWheelRadius;// 圆圈普通状态下的半径
	private float circleStrokeWidth;// 圆圈的线条粗细
	private float pressExtraStrokeWidth;// 按下状态下增加的圆圈线条增加的粗细
	private int mText;// 中间文字内容
	private int mCount;// 为了达到数字增加效果而添加的变量，他和mText其实代表一个意思
	private float mProgressAni;// 为了达到蓝色扇形增加效果而添加的变量，他和mProgress其实代表一个意思
	private float mProgress;// 扇形弧度
	private int mTextSize;// 中间文字大小
	private int mTextSize1;// 上下文字大小
	private int mDistance;// 上下文字的距离
	BarAnimation anim;// 动画类
	private int mType;// 根据传入的数值判断应该显示的页面

	public CircleBar(Context context) {
		super(context);
		init();
	}

	public CircleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CircleBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {

		circleStrokeWidth = dip2px(getContext(),3 );/*20*/// 圆圈的线条粗细
		pressExtraStrokeWidth = dip2px(getContext(), 2);// 按下状态下增加的圆圈线条增加的粗细
		mTextSize = dip2px(getContext(),12 );/*80*/// 中间文字大小
		mTextSize1 = dip2px(getContext(), 10);/*30*/// 上下文字大小
		mDistance = dip2px(getContext(), 12);/*70*///文字间的距离

		// 绘制蓝色扇形的画笔
		mColorWheelPaint = new Paint();
		mColorWheelPaint.setAntiAlias(true);// 抗锯齿
		mColorWheelPaint.setColor(Color.parseColor("#F64242"));/*0xFF29a6f6*/// 设置颜色
		mColorWheelPaint.setStyle(Style.STROKE);// 设置空心
		mColorWheelPaint.setStrokeWidth(circleStrokeWidth);// 设置圆圈粗细

		// 绘制底部灰色圆圈的画笔
		mDefaultWheelPaint = new Paint();
		mDefaultWheelPaint.setAntiAlias(true);
		mDefaultWheelPaint.setColor(Color.parseColor("#F5F5F5"));/*#d9d6c3*/
		mDefaultWheelPaint.setStyle(Style.STROKE);
		mDefaultWheelPaint.setStrokeWidth(circleStrokeWidth);

		// 中间文字的画笔
		textPaint = new Paint(Paint.LINEAR_TEXT_FLAG);
		textPaint.setAntiAlias(true);
		textPaint.setColor(Color.parseColor("#F64242"));/*#6DCAEC*/
		textPaint.setStyle(Style.FILL_AND_STROKE);
		textPaint.setTextAlign(Align.LEFT);
		textPaint.setTextSize(mTextSize);

		// 上下文字的画笔
		textPaint1 = new Paint(Paint.LINEAR_TEXT_FLAG);
		textPaint1.setAntiAlias(true);
		textPaint1.setColor(Color.parseColor("#F64242"));/*#a1a3a6*/
		textPaint1.setStyle(Style.FILL_AND_STROKE);
		textPaint1.setTextAlign(Align.LEFT);
		textPaint1.setTextSize(mTextSize1);

		// 中间文字内容
		mText = 0;
		// 扇形弧度
		mProgress = 0;

		// 动画类
		anim = new BarAnimation();
		anim.setDuration(1000);

	}

	public static String middleText = "";// 中间的文字
	public static String upText = "";// 上面文字
	public static String downText = "";// 底部文字

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		int halfHeight = getHeight() / 2;
		int halfWidth = getWidth() / 2;
		int radius = halfHeight < halfWidth ? halfHeight : halfWidth;
		// 圆圈的矩形范围 绘制底部灰色圆圈的画笔
		canvas.drawCircle(halfWidth, halfHeight, radius - 20f,
				mDefaultWheelPaint);

		// canvas.drawArc(mColorWheelRectangle, -90, 360, false,
		// mDefaultWheelPaint);
		// 为了达到蓝色扇形增加效果而添加的变量，他和mSweepAngle其实代表一个意思

		// mColorWheelRectangle是绘制蓝色扇形的画笔
		mColorWheelRectangle.top = halfHeight - radius + 20f;
		mColorWheelRectangle.bottom = halfHeight + radius - 20f;
		mColorWheelRectangle.left = halfWidth - radius + 20f;
		mColorWheelRectangle.right = halfWidth + radius - 20f;
		// 根据mProgressAni（角度）画扇形
		canvas.drawArc(mColorWheelRectangle, -90, mProgressAni, false,
				mColorWheelPaint);
		Rect bounds = new Rect();


		if (this.mType == 1) {// 第一个页面
			upText = "";/*步数*//** Modify By Gele **/
			downText = String.valueOf(mCount)+"%";/*目标:10000*/
			middleText = String.valueOf("马上赚钱");/*mCount*/
			textPaint.setColor(Color.parseColor("#F64242"));
		} else if (this.mType == 2) {// 第二个页面
			upText = "卡路里";
			downText = "目标:10000";
			middleText = String.valueOf(mCount);
		} else if (this.mType == 3) {// 第三个页面
			upText = "根据自己的需要填写";
			downText = "3";
			middleText = "天气";
		}else if(this.mType == 4){//特殊处理,已完成(已售罄)
			upText = "";/*步数*//** Modify By Gele **/
			downText = "";/*目标:10000*/
			middleText = String.valueOf("已售罄");/*mCount*/
			textPaint.setColor(Color.parseColor("#9c9c9c"));
		}else if(this.mType == 5){//特殊处理,复核中
			upText = "";/*步数*//** Modify By Gele **/
			downText = "";/*目标:10000*/
			middleText = String.valueOf("复核中");/*mCount*/
			textPaint.setColor(Color.parseColor("#9c9c9c"));
		}else if(this.mType == 6){//特殊处理,还款中
			upText = "";/*步数*//** Modify By Gele **/
			downText = "";/*目标:10000*/
			middleText = String.valueOf("还款中");/*mCount*/
			textPaint.setColor(Color.parseColor("#9c9c9c"));
		}


		// 中间文字的画笔
		try {
			textPaint.getTextBounds(middleText, 0, middleText.length(), bounds);
		}catch (Exception e){
			e.printStackTrace();
		}finally {

		}

		// drawText各个属性的意思(文字,x坐标,y坐标,画笔)
		canvas.drawText(middleText, (mColorWheelRectangle.centerX())
				- (textPaint.measureText(middleText) / 2),
				mColorWheelRectangle.centerY() + bounds.height() / 2, textPaint);
		textPaint1.getTextBounds(upText, 0, upText.length(), bounds);
		canvas.drawText(
				upText,
				(mColorWheelRectangle.centerX())
						- (textPaint1.measureText(upText) / 2),
				mColorWheelRectangle.centerY() + bounds.height() / 2
						- mDistance, textPaint1);
		textPaint1.getTextBounds(downText, 0, downText.length(), bounds);
		canvas.drawText(downText, (mColorWheelRectangle.centerX())
				- (textPaint1.measureText(downText) / 2),
				mColorWheelRectangle.centerY() + bounds.height() / 2
						+ mDistance, textPaint1);
	}

	// 测量父布局的大小
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int height = getDefaultSize(getSuggestedMinimumHeight(),
				heightMeasureSpec);
		int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
		int min = Math.min(width, height);
		setMeasuredDimension(min, min);
		mColorWheelRadius = min - circleStrokeWidth - pressExtraStrokeWidth;

		// set方法的参数意思：left,top,right,bottom
		mColorWheelRectangle.set(circleStrokeWidth + pressExtraStrokeWidth,
				circleStrokeWidth + pressExtraStrokeWidth, mColorWheelRadius,
				mColorWheelRadius);
	}

	// 对外的一个接口，用来开启动画
	public void startCustomAnimation() {
		this.startAnimation(anim);
	}

	// 中间的数值
	public void setText(int text) {
		mText = text;
		this.postInvalidate();// 可以用子线程更新视图的方法调用。
	}

	// 设置圆圈的进度和圆圈所显示的第几个页面
	public void setProgress(float progress, int mType) {
		mProgress = progress;
		this.mType = mType;
		this.postInvalidate();// 可以用子线程更新视图的方法调用。
	}

	/**
	 * 继承animation的一个动画类
	 * 
	 * @author liyachao
	 *
	 */
	public class BarAnimation extends Animation {
		/**
		 * Initializes expand collapse animation, has two types, collapse (1)
		 * and expand (0).
		 *
		 * @param view
		 *            The view to animate
		 * @param type
		 *            The type of animation: 0 will expand from gone and 0 size
		 *            to visible and layout size defined in xml. 1 will collapse
		 *            view and set to gone
		 */
		@Override
		protected void applyTransformation(float interpolatedTime,
				Transformation t) {
			super.applyTransformation(interpolatedTime, t);
			if (interpolatedTime < 1.0f) {
				mProgressAni = interpolatedTime * mProgress;
				mCount = (int) (interpolatedTime * mText);
			} else {
				mProgressAni = mProgress;
				mCount = mText;
			}
			postInvalidate();

		}
	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

}
