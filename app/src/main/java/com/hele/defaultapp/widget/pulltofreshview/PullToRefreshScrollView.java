package com.hele.defaultapp.widget.pulltofreshview;

import com.hele.defaultapp.R;
import com.hele.defaultapp.listener.IOnScrollListener;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * 下拉刷新和上拉加载的ScrollView
 * @author aeal
 *
 */
public class PullToRefreshScrollView extends PullToRefreshBase<ScrollView> {

	public PullToRefreshScrollView(Context context) {
		super(context);
	}

	public PullToRefreshScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PullToRefreshScrollView(Context context, Mode mode) {
		super(context, mode);
	}

	public PullToRefreshScrollView(Context context, Mode mode, AnimationStyle style) {
		super(context, mode, style);
	}

	@Override
	public final Orientation getPullToRefreshScrollDirection() {
		return Orientation.VERTICAL;
	}

	@Override
	protected ScrollView createRefreshableView(Context context, AttributeSet attrs) {
		ScrollView scrollView;
		if (VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD) {
			scrollView = new InternalScrollViewSDK9(context, attrs);
		} else {
			scrollView = new ScrollView(context, attrs);
		}

		scrollView.setId(R.id.scrollview);
		return scrollView;
	}

	@Override
	protected boolean isReadyForPullStart() {
		return mRefreshableView.getScrollY() == 0;
	}

	@Override
	protected boolean isReadyForPullEnd() {
		View scrollViewChild = mRefreshableView.getChildAt(0);
		if (null != scrollViewChild) {
			return mRefreshableView.getScrollY() >= (scrollViewChild.getHeight() - getHeight());
		}
		return false;
	}

	@TargetApi(9)
	public final class InternalScrollViewSDK9 extends ScrollView {

		public InternalScrollViewSDK9(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		@Override
		protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX,
				int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

			final boolean returnValue = super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
					scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);

			// Does all of the hard work...
			OverscrollHelper.overScrollBy(PullToRefreshScrollView.this, deltaX, scrollX, deltaY, scrollY,
					getScrollRange(), isTouchEvent);

			return returnValue;
		}

		/**
		 * Taken from the AOSP ScrollView source
		 */
		private int getScrollRange() {
			int scrollRange = 0;
			if (getChildCount() > 0) {
				View child = getChildAt(0);
				scrollRange = Math.max(0, child.getHeight() - (getHeight() - getPaddingBottom() - getPaddingTop()));
			}
			return scrollRange;
		}

		private IOnScrollListener onScrollListener;  

		/** 
		 * 设置滚动接口 
		 * @param onScrollListener 
		 */
		public void setOnScrollListener(IOnScrollListener onScrollListener){
			this.onScrollListener = onScrollListener;
		}
		/** 
		 * 用于用户手指离开MyScrollView的时候获取MyScrollView滚动的Y距离，然后回调给onScroll方法中 
		 */  
		private Handler handler = new Handler() {  

			public void handleMessage(android.os.Message msg) {  
				int scrollY = InternalScrollViewSDK9.this.getScrollY();  

				//此时的距离和记录下的距离不相等，在隔5毫秒给handler发送消息  
				if(lastScrollY != scrollY){  
					lastScrollY = scrollY;  
					handler.sendMessageDelayed(handler.obtainMessage(), 5);    
				}  
				if(onScrollListener != null){  
					onScrollListener.onScroll(scrollY);  
				}  

			};  

		}; 

		/** 
		 * 主要是用在用户手指离开MyScrollView，MyScrollView还在继续滑动，我们用来保存Y的距离，然后做比较 
		 */  
		private int lastScrollY;
		/** 
		 * 重写onTouchEvent， 当用户的手在MyScrollView上面的时候， 
		 * 直接将MyScrollView滑动的Y方向距离回调给onScroll方法中，当用户抬起手的时候， 
		 * MyScrollView可能还在滑动，所以当用户抬起手我们隔5毫秒给handler发送消息，在handler处理 
		 * MyScrollView滑动的距离 
		 */ 
		@Override
		public boolean onTouchEvent(MotionEvent ev) {
			if(onScrollListener != null){  
				onScrollListener.onScroll(lastScrollY = this.getScrollY());  
			}  
			switch(ev.getAction()){  
			case MotionEvent.ACTION_UP:  
				handler.sendMessageDelayed(handler.obtainMessage(), 20);    
				break;  
			}  
			return super.onTouchEvent(ev);
		}


	}
}
