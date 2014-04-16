package per.learn.multiselectlistview.lib;

import per.learn.multiselectlistview.R;
import per.learn.multiselectlistview.util.LogUtil;
import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;

public class MultiSelectListView extends ListView {
    private static final String TAG = "MultiSelectListView";

    public static final int DEFAULT_RANGE = 100;

    private int mCheckBoxID = -1;
    private boolean mEnableMultiSelect = true;
    private int mSelectRange = DEFAULT_RANGE;
    private int mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

    private int mLastMotionX, mLastMotionY;
    private int mCurX, mCurY;
    private int mRangeLeftSide = -1, mRangeRightSide = -1;
    private int mLastChangedPos = -1;

    
    public static final int STATE_NORMAL = 0;
    public static final int STATE_SLIDING = 1;
    private static int mCurrentState = STATE_NORMAL;

    private View mItemView;
    private CheckBox mCb;

    private SparseBooleanArray mSelectedMap = new SparseBooleanArray();

    public MultiSelectListView(Context context) {
        super(context);
        init(null);
    }

    public MultiSelectListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MultiSelectListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if(attrs != null) {
            
        }

        mCheckBoxID = R.id.select_cb;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(!mEnableMultiSelect || mCheckBoxID == -1)
            return super.onTouchEvent(ev);

        switch(ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mLastMotionX = (int)ev.getRawX();
                mLastMotionY = (int)ev.getRawY();

                
            }break;

            case MotionEvent.ACTION_MOVE: {
                mCurX = (int)ev.getX();
                mCurY = (int)ev.getY();

                if(mRangeLeftSide == -1) {
                    mRangeRightSide = getRight();
                    mRangeLeftSide = mRangeRightSide - mSelectRange;
                }

                if(mCurX < mRangeLeftSide || mCurX > mRangeRightSide)
                    break;

                LogUtil.Log("onTouchEvent(), action move,"
                        + ", mCurX = " + mCurX
                        + ", mCurY = " + mCurY
                        + ", mRangeLeftSide = " + mRangeLeftSide
                        + ", mRangeRightSide = " + mRangeRightSide);

                //here, we know user is sliding into the range which layout the CheckBoxs
                mCurrentState = STATE_SLIDING;

                int curPos = pointToPosition(mCurX, mCurY);
                if(curPos == AdapterView.INVALID_POSITION) {
                    break;
                } else {
                    //we will handle current MotionEvent, so we don't want
                    //this event will been handled by others
                    ev.setAction(MotionEvent.ACTION_CANCEL);
                }
                int firstVisiblePos = getFirstVisiblePosition() - getHeaderViewsCount();
                int factPos = curPos - firstVisiblePos;
                if(curPos != mLastChangedPos) {
                    mItemView = getChildAt(factPos);
                    mCb = (CheckBox) mItemView.findViewById(mCheckBoxID);
                    mCb.setChecked(!mCb.isChecked());

                    mLastChangedPos = curPos;
                }
            }break;

            case MotionEvent.ACTION_UP: {
                mCurrentState = STATE_NORMAL;
            }break;

            case MotionEvent.ACTION_CANCEL: {
                mCurrentState = STATE_NORMAL;
            }break;
        }

        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }
}
