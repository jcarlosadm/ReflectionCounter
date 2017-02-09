package test.java.com.reflectCounter.github.api;

import static org.junit.Assert.*;

import org.json.simple.JSONObject;
import org.junit.Test;

import com.reflectCounter.github.api.GithubSearchApi;

public class GithubSearchApiTest {
	
	private static final String WRONG_URL = "https://api.github.com/repos/danidddd-e/tesssdsros/commits";
	private static final String URL = "https://api.github.com/search/repositories?q=language:java";

	@Test
	public void testGetInstance() {
		GithubSearchApi gApi1 = GithubSearchApi.getInstance(true);
		GithubSearchApi gApi2 = GithubSearchApi.getInstance(false);
		
		assertNotNull(gApi1);
		assertNotNull(gApi2);
		assertEquals(gApi1, gApi2);
	}

	@Test
	public void testGetJsonObject() {
		GithubSearchApi githubSearchApi = GithubSearchApi.getInstance(true);
		JSONObject jsonObject = null;
		
		try {
			jsonObject = githubSearchApi.getJsonObject(WRONG_URL);
		} catch (Exception e) {
			assertNotNull(e);
		}
		
		try {
			jsonObject = githubSearchApi.getJsonObject(URL);
			assertNotNull(jsonObject);
		} catch (Exception e) {
			assertNull(e);
		}
		
		try {
			for (int i = 0; i < 40; i++) {
				jsonObject = githubSearchApi.getJsonObject(URL);
				assertNotNull(jsonObject);
			}
		} catch (Exception e) {
			e.printStackTrace();
			assertNull(e);
		}
		
		try {
			for (int i = 0; i < 40; i++) {
				jsonObject = githubSearchApi.getJsonObject(URL);
				assertNotNull(jsonObject);
			}
		} catch (Exception e) {
			e.printStackTrace();
			assertNull(e);
		}
	}

}
