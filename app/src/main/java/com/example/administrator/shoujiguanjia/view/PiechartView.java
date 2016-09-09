package com.example.administrator.shoujiguanjia.view;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import com.example.administrator.shoujiguanjia.R;
import java.util.Timer;
import java.util.TimerTask;
public  class PiechartView extends View {
    private Paint paint;
    private RectF oval;
    private float proportionPhone = 0;
    private float proportionSD = 0;
    private float piecharAnglePhone = 0;
    private float piecharAngleSD = 0;
    private int phoneColor = 0;
    private int sdColor = 0;
    private int baseColor = 0;
    public PiechartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        phoneColor = context.getResources().getColor(R.color.piechar_phone);
        sdColor =    context.getResources().getColor(R.color.piechar_sdcard);
        baseColor =  context.getResources().getColor(R.color.piechar_base);
    }
    public  void setPiechartProportionWithAnim(float f1, float f2) {
        proportionPhone = f1;
        proportionSD = f2;
        final float phoneTargetAngle = 360 * proportionPhone;
        final float sdcardTargetAngle = 360 * proportionSD;
        final Timer timer = new Timer();
        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                piecharAnglePhone += 4;
                piecharAngleSD += 4;
                postInvalidate();
                if (piecharAnglePhone >= phoneTargetAngle) {
                    piecharAnglePhone = phoneTargetAngle;
                }
                if (piecharAngleSD >= sdcardTargetAngle) {
                    piecharAngleSD = sdcardTargetAngle;
                }
                if (piecharAnglePhone == phoneTargetAngle && piecharAngleSD == sdcardTargetAngle) {
                    timer.cancel();
                }
            }
        };
        timer.schedule(timerTask, 26, 26);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        int viewHeight = MeasureSpec.getSize(heightMeasureSpec);
        oval = new RectF(0, 0, viewWidth, viewHeight);
        setMeasuredDimension(viewWidth, viewHeight);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        paint.setAntiAlias(true);
        paint.setColor(baseColor);
        canvas.drawArc(oval, -90, 360, true, paint);
        paint.setColor(phoneColor);
        canvas.drawArc(oval, -90, piecharAnglePhone, true, paint);
        paint.setColor(sdColor);
        canvas.drawArc(oval,  -90 + piecharAnglePhone, piecharAngleSD, true,paint);
    }
}