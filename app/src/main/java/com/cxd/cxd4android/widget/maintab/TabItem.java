package com.cxd.cxd4android.widget.maintab;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Administrator on 15-11-23.
 */
public class TabItem extends View {

    /*字体大小*/

    private int mTextSize ;

    /*字体选中的颜色*/
    private int mTextColorSelect ;

    /*字体未选择的时候的颜色*/
    private int mTextColorNormal;

    /*绘制未选中时字体的画笔*/
    private Paint mTextPaintNormal;

    /*绘制已选中时字体的画笔*/
    private Paint mTextPaintSelect;

    /*每个 item 的宽和高，包括字体和图标一起*/
    private int mViewHeight, mViewWidth;

    /*字体的内容*/
    private String mTextValue ;

    /*已选中时的图标*/
    private Bitmap mIconNormal;

    /*未选中时的图标*/
    private Bitmap mIconSelect;

    /*用于记录字体大小*/
    private Rect mBoundText;

    /*已选中是图标的画笔*/
    private Paint mIconPaintSelect;

    /*为选中时图标的画笔*/
    private Paint mIconPaintNormal;


    /*新消息时的红点图标*/
    private Bitmap mBedgeSelect;

    /*新消息时的红点图标*/
    private Bitmap mBedgeNormal;

    /*新消息时的红点图标的画笔*/
    private Paint mBedgePaintSelect;

    /*新消息时的红点图标的画笔*/
    private Paint mBedgePaintNormal;

    public TabItem(Context context) {
        this(context, null);
    }

    public TabItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initText();
    }

    /*初始化一些东西*/
    private void initView() {
        mBoundText = new Rect();
    }

    /*初始化画笔，并设置出是内容*/
    private void initText() {
        mTextPaintNormal = new Paint();
        mTextPaintNormal.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mTextSize, getResources().getDisplayMetrics()));
        mTextPaintNormal.setColor(mTextColorNormal);
        mTextPaintNormal.setAntiAlias(true);
        mTextPaintNormal.setAlpha(255);

        mTextPaintSelect = new Paint();
        mTextPaintSelect.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mTextSize, getResources().getDisplayMetrics()));
        mTextPaintSelect.setColor(mTextColorSelect);
        mTextPaintSelect.setAntiAlias(true);
        mTextPaintSelect.setAlpha(0);

        mIconPaintSelect = new Paint(Paint.ANTI_ALIAS_FLAG) ;
        mIconPaintSelect.setAlpha(0);

        mIconPaintNormal = new Paint(Paint.ANTI_ALIAS_FLAG) ;
        mIconPaintNormal.setAlpha(255);

        mBedgePaintSelect = new Paint(Paint.ANTI_ALIAS_FLAG) ;
        mBedgePaintSelect.setAlpha(255);

        mBedgePaintNormal = new Paint(Paint.ANTI_ALIAS_FLAG) ;
        mBedgePaintNormal.setAlpha(255);
    }

    /*测量字体的大小*/
    private void measureText() {
        mTextPaintNormal.getTextBounds(mTextValue, 0, mTextValue.length(), mBoundText);
    }


    /*测量字体和图标的大小，并设置自身的宽和高*/
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0, height = 0;

        measureText();
        int contentWidth = Math.max(mBoundText.width(), mIconNormal.getWidth());
        int desiredWidth = getPaddingLeft() + getPaddingRight() + contentWidth;
        switch (widthMode) {
            case MeasureSpec.AT_MOST:
                width = Math.min(widthSize, desiredWidth);
                break;
            case MeasureSpec.EXACTLY:
                width = widthSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                width = desiredWidth;
                break;
        }
        int contentHeight = mBoundText.height() + mIconNormal.getHeight()+12;/** Modify By Gele **/
        int desiredHeight = getPaddingTop() + getPaddingBottom() + contentHeight;
        switch (heightMode) {
            case MeasureSpec.AT_MOST:
                height = Math.min(heightSize, desiredHeight);
                break;
            case MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                height = contentHeight;
                break;
        }
        setMeasuredDimension(width, height);
        mViewWidth = getMeasuredWidth() ;
        mViewHeight = getMeasuredHeight() ;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBitmap(canvas) ;
        drawText(canvas) ;
    }
    int left;
    int top;
    /*话图标，先画为选中的图标，在画已选中的图标*/
    private void drawBitmap(Canvas canvas) {
        left = (mViewWidth - mIconNormal.getWidth())/2 ;
        top = (mViewHeight - mIconNormal.getHeight() - mBoundText.height()) /2 ;

        canvas.drawBitmap(mIconNormal, left, top, mIconPaintNormal);
        canvas.drawBitmap(mIconSelect, left, top, mIconPaintSelect);

        canvas.drawBitmap(mBedgeSelect, left+mIconNormal.getWidth(), top+top/2, mBedgePaintSelect);
//        canvas.drawCircle(left,top,10,mBedgePaintSelect);
//        canvas.drawBitmap(mBedgeNormal, left, top, mBedgePaintNormal);
    }

    /*画字体*/
    private void drawText(Canvas canvas) {
        float x = (mViewWidth - mBoundText.width())/2.0f ;
        float y = (mViewHeight + mIconNormal.getHeight() + mBoundText.height()) /2.0F ;
        canvas.drawText(mTextValue, x, y + 5, mTextPaintNormal);
        canvas.drawText(mTextValue, x, y + 5, mTextPaintSelect);

    }

    public void setTextSize(int textSize) {
        this.mTextSize = textSize;
        mTextPaintNormal.setTextSize(textSize);
        mTextPaintSelect.setTextSize(textSize);
    }

    public void setTextColorSelect(int mTextColorSelect) {
        this.mTextColorSelect = mTextColorSelect;
        mTextPaintSelect.setColor(mTextColorSelect);
        mTextPaintSelect.setAlpha(0);
    }

    public void setTextColorNormal(int mTextColorNormal) {
        this.mTextColorNormal = mTextColorNormal;
        mTextPaintNormal.setColor(mTextColorNormal);
        mTextPaintNormal.setAlpha(0xff);
    }

    public void setTextValue(String TextValue) {
        this.mTextValue = TextValue;
    }
    public void setIconText(int[] iconSelId,String TextValue) {
        this.mIconSelect = BitmapFactory.decodeResource(getResources(), iconSelId[0]);
        this.mIconNormal = BitmapFactory.decodeResource(getResources(), iconSelId[1]);
        this.mTextValue = TextValue;
    }
    public void setBadge(int[] iconSelId) {
        this.mBedgeSelect = BitmapFactory.decodeResource(getResources(),iconSelId[0]);
        this.mBedgeNormal = BitmapFactory.decodeResource(getResources(),iconSelId[1]);
    }


    /*通过 alpha 来设置 每个画笔的透明度，从而实现现实的效果*/
    public void setTabAlpha(float alpha){
        int paintAlpha = (int)(alpha*255) ;
        mIconPaintSelect.setAlpha(paintAlpha);
        mIconPaintNormal.setAlpha(255-paintAlpha);
        mTextPaintSelect.setAlpha(paintAlpha);
        mTextPaintNormal.setAlpha(255-paintAlpha);
        invalidate();
    }

    /**
     * 小红点提示
     */
    public void setBedgeAlpha(float alpha){
        int paintAlpha = (int)(alpha*255) ;
        mBedgePaintSelect.setAlpha(paintAlpha);
        mBedgePaintNormal.setAlpha(255-paintAlpha);
        invalidate();
    }
}
