
package com.cxd.cxd4android.widget.seekbar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;

import com.cxd.cxd4android.R;

/**
 * @绫诲悕: NumberSeekBar
 * @鎻忚堪: TODO(甯︽湁鏁板瓧鐨勬按骞虫嫋鍔ㄦ潯)
 * @浣滆��: wang.fb
 * @鏃ユ湡: 2014-8-11 涓嬪崍2:01:14
 * @淇敼浜�:
 * @淇敼鏃堕棿: 2014-8-11 涓嬪崍2:01:14
 * @淇敼鍐呭:
 * @鐗堟湰: V1.0
 */
public class NumberSeekBar extends SeekBar {
    
    private int oldPaddingTop;
    
    private int oldPaddingLeft;
    
    private int oldPaddingRight;
    
    private int oldPaddingBottom;
    
    private boolean isMysetPadding = true;
    
    private String mText;
    
    private float mTextWidth;
    
    private float mImgWidth;
    
    private float mImgHei;
    
    private Paint mPaint;
    
    private Resources res;
    
    private Bitmap bm;
    
    private int textsize = 13;
    
    private int textpaddingleft;
    
    private int textpaddingtop;
    
    private int imagepaddingleft;
    
    private int imagepaddingtop;
    
    public NumberSeekBar(Context context) {
        super(context);
        init();
    }
    
    public NumberSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    public NumberSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    
    // 灞忚斀婊戝姩
    // @Override
    // public boolean onTouchEvent(MotionEvent event) {
    // return false;
    // }
    /**
     * (闈� Javadoc)
     * 
     * @鏂规硶鍚�: onTouchEvent
     * @鎻忚堪: 涓嶅睆钄藉睆钄芥粦鍔�
     * @鏃ユ湡: 2014-8-11 涓嬪崍2:03:15
     * @param event
     * @return
     * @see android.widget.AbsSeekBar#onTouchEvent(MotionEvent)
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
    
    // 淇敼setpadding 浣垮叾鍦ㄥ閮ㄨ皟鐢ㄧ殑鏃跺�欐棤鏁�
    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        if (isMysetPadding) {
            super.setPadding(left, top, right, bottom);
        }
    }
    
    // 鍒濆鍖�
    private void init() {
        res = getResources();
        initBitmap();
        initDraw();
        setPadding();
    }
    
    private void initDraw() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTypeface(Typeface.DEFAULT);
        mPaint.setTextSize(textsize);
//        mPaint.setColor(0xFF3E64B5);
    }
    
    private void initBitmap() {
        bm = BitmapFactory.decodeResource(res, R.mipmap.ic_bout_jindu);
        if (bm != null) {
            mImgWidth = bm.getWidth();
            mImgHei = bm.getHeight();
        } else {
            mImgWidth = 0;
            mImgHei = 0;
        }
    }
    
    protected synchronized void onDraw(Canvas canvas) {
        try {
            super.onDraw(canvas);
            mText = (getProgress() * 100 / getMax()) + "%";
            mTextWidth = mPaint.measureText(mText);
            Rect bounds = this.getProgressDrawable().getBounds();
            float xImg =
                bounds.width() * getProgress() / getMax() + imagepaddingleft
                    + oldPaddingLeft;
            float yImg = imagepaddingtop + oldPaddingTop;
            float xText =
                bounds.width() * getProgress() / getMax() + mImgWidth / 2
                    - mTextWidth / 2 + textpaddingleft + oldPaddingLeft;
            float yText =
                yImg + textpaddingtop + mImgHei / 2 + getTextHei() / 4;
            canvas.drawBitmap(bm, xImg, yImg, mPaint);
            canvas.drawText(mText, xText, yText, mPaint);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // 鍒濆鍖杙adding 浣垮叾宸﹀彸涓� 鐣欎笅浣嶇疆鐢ㄤ簬灞曠ず杩涘害鍥剧墖
    private void setPadding() {
        int top = getBitmapHeigh() + oldPaddingTop;
        int left = getBitmapWidth() / 2 + oldPaddingLeft;
        int right = getBitmapWidth() / 2 + oldPaddingRight;
        int bottom = oldPaddingBottom;
        isMysetPadding = true;
        setPadding(left, top, right, bottom);
        isMysetPadding = false;
    }
    
    /**
     * 璁剧疆灞曠ず杩涘害鑳屾櫙鍥剧墖
     * 
     * @param resid
     */
    public void setBitmap(int resid) {
        bm = BitmapFactory.decodeResource(res, resid);
        if (bm != null) {
            mImgWidth = bm.getWidth();
            mImgHei = bm.getHeight();
        } else {
            mImgWidth = 0;
            mImgHei = 0;
        }
        setPadding();
    }
    
    /**
     * 鏇夸唬setpadding
     * 
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void setMyPadding(int left, int top, int right, int bottom) {
        oldPaddingTop = top;
        oldPaddingLeft = left;
        oldPaddingRight = right;
        oldPaddingBottom = bottom;
        isMysetPadding = true;
        setPadding(left + getBitmapWidth() / 2, top + getBitmapHeigh(), right
            + getBitmapWidth() / 2, bottom);
        isMysetPadding = false;
    }
    
    /**
     * 璁剧疆杩涘害瀛椾綋澶у皬
     * 
     * @param textsize
     */
    public void setTextSize(int textsize) {
        this.textsize = textsize;
        mPaint.setTextSize(textsize);
    }
    
    /**
     * 璁剧疆杩涘害瀛椾綋棰滆壊
     * 
     * @param color
     */
    public void setTextColor(int color) {
        mPaint.setColor(color);
    }
    
    /**
     * 璋冩暣杩涘害瀛椾綋鐨勪綅缃� 鍒濆浣嶇疆涓哄浘鐗囩殑姝ｄ腑澶�
     * 
     * @param top
     * @param left
     */
    public void setTextPadding(int top, int left) {
        this.textpaddingleft = left;
        this.textpaddingtop = top;
    }
    
    /**
     * 璋冩暣杩涘浘鑳屾櫙鍥剧殑浣嶇疆 鍒濆浣嶇疆涓鸿繘搴︽潯姝ｄ笂鏂广�佸亸宸︿竴鍗�
     * 
     * @param top
     * @param left
     */
    public void setImagePadding(int top, int left) {
        this.imagepaddingleft = left;
        this.imagepaddingtop = top;
    }
    
    private int getBitmapWidth() {
        return (int)Math.ceil(mImgWidth);
    }
    
    private int getBitmapHeigh() {
        return (int)Math.ceil(mImgHei);
    }
    
    private float getTextHei() {
        FontMetrics fm = mPaint.getFontMetrics();
        return (float)Math.ceil(fm.descent - fm.top) + 2;
    }
    
    public int getTextpaddingleft() {
        return textpaddingleft;
    }
    
    public int getTextpaddingtop() {
        return textpaddingtop;
    }
    
    public int getImagepaddingleft() {
        return imagepaddingleft;
    }
    
    public int getImagepaddingtop() {
        return imagepaddingtop;
    }
    
    public int getTextsize() {
        return textsize;
    }
    
}
