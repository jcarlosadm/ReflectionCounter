package com.reflectCounter.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.reflectCounter.util.request.GetResponse;

public class GetResponseTest {

	private static final String WRONG_URL = "https://api.github.com/repos/danidddd-e/tesssdsros/commits";
	private static final String URL_02 = "https://api.github.com/repos/daniel-e/tetros/commits";
	private static final String URL_01 = "https://api.github.com/repos/daniel-e/tetros/commits?page=2";

	@Test
	public void testGetJsonString() {
		
		GetResponse getResponse = new GetResponse(URL_01);
		GetResponse getResponse2 = new GetResponse(URL_02);
		GetResponse getResponse3 = new GetResponse(WRONG_URL);
		
		String json;
		try {
			json = getResponse.getJsonString();
			assertNotNull(json);
			assertFalse(json.isEmpty());
		} catch (NullPointerException e) {
		} catch (Exception e) {
			json = null;
			assertNull(e);
		}
		
		try {
			json = getResponse2.getJsonString();
			assertNotNull(json);
			assertFalse(json.isEmpty());
		} catch (NullPointerException e) {
		} catch (Exception e) {
			json = null;
			assertNull(e);
		}
		
		try {
			json = getResponse3.getJsonString();
			assertNull(json);
		} catch (Exception e) {
			json = null;
			assertNotNull(e);
		}
		
	}

}
