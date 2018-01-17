package com.talkingdata.analytics;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.tendcloud.tenddata.TalkingDataEAuth;
import com.tendcloud.tenddata.TalkingDataEAuthCallback;

public class TalkingDataSMSPlugin extends CordovaPlugin implements TalkingDataEAuthCallback {
	private Context ctx;
	private CallbackContext talkingDataEAuthCallback;
	private static final String INIT_EAUTH = "initEAuth";
	private static final String APPLY_AUTH_CODE = "applyAuthCode";
	private static final String REAPPLY_AUTH_CODE = "reapplyAuthCode";
	private static final String VERIFY_ACCOUNT = "isVerifyAccount";
	private static final String MOBILE_MATCH_ACCOUNT = "isMobileMatchAccount";
	private static final String BIND_EAUTH = "bindEAuth";
	private static final String UN_BIND_EAUTH = "unbindEAuth";
	private static final String GET_DEVICEID = "getDeviceId";
	
	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
		this.ctx = cordova.getActivity().getApplicationContext();
	}
	
	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		if (INIT_EAUTH.equals(action)) {
			String appKey = args.getString(0);
			String secretId = args.getString(1);
			TalkingDataEAuth.initEAuth(ctx, appKey, secretId);
			return true;
		} else if (APPLY_AUTH_CODE.equals(action)) {
			String countryCode = args.getString(0);
            String mobile = args.getString(1);
            String accountName = args.getString(2);
			talkingDataEAuthCallback = callbackContext;
			TalkingDataEAuth.applyAuthCode(countryCode, mobile, this, accountName);
			return true;
		} else if (REAPPLY_AUTH_CODE.equals(action)) {
            String countryCode = args.getString(0);
            String mobile = args.getString(1);
            String requestId = args.getString(2);
			String accountName = args.getString(3);
			talkingDataEAuthCallback = callbackContext;
			Log.i("TalkingData","REAPPLY_AUTH_CODE: ß"+REAPPLY_AUTH_CODE);
			TalkingDataEAuth.reapplyAuthCode(countryCode, mobile, requestId, this, accountName, TalkingDataEAuth.TDAuthCodeType.smsAuth);
			return true;
		} else if (VERIFY_ACCOUNT.equals(action)) {
			String accountName = args.getString(0);
			talkingDataEAuthCallback = callbackContext;
			TalkingDataEAuth.isVerifyAccount(accountName,this);
			return true;
		}else if(MOBILE_MATCH_ACCOUNT.equals(action)){
			String countryCode = args.getString(0);
			String mobile = args.getString(1);
			String accountName = args.getString(2);
			talkingDataEAuthCallback = callbackContext;
			TalkingDataEAuth.isMobileMatchAccount(countryCode, mobile, accountName,this);
			return true;
		}else if(BIND_EAUTH.equals(action)){
			String countryCode = args.getString(0);
			String mobile = args.getString(1);
			String authCode = args.getString(2);
			String accountName = args.getString(3);
			talkingDataEAuthCallback = callbackContext;
			TalkingDataEAuth.bindEAuth(countryCode, mobile, authCode,this, accountName);
			return true;
		}else if(UN_BIND_EAUTH.equals(action)){
			String countryCode = args.getString(0);
			String mobile = args.getString(1);
			String accountName = args.getString(2);
			talkingDataEAuthCallback = callbackContext;
			TalkingDataEAuth.unbindEAuth(countryCode, mobile, accountName, this);
			return true;
		}else if (GET_DEVICEID.equals(action)) {
			// 获取 TalkingData Device Id，并将其作为参数传入 JS 的回调函数
			String deviceId = TalkingDataEAuth.getDeviceId(ctx);
			callbackContext.success(deviceId);
			return true;
		}
		return false;
	}

	@Override
	public void onRequestSuccess(TalkingDataEAuth.TDEAuthType type, String requestId, String phoneNumber, JSONArray phoneNumSeg) {
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("type", type.ordinal());
			jsonObject.put("requestId", requestId);
			jsonObject.put("phoneNumber", phoneNumber);
			jsonObject.put("phoneNumSeg", phoneNumSeg);
			talkingDataEAuthCallback.success(jsonObject.toString());
		}catch (Throwable t) {
			t.printStackTrace();
		}
	}

	@Override
	public void onRequestFailed(TalkingDataEAuth.TDEAuthType type, int errorCode, String errorMessage) {
		Log.d("TDLog","type = "+type + " error code="+errorCode + " error message = " + errorMessage);
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("type",type.ordinal());
			jsonObject.put("errorCode",errorCode);
			jsonObject.put("errorMessage",errorMessage);
			talkingDataEAuthCallback.error(jsonObject.toString());
		}catch (Throwable t){
		    t.printStackTrace();
		}
	}
}
