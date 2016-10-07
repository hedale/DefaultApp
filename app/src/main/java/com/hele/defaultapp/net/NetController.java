package com.hele.defaultapp.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.hele.defaultapp.Exception.INetParamException;
import com.hele.defaultapp.constant.Config;
import com.hele.defaultapp.constant.NetConstant;
import com.hele.defaultapp.listener.IOnNetResultListener;
import com.hele.defaultapp.util.CommonUtil;

import android.os.AsyncTask;

public class NetController {
	private static final int TIME_OUT = 8 * 1000;

	private String[] keys;
	private String[] values;

	private NetTask mNetTask;

	public void setKeys(String... key) {
		keys = key;
	}

	public void setValues(String... value) {
		values = value;
	}

	public void requestNet(String url, HttpMethod method, int flag, IOnNetResultListener onNetResultListener) {
		if (mNetTask != null && mNetTask.getStatus() == AsyncTask.Status.RUNNING) {
			mNetTask.cancel(true);
			mNetTask = null;
		}
		mNetTask = new NetTask(url, onNetResultListener, flag, method, keys, values);
		mNetTask.execute();
	}

	public void cancelTask() {
		if (mNetTask != null && mNetTask.getStatus() == AsyncTask.Status.RUNNING) {
			mNetTask.cancel(true);
			mNetTask = null;
		}
	}

	static class NetTask extends AsyncTask<Void, Void, String> {
		private String url;
		private IOnNetResultListener onNetResultListener;
		private int flag;
		private HttpMethod method;
		private String[] keys;
		private String[] values;

		public NetTask(String url, IOnNetResultListener onNetResultListener, int flag, HttpMethod method, String[] keys,
				String[] values) {
			this.url = url;
			this.flag = flag;
			this.onNetResultListener = onNetResultListener;
			this.method = method;
			this.keys = keys;
			this.values = values;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(Void... params) {
			if (CommonUtil.isEmpty(url)) {
				if (onNetResultListener != null)
					onNetResultListener.onNetResult(flag, null);
				return null;
			}
			StringBuffer sb = new StringBuffer();
			try {
				switch (method) {
				case GET:

					sb = requestGetUtl();
					if (Config.DEBUG_MODE) {
						System.out.println("hele>>>" + sb.toString());
					}
					if (onNetResultListener != null) {
						onNetResultListener.onNetResult(flag, sb.toString());
						return (url + "\n" + sb.toString());
					}
					break;

				case POST:
					sb = requestPostUrl();
					if (Config.DEBUG_MODE) {
						System.out.println("hele>>>" + sb.toString());
					}
					if (onNetResultListener != null) {
						onNetResultListener.onNetResult(flag, sb.toString());
						return (url + "\n" + sb.toString());
					}
					break;
				}
			} catch (INetParamException | IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (onNetResultListener != null)
				onNetResultListener.onNetComplete(flag);
		}

		private StringBuffer requestGetUtl() throws INetParamException, IOException {
			String params = getParams(keys, values, true);
			url += params;
			URL getUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) getUrl.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(TIME_OUT);
			conn.setReadTimeout(TIME_OUT);
			conn.connect();

			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			StringBuffer sb = new StringBuffer();

			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}

			reader.close();
			conn.disconnect();
			return sb;
		}

		/**
		 * post请求
		 * 
		 * @return
		 * @throws IOException
		 * @throws INetParamException
		 */
		private StringBuffer requestPostUrl() throws IOException, INetParamException {
			URL postUrl = new URL(NetConstant.URL + url);
			HttpURLConnection conn = (HttpURLConnection) postUrl.openConnection();
			String params = getParams(keys, values, false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setUseCaches(false);
			conn.setConnectTimeout(TIME_OUT);
			conn.setReadTimeout(TIME_OUT);
			conn.connect();
			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			out.writeBytes(params);

			out.flush();
			out.close();

			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			StringBuffer sb = new StringBuffer();

			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}

			reader.close();
			conn.disconnect();
			return sb;
		}

		/**
		 * 获取参数
		 * 
		 * @param keys
		 * @param values
		 * @return
		 * @throws INetParamException
		 * @throws IOException
		 */
		private String getParams(String[] keys, String[] values, boolean isGet) throws INetParamException, IOException {
			if (keys != null && values != null && keys.length != values.length) {
				throw new INetParamException("key vlues 不匹配！");
			}
			StringBuffer sb = new StringBuffer();
			if (keys != null && values != null)
				for (int i = 0; i < keys.length; i++) {
					if (i == 0 && isGet) {
						sb.append("?");
					} else if (i != 0)
						sb.append("&");
					sb.append(keys[i]);
					sb.append("=");
					sb.append(values[i]);
				}
			return sb.toString();
		}

	}
	/**
	 * Gson解析Json
	 * @param jsonObj
	 * @param cls
	 * @return
	 */
	public static <T>T parseJsonToObj(JSONObject jsonObj,Class<T> cls){
		T t = null;
		if (jsonObj == null) {
			return null;
		}
		try {
			Gson gson = new Gson();
			t = gson.fromJson(jsonObj.toString(), cls);
		} catch (Exception e) {
			return null;
		}
		return t;
	}
	
	

	/**
	 * 网络请求类型枚举
	 * @author hele
	 *
	 */
	public static enum HttpMethod {
		GET, POST
	}
}
