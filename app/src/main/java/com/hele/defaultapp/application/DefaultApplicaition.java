package com.hele.defaultapp.application;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.hele.defaultapp.R;
import com.hele.defaultapp.reporterror.CustomCrashHandler;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class DefaultApplicaition extends Application{
	
	/** ImageLoader default options */
	private static DisplayImageOptions options = null;
	public static DefaultApplicaition applicationContext; 
	@Override
	public void onCreate() {
		super.onCreate();
		applicationContext = this;
		CustomCrashHandler crashHandler = CustomCrashHandler.getInstance();  
		crashHandler.setCustomCrashHanler(getApplicationContext());  
		initImageLoader(getApplicationContext());
		System.out.print("helelele");
	}
	
	
	
	/**
	 * 初始化imageLoader
	 * @param context
	 */
	public static void initImageLoader(Context context) {

		if (options == null) {
			options = new DisplayImageOptions.Builder().showImageOnLoading(R.mipmap.default_image)
					.showImageForEmptyUri(R.mipmap.default_image).showImageOnFail(R.mipmap.default_image)
					.resetViewBeforeLoading(true).cacheInMemory(true).cacheOnDisk(true)
					.displayer(new FadeInBitmapDisplayer(300)).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
					.considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).build();
		}


		ImageLoaderConfiguration config ;
		ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(context)
		.threadPoolSize(3)
		.threadPriority(Thread.NORM_PRIORITY - 2)
		.denyCacheImageMultipleSizesInMemory()
		.diskCacheFileNameGenerator(new Md5FileNameGenerator())
		.tasksProcessingOrder(QueueProcessingType.FIFO)
		.memoryCache(new LruMemoryCache(1 * 1024 * 1024)) //可以通过自己的内存缓存实现
		.memoryCacheSize(2 * 1024 * 1024)
		.defaultDisplayImageOptions(options)
		.diskCache(new UnlimitedDiskCache(StorageUtils.getOwnCacheDirectory(context, DefaultApplicaition.applicationContext.getPackageName()+"/Cache")));
		config = builder.build();
		ImageLoader.getInstance().init(config);

	}
}
