package com.reflectCounter.maven.api;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.reflectCounter.util.request.GetResponse;

public class MavenVersionJSON {

	private static final String URL_PREFIX = "http://search.maven.org/solrsearch/select?q=g:%22";
	private static final String AND_STRING = "%22+AND+a:%22";
	private static final String URL_SUFFIX = "%22&core=gav&rows=20&wt=json";

	private MavenVersionJSON() {
	}

	public static JSONObject getJson(String group, String artifact) throws Exception {
		String url = buildUrl(group, artifact);
		GetResponse getResponse = new GetResponse(url);

		String jsonString = getResponse.getJsonString();
		JSONObject jsonObject = (JSONObject) (new JSONParser().parse(jsonString));

		return jsonObject;
	}

	private static String buildUrl(String group, String artifact) {
		return URL_PREFIX + group + AND_STRING + artifact + URL_SUFFIX;
	}
}
