package my.util.app.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import my.util.app.R;


public class StatusTimeLineView extends View {

    private Paint mLinePaint;
    private Paint mCirclePaint;
    private Paint mVerticalLinePaint;
    private Paint mTextPaint;
    private int mScreenWidth;
    private int mScreenHeight;
   // ArrayList<Integer> colorList = new ArrayList();
   // ArrayList<JobHistory> jobsList = new ArrayList<>();

/*    public StatusTimeLineView(Context context, ArrayList<JobHistory> jobsList) {
        super(context);
        this.jobsList = jobsList;
        init();
    }*/

    public StatusTimeLineView(Context context) {
        super(context);
        init();
    }

    public StatusTimeLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StatusTimeLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mLinePaint = new Paint();
        mLinePaint.setColor(ContextCompat.getColor(getContext(), R.color.pink));
        mLinePaint.setStrokeWidth(15);

        mCirclePaint = new Paint();
        // mCirclePaint.setColor(ContextCompat.getColor(getContext(), R.color.circle_color));

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.CYAN);
        mTextPaint.setTextSize(50);

        mVerticalLinePaint = new Paint();
        mVerticalLinePaint.setStrokeWidth(10);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mScreenWidth = MeasureSpec.getSize(widthMeasureSpec);
       mScreenHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (mScreenHeight <= 1217) {
            mScreenHeight /= 2;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        drawView(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    int mCircleRadius = 20;
    // int mVerticalLineHeight = 150;
    int mVerticalLineHeight;

    private void drawView(Canvas canvas) {
        float mStartX = 0;
        float mEndX = 0;
        int j = 0;
        for (int i = 0; i < 3; i++) {
            mEndX = mEndX + 400;

            //mVerticalLineHeight = Integer.parseInt(jobsList.get(i).getProjectDuration());
           /* if (i < colorList.size()) {
                mVerticalLinePaint.setColor(colorList.get(i));
                mCirclePaint.setColor(colorList.get(i));
            } else {
                if (j == colorList.size()) {
                    j = 0;
                }
                mVerticalLinePaint.setColor(colorList.get(j));
                mCirclePaint.setColor(colorList.get(j));
                ++j;
            }*/
            mVerticalLinePaint.setColor(Color.RED);
            mCirclePaint.setColor(Color.RED);


            mVerticalLineHeight = 10;
            mLinePaint.setColor(Color.RED);
         //   canvas.drawLine(mEndX - mCircleRadius / 2, mScreenHeight / 2 - mCircleRadius / 2, mEndX - mCircleRadius / 2, mScreenHeight / 2 - mVerticalLineHeight, mVerticalLinePaint);
            canvas.drawLine(mStartX, mScreenHeight / 2, mEndX + mEndX, mScreenHeight / 2, mLinePaint);
            // canvas.drawLine(mEndX - mCircleRadius / 2, mScreenHeight / 2 - mCircleRadius / 2, mEndX - mCircleRadius / 2, mScreenHeight / 2 + mVerticalLineHeight, mVerticalLinePaint);
            canvas.drawCircle(mEndX - mCircleRadius / 2, mScreenHeight / 2, mCircleRadius, mCirclePaint);
            mStartX = mEndX + mCircleRadius / 2;
        }
        setLayoutParams(new LinearLayout.LayoutParams((int) mEndX + 400, LinearLayout.LayoutParams.WRAP_CONTENT));
    }


}
