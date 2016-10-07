package com.hele.defaultapp.util;

import com.hele.defaultapp.R;
import com.hele.defaultapp.application.DefaultApplicaition;
import com.hele.defaultapp.widget.dialog.LoadingDialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * 视图工具类
 * 
 * @author by 孙炜
 *
 */
public class ViewUtils {

	private static ProgressDialog loadingPD;

	private static LoadingDialog loadingDialog;

	private static AnimationDrawable animDrawable;

	private static AnimationDrawable smallAnimDrawable;

	private static AnimationDrawable loginAnimDrawable;

	public static AnimationDrawable getAnimDrawable() {
		// if (animDrawable == null) {
		animDrawable = null;
		animDrawable = (AnimationDrawable) DefaultApplicaition.applicationContext.getResources()
				.getDrawable(R.drawable.small_loading_anim);
		// }
		return animDrawable;
	}

	public static AnimationDrawable getSmallAnimDrawable() {
		if (smallAnimDrawable == null) {
			smallAnimDrawable = (AnimationDrawable) DefaultApplicaition.applicationContext.getResources()
					.getDrawable(R.drawable.small_loading_anim);
		}
		return smallAnimDrawable;
	}

	public static AnimationDrawable getLoginAnimDrawable() {
		if (loginAnimDrawable == null) {
			loginAnimDrawable = (AnimationDrawable) DefaultApplicaition.applicationContext.getResources()
					.getDrawable(R.drawable.small_loading_anim);
		}
		return loginAnimDrawable;
	}

	/**
	 * 显示loading对话框
	 * 
	 * @param context
	 */
	public static void showLoadingPD(Context context) {
		if (loadingPD == null || context != loadingPD.getContext()) {
			loadingPD = new ProgressDialog(context);
			loadingPD.setCancelable(false);
			loadingPD.setCanceledOnTouchOutside(false);
		}
		loadingPD.show();
	}

	/**
	 * 取消loading对话框
	 */
	public static void dismissLoadingPD() {
		if (loadingPD != null) {
			loadingPD.dismiss();
		}
	}

	public static LoadingDialog getLoadingDialog() {
		return loadingDialog;
	}

	public static void showLoadingDialog(Context context, boolean isCancel) {
		if (loadingDialog != null && loadingDialog.getContext() == context &&
				!loadingDialog.isShowing()) {
			loadingDialog.setCancelable(isCancel);
			loadingDialog.show();
			return;
		}

		loadingDialog = new LoadingDialog(context, R.style.transparentdialog, 0);
		loadingDialog.setContentView(R.layout.dialog_loading_main);
		// 去掉Dialog默认背景
		loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
		loadingDialog.setCanceledOnTouchOutside(false);
		loadingDialog.setCancelable(true);
		loadingDialog.show();



	}
	//	public static void showLoadingDialog(Context context, boolean isCancel) {
	//		if (LoadingAct.INSTANCE == null  && CommonUtils.isNetWorkConnected(context)) {
	//			context.startActivity(new Intent(context, LoadingAct.class));
	//			((Activity) context).overridePendingTransition(anim.anim_loading_enter, anim.anim_loading_exit);
	//		}
	//
	//	}

	 public static void dismissLoadingDialog(Context context) {
	 if (loadingDialog != null) {
	 loadingDialog.dismiss();
	 }
	 }

	/**
	 * 心跳动的动画效果---ImageView
	 */
	public static void playHeartbeatAnimation(final ImageView imageView) {
		AnimationSet animationSet = new AnimationSet(true);
		animationSet.addAnimation(new ScaleAnimation(1.0f, 1.8f, 1.0f, 1.8f, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f));
		animationSet.addAnimation(new AlphaAnimation(1.0f, 0.4f));

		animationSet.setDuration(200);
		animationSet.setInterpolator(new AccelerateInterpolator());
		animationSet.setFillAfter(true);

		animationSet.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				AnimationSet animationSet = new AnimationSet(true);
				animationSet.addAnimation(new ScaleAnimation(1.8f, 1.0f, 1.8f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f,
						Animation.RELATIVE_TO_SELF, 0.5f));
				animationSet.addAnimation(new AlphaAnimation(0.4f, 1.0f));

				animationSet.setDuration(600);
				animationSet.setInterpolator(new DecelerateInterpolator());
				animationSet.setFillAfter(false);

				// 实现心跳的View
				imageView.startAnimation(animationSet);
			}
		});

		// 实现心跳的View
		imageView.startAnimation(animationSet);
	}

	/**
	 * 心跳动的动画效果---ImageButton
	 * 
	 * @param imageButton
	 */
	public static void playHeartbeatAnimation(final ImageButton imageButton) {
		AnimationSet animationSet = new AnimationSet(true);
		animationSet.addAnimation(new ScaleAnimation(1.0f, 1.8f, 1.0f, 1.8f, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f));
		animationSet.addAnimation(new AlphaAnimation(1.0f, 0.4f));

		animationSet.setDuration(200);
		animationSet.setInterpolator(new AccelerateInterpolator());
		animationSet.setFillAfter(true);

		animationSet.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				AnimationSet animationSet = new AnimationSet(true);
				animationSet.addAnimation(new ScaleAnimation(1.8f, 1.0f, 1.8f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f,
						Animation.RELATIVE_TO_SELF, 0.5f));
				animationSet.addAnimation(new AlphaAnimation(0.4f, 1.0f));

				animationSet.setDuration(600);
				animationSet.setInterpolator(new DecelerateInterpolator());
				animationSet.setFillAfter(false);

				// 实现心跳的View
				imageButton.startAnimation(animationSet);
			}
		});

		// 实现心跳的View
		imageButton.startAnimation(animationSet);
	}

}
