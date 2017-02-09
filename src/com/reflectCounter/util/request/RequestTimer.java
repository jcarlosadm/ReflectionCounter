package com.reflectCounter.util.request;

public class RequestTimer {
	
	private long lastTimeInMillis = 0;
	
	private long timeLimitInMillis = 0;
	
	public RequestTimer(long timeLimitInMillis) {
		this.timeLimitInMillis = timeLimitInMillis;
		this.lastTimeInMillis = System.currentTimeMillis();
	}
	
	public boolean checkTime() {
		long currentTimeInMillis = System.currentTimeMillis();
		
		boolean ok = false;
		if ((currentTimeInMillis - this.lastTimeInMillis) <= this.timeLimitInMillis) {
			ok = true;
		} else {
			this.lastTimeInMillis = currentTimeInMillis;
		}
		
		return ok;
	}
	
	public long getLastTime() {
		return this.lastTimeInMillis;
	}
}
