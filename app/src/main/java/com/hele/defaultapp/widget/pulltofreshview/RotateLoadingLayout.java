package com.hele.defaultapp.widget.pulltofreshview;

import com.hele.defaultapp.R;
import com.hele.defaultapp.widget.pulltofreshview.PullToRefreshBase.Mode;
import com.hele.defaultapp.widget.pulltofreshview.PullToRefreshBase.Orientation;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;


public class RotateLoadingLayout extends LoadingLayout {

	static final int ROTATION_ANIMATION_DURATION = 1200;

	//	private final Animation mRotateAnimation;
	//	private final Matrix mHeaderImageMatrix;

	//	private float mRotationPivotX, mRotationPivotY;


	public RotateLoadingLayout(Context context, Mode mode, Orientation scrollDirection, TypedArray attrs) {
		super(context, mode, scrollDirection, attrs);


		//		mHeaderImage.setScaleType(ScaleType.MATRIX);
		//		mHeaderImageMatrix = new Matrix();
		//		mHeaderImage.setImageMatrix(mHeaderImageMatrix);

		//		mRotateAnimation = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
		//				0.5f);
		//		mRotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
		//		mRotateAnimation.setDuration(ROTATION_ANIMATION_DURATION);
		//		mRotateAnimation.setRepeatCount(Animation.INFINITE);
		//		mRotateAnimation.setRepeatMode(Animation.RESTART);
	}

	public void onLoadingDrawableSet(Drawable imageDrawable) {
		if (null != imageDrawable) {
			//			mRotationPivotX = Math.round(imageDrawable.getIntrinsicWidth() / 2f);
			//			mRotationPivotY = Math.round(imageDrawable.getIntrinsicHeight() / 2f);
		}
	}

	protected void onPullImpl(float scaleOfLayout) {
//		float angle;
//		if (mRotateDrawableWhilePulling) {
//			angle = scaleOfLayout * 90f;
//		} else {
//			angle = Math.max(0f, Math.min(180f, scaleOfLayout * 360f - 180f));
//		}
		//		mHeaderImageMatrix.setRotate(angle, mRotationPivotX, mRotationPivotY);
		//		mHeaderImage.setImageMatrix(mHeaderImageMatrix);
	}

	@Override
	protected void refreshingImpl() {
		//		mHeaderImage.startAnimation(mRotateAnimation);
		//		mHeaderImage.setImageDrawable(animDrawable);
		//		animDrawable.start();
	}

	@Override
	protected void resetImpl() {
		mHeaderImage.clearAnimation();
		resetImageRotation();
	}

	private void resetImageRotation() {
		//		if (null != mHeaderImageMatrix) {
		//			mHeaderImageMatrix.reset();
		//			mHeaderImage.setImageMatrix(mHeaderImageMatrix);
		//		}
	}

	@Override
	protected void pullToRefreshImpl() {
		// NO-OP
	}

	@Override
	protected void releaseToRefreshImpl() {
		// NO-OP
	}

	@Override
	protected int getDefaultDrawableResId() {
		return R.drawable.small_loading_anim;
	}

}
