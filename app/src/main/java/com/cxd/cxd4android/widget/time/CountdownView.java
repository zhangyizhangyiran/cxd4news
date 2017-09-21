package com.cxd.cxd4android.widget.time;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.cxd.cxd4android.R;


/**
 * Countdown View
 * Created by iWgang on 15/9/16.
 * https://github.com/iwgang/CountdownView
 */
public class CountdownView extends View {
    private CustomCountDownTimer mCustomCountDownTimer;
    private OnCountdownEndListener mOnCountdownEndListener;
    private OnCountdownIntervalListener mOnCountdownIntervalListener;

    private boolean isHideTimeBackground;
    private long mPreviousIntervalCallbackTime;
    private long mInterval;
    private long mRemainTime;
    private BaseCountdown mBaseCountdown;

    public CountdownView(Context context) {
        this(context, null);
    }

    public CountdownView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountdownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CountdownView);
        isHideTimeBackground = ta.getBoolean(R.styleable.CountdownView_isHideTimeBackground, true);

        mBaseCountdown = isHideTimeBackground ? new BaseCountdown() : new BackgroundCountdown();
        mBaseCountdown.initStyleAttr(context, ta);
        ta.recycle();

        mBaseCountdown.initialize();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int contentAllWidth = mBaseCountdown.getAllContentWidth();
        int contentAllHeight = mBaseCountdown.getAllContentHeight();
        int viewWidth = measureSize(1, contentAllWidth, widthMeasureSpec);
        int viewHeight = measureSize(2, contentAllHeight, heightMeasureSpec);
        setMeasuredDimension(viewWidth, viewHeight);

        mBaseCountdown.onMeasure(this, viewWidth, viewHeight, contentAllWidth, contentAllHeight);
    }

    /**
     * measure view Size
     *
     * @param specType    1 width 2 height
     * @param contentSize all content view size
     * @param measureSpec spec
     * @return measureSize
     */
    private int measureSize(int specType, int contentSize, int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = Math.max(contentSize, specSize);
        } else {
            result = contentSize;

            if (specType == 1) {
                // width
                result += (getPaddingLeft() + getPaddingRight());
            } else {
                // height
                result += (getPaddingTop() + getPaddingBottom());
            }
        }

        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mBaseCountdown.onDraw(canvas);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    private void reLayout() {
        mBaseCountdown.reLayout();
        requestLayout();
    }

    /**
     * start countdown
     *
     * @param millisecond millisecond
     */
    public void start(long millisecond) {
        if (millisecond <= 0) return;

        mPreviousIntervalCallbackTime = 0;

        if (null != mCustomCountDownTimer) {
            mCustomCountDownTimer.stop();
            mCustomCountDownTimer = null;
        }

        long countDownInterval;
        if (mBaseCountdown.isShowMillisecond) {
            countDownInterval = 10;
            updateShow(millisecond);
        } else {
            countDownInterval = 1000;
            updateShow(millisecond);
        }

        mCustomCountDownTimer = new CustomCountDownTimer(millisecond, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateShow(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                // countdown end
                allShowZero();
                // callback
                if (null != mOnCountdownEndListener) {
                    mOnCountdownEndListener.onEnd(CountdownView.this);
                }
            }
        };
        mCustomCountDownTimer.start();
    }

    /**
     * stop countdown
     */
    public void stop() {
        if (null != mCustomCountDownTimer) mCustomCountDownTimer.stop();
    }

    /**
     * pause countdown
     */
    public void pause() {
        if (null != mCustomCountDownTimer) mCustomCountDownTimer.pause();
    }

    /**
     * pause countdown
     */
    public void restart() {
        if (null != mCustomCountDownTimer) mCustomCountDownTimer.restart();
    }

    /**
     * custom time show
     *
     * @param isShowDay         isShowDay
     * @param isShowHour        isShowHour
     * @param isShowMinute      isShowMinute
     * @param isShowSecond      isShowSecond
     * @param isShowMillisecond isShowMillisecond
     *                          <p>
     *                          use:{@link #dynamicShow(DynamicConfig)}
     */
    @Deprecated
    public void customTimeShow(boolean isShowDay, boolean isShowHour, boolean isShowMinute, boolean isShowSecond, boolean isShowMillisecond) {
        mBaseCountdown.mHasSetIsShowDay = true;
        mBaseCountdown.mHasSetIsShowHour = true;

        boolean isModCountdownInterval = mBaseCountdown.refTimeShow(isShowDay, isShowHour, isShowMinute, isShowSecond, isShowMillisecond);

        // judgement modify countdown interval
        if (isModCountdownInterval) {
            start(mRemainTime);
        }
    }

    /**
     * set all time zero
     */
    public void allShowZero() {
        mBaseCountdown.setTimes(0, 0, 0, 0, 0);
        invalidate();
    }

    /**
     * set countdown end callback listener
     *
     * @param onCountdownEndListener OnCountdownEndListener
     */
    public void setOnCountdownEndListener(OnCountdownEndListener onCountdownEndListener) {
        mOnCountdownEndListener = onCountdownEndListener;
    }

    /**
     * set interval callback listener
     *
     * @param interval                    interval time
     * @param onCountdownIntervalListener OnCountdownIntervalListener
     */
    public void setOnCountdownIntervalListener(long interval, OnCountdownIntervalListener onCountdownIntervalListener) {
        mInterval = interval;
        mOnCountdownIntervalListener = onCountdownIntervalListener;
    }

    /**
     * get day
     *
     * @return current day
     */
    public int getDay() {
        return mBaseCountdown.mDay;
    }

    /**
     * get hour
     *
     * @return current hour
     */
    public int getHour() {
        return mBaseCountdown.mHour;
    }

    /**
     * get minute
     *
     * @return current minute
     */
    public int getMinute() {
        return mBaseCountdown.mMinute;
    }

    /**
     * get second
     *
     * @return current second
     */
    public int getSecond() {
        return mBaseCountdown.mSecond;
    }

    /**
     * get remain time
     *
     * @return remain time ( millisecond )
     */
    public long getRemainTime() {
        return mRemainTime;
    }

    public void updateShow(long ms) {
        this.mRemainTime = ms;

        reSetTime(ms);

        // interval callback
        if (mInterval > 0 && null != mOnCountdownIntervalListener) {
            if (mPreviousIntervalCallbackTime == 0) {
                mPreviousIntervalCallbackTime = ms;
            } else if (ms + mInterval <= mPreviousIntervalCallbackTime) {
                mPreviousIntervalCallbackTime = ms;
                mOnCountdownIntervalListener.onInterval(this, mRemainTime);
            }
        }

        if (mBaseCountdown.handlerAutoShowTime() || mBaseCountdown.handlerDayLargeNinetyNine()) {
            reLayout();
        } else {
            invalidate();
        }
    }

    private void reSetTime(long ms) {
        int day = 0;
        int hour;

        if (!mBaseCountdown.isConvertDaysToHours) {
            day = (int) (ms / (1000 * 60 * 60 * 24));
            hour = (int) ((ms % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        } else {
            hour = (int) (ms / (1000 * 60 * 60));
        }

        int minute = (int) ((ms % (1000 * 60 * 60)) / (1000 * 60));
        int second = (int) ((ms % (1000 * 60)) / 1000);
        int millisecond = (int) (ms % 1000);
        DynamicConfig.Builder builder = new DynamicConfig.Builder();
        if (day <= 0) {
            builder.setShowDay(false);
        }
        if (hour <= 0) {
            builder.setShowHour(false);
        }
        if (minute <= 0) {
            builder.setShowMinute(false);
        }
        DynamicConfig dynamicConfig = new DynamicConfig(builder);
        this.dynamicShow(dynamicConfig);
        mBaseCountdown.setTimes(day, hour, minute, second, millisecond);


    }

    public interface OnCountdownEndListener {
        void onEnd(CountdownView cv);
    }

    public interface OnCountdownIntervalListener {
        void onInterval(CountdownView cv, long remainTime);
    }

    /**
     * Dynamic show
     *
     * @param dynamicConfig DynamicConfig
     */
    public void dynamicShow(DynamicConfig dynamicConfig) {
        if (null == dynamicConfig) return;

        boolean isReLayout = false;
        boolean isInvalidate = false;

        Float timeTextSize = dynamicConfig.getTimeTextSize();
        if (null != timeTextSize) {
            mBaseCountdown.setTimeTextSize(timeTextSize);
            isReLayout = true;
        }

        Float suffixTextSize = dynamicConfig.getSuffixTextSize();
        if (null != suffixTextSize) {
            mBaseCountdown.setSuffixTextSize(suffixTextSize);
            isReLayout = true;
        }

        Integer timeTextColor = dynamicConfig.getTimeTextColor();
        if (null != timeTextColor) {
            mBaseCountdown.setTimeTextColor(timeTextColor);
            isInvalidate = true;
        }

        Integer suffixTextColor = dynamicConfig.getSuffixTextColor();
        if (null != suffixTextColor) {
            mBaseCountdown.setSuffixTextColor(suffixTextColor);
            isInvalidate = true;
        }

        Boolean isTimeTextBold = dynamicConfig.isTimeTextBold();
        if (null != isTimeTextBold) {
            mBaseCountdown.setTimeTextBold(isTimeTextBold);
            isReLayout = true;
        }

        Boolean isSuffixTimeTextBold = dynamicConfig.isSuffixTimeTextBold();
        if (null != isSuffixTimeTextBold) {
            mBaseCountdown.setSuffixTextBold(isSuffixTimeTextBold);
            isReLayout = true;
        }

        // suffix text (all)
        String suffix = dynamicConfig.getSuffix();
        if (!TextUtils.isEmpty(suffix)) {
            mBaseCountdown.setSuffix(suffix);
            isReLayout = true;
        }

        // suffix text
        String suffixDay = dynamicConfig.getSuffixDay();
        String suffixHour = dynamicConfig.getSuffixHour();
        String suffixMinute = dynamicConfig.getSuffixMinute();
        String suffixSecond = dynamicConfig.getSuffixSecond();
        String suffixMillisecond = dynamicConfig.getSuffixMillisecond();
        if (mBaseCountdown.setSuffix(suffixDay, suffixHour, suffixMinute, suffixSecond, suffixMillisecond)) {
            isReLayout = true;
        }

        // suffix margin (all)
        Float suffixLRMargin = dynamicConfig.getSuffixLRMargin();
        if (null != suffixLRMargin) {
            mBaseCountdown.setSuffixLRMargin(suffixLRMargin);
            isReLayout = true;
        }

        // suffix margin
        Float suffixDayLeftMargin = dynamicConfig.getSuffixDayLeftMargin();
        Float suffixDayRightMargin = dynamicConfig.getSuffixDayRightMargin();
        Float suffixHourLeftMargin = dynamicConfig.getSuffixHourLeftMargin();
        Float suffixHourRightMargin = dynamicConfig.getSuffixHourRightMargin();
        Float suffixMinuteLeftMargin = dynamicConfig.getSuffixMinuteLeftMargin();
        Float suffixMinuteRightMargin = dynamicConfig.getSuffixMinuteRightMargin();
        Float suffixSecondLeftMargin = dynamicConfig.getSuffixSecondLeftMargin();
        Float suffixSecondRightMargin = dynamicConfig.getSuffixSecondRightMargin();
        Float suffixMillisecondRightMargin = dynamicConfig.getSuffixMillisecondLeftMargin();
        if (mBaseCountdown.setSuffixMargin(suffixDayLeftMargin, suffixDayRightMargin, suffixHourLeftMargin, suffixHourRightMargin,
                suffixMinuteLeftMargin, suffixMinuteRightMargin, suffixSecondLeftMargin, suffixSecondRightMargin, suffixMillisecondRightMargin)) {
            isReLayout = true;
        }

        Integer suffixGravity = dynamicConfig.getSuffixGravity();
        if (null != suffixGravity) {
            mBaseCountdown.setSuffixGravity(suffixGravity);
            isReLayout = true;
        }

        // judgement time show
        Boolean tempIsShowDay = dynamicConfig.isShowDay();
        Boolean tempIsShowHour = dynamicConfig.isShowHour();
        Boolean tempIsShowMinute = dynamicConfig.isShowMinute();
        Boolean tempIsShowSecond = dynamicConfig.isShowSecond();
        Boolean tempIsShowMillisecond = dynamicConfig.isShowMillisecond();
        if (null != tempIsShowDay || null != tempIsShowHour || null != tempIsShowMinute || null != tempIsShowSecond || null != tempIsShowMillisecond) {
            boolean isShowDay = mBaseCountdown.isShowDay;
            if (null != tempIsShowDay) {
                isShowDay = tempIsShowDay;
                mBaseCountdown.mHasSetIsShowDay = true;
            } else {
                mBaseCountdown.mHasSetIsShowDay = false;
            }
            boolean isShowHour = mBaseCountdown.isShowHour;
            if (null != tempIsShowHour) {
                isShowHour = tempIsShowHour;
                mBaseCountdown.mHasSetIsShowHour = true;
            } else {
                mBaseCountdown.mHasSetIsShowHour = false;
            }
            boolean isShowMinute = null != tempIsShowMinute ? tempIsShowMinute : mBaseCountdown.isShowMinute;
            boolean isShowSecond = null != tempIsShowSecond ? tempIsShowSecond : mBaseCountdown.isShowSecond;
            boolean isShowMillisecond = null != tempIsShowMillisecond ? tempIsShowMillisecond : mBaseCountdown.isShowMillisecond;

            boolean isModCountdownInterval = mBaseCountdown.refTimeShow(isShowDay, isShowHour, isShowMinute, isShowSecond, isShowMillisecond);

            // judgement modify countdown interval
            if (isModCountdownInterval) {
                start(mRemainTime);
            }

            isReLayout = true;
        }

        DynamicConfig.BackgroundInfo backgroundInfo = dynamicConfig.getBackgroundInfo();
        if (!isHideTimeBackground && null != backgroundInfo) {
            BackgroundCountdown backgroundCountdown = (BackgroundCountdown) mBaseCountdown;

            Float size = backgroundInfo.getSize();
            if (null != size) {
                backgroundCountdown.setTimeBgSize(size);
                isReLayout = true;
            }

            Integer color = backgroundInfo.getColor();
            if (null != color) {
                backgroundCountdown.setTimeBgColor(color);
                isInvalidate = true;
            }

            Float radius = backgroundInfo.getRadius();
            if (null != radius) {
                backgroundCountdown.setTimeBgRadius(radius);
                isInvalidate = true;
            }

            Boolean isShowTimeBgDivisionLine = backgroundInfo.isShowTimeBgDivisionLine();
            if (null != isShowTimeBgDivisionLine) {
                backgroundCountdown.setIsShowTimeBgDivisionLine(isShowTimeBgDivisionLine);

                if (isShowTimeBgDivisionLine) {
                    Integer divisionLineColor = backgroundInfo.getDivisionLineColor();
                    if (null != divisionLineColor) {
                        backgroundCountdown.setTimeBgDivisionLineColor(divisionLineColor);
                    }

                    Float divisionLineSize = backgroundInfo.getDivisionLineSize();
                    if (null != divisionLineSize) {
                        backgroundCountdown.setTimeBgDivisionLineSize(divisionLineSize);
                    }
                }
                isInvalidate = true;
            }

            Boolean isShowTimeBgBorder = backgroundInfo.isShowTimeBgBorder();
            if (null != isShowTimeBgBorder) {
                backgroundCountdown.setIsShowTimeBgBorder(isShowTimeBgBorder);

                if (isShowTimeBgBorder) {
                    Integer borderColor = backgroundInfo.getBorderColor();
                    if (null != borderColor) {
                        backgroundCountdown.setTimeBgBorderColor(borderColor);
                    }

                    Float borderSize = backgroundInfo.getBorderSize();
                    if (null != borderSize) {
                        backgroundCountdown.setTimeBgBorderSize(borderSize);
                    }

                    Float borderRadius = backgroundInfo.getBorderRadius();
                    if (null != borderRadius) {
                        backgroundCountdown.setTimeBgBorderRadius(borderRadius);
                    }
                }
                isReLayout = true;
            }
        }

        Boolean tempIsConvertDaysToHours = dynamicConfig.isConvertDaysToHours();
        if (null != tempIsConvertDaysToHours && mBaseCountdown.setConvertDaysToHours(tempIsConvertDaysToHours)) {
            reSetTime(getRemainTime());
            isReLayout = true;
        }

        if (isReLayout) {
            reLayout();
        } else if (isInvalidate) {
            invalidate();
        }
    }

}
