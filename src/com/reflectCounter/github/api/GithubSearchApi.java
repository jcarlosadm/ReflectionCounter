package com.reflectCounter.github.api;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.reflectCounter.util.request.GetResponse;
import com.reflectCounter.util.request.RequestTimer;

public class GithubSearchApi {
	/*
	 * TODO remove later uses GetReponse to get jsonString verify limit of 30
	 * requests per minute
	 */

	private static final int REQUEST_PERIOD_IN_MILLIS = 60000;
	private static final int AUTH_REQUESTS_PER_MINUTE = 30;
	private static final int NOAUTH_REQUESTS_PER_MINUTE = 10;

	private static GithubSearchApi instance = null;

	private RequestTimer requestTimer = new RequestTimer(REQUEST_PERIOD_IN_MILLIS);

	private int requestCount = 0;

	private boolean authMode = false;

	private GithubSearchApi() {
	}

	public static GithubSearchApi getInstance(boolean auth) {
		if (instance == null) {
			instance = new GithubSearchApi();
		}

		instance.authMode = auth;

		return instance;
	}

	public JSONObject getJsonObject(String url) throws Exception {
		GetResponse getResponse = new GetResponse(url);
		++this.requestCount;
		int requestLimit = (this.authMode == true ? AUTH_REQUESTS_PER_MINUTE : NOAUTH_REQUESTS_PER_MINUTE);

		if (this.requestCount >= requestLimit && this.requestTimer.checkTime() == true) {
			long timeToWait = REQUEST_PERIOD_IN_MILLIS - (System.currentTimeMillis() - this.requestTimer.getLastTime());
			this.requestCount = 1;
			Thread.sleep(timeToWait);
		}

		String responseString = getResponse.getJsonString();
		JSONObject json = (JSONObject) (new JSONParser()).parse(responseString);

		return json;
	}

	public static int getNumberOfRequestPerMinute(boolean auth) {
		return (auth == true ? AUTH_REQUESTS_PER_MINUTE : NOAUTH_REQUESTS_PER_MINUTE);
	}
}
