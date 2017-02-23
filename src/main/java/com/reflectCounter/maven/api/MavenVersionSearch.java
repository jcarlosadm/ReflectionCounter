package com.reflectCounter.maven.api;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class MavenVersionSearch {

	private MavenVersionSearch() {
	}
	
	public static String getVersion(String group, String artifact) throws Exception {
		if (group == null || group.isEmpty() || artifact == null || artifact.isEmpty()) {
			return null;
		}
		
		JSONObject jsonObject = MavenVersionJSON.getJson(group, artifact);
		if (jsonObject == null)
			return null;
		
		JSONObject responseObj = (JSONObject) jsonObject.get("response");
		
		if (responseObj == null)
			return null;
			
		Long numFound = (Long) responseObj.get("numFound");
		if (numFound == null || numFound.longValue() <= 0)
			return null;
		
		JSONArray jsonArray = (JSONArray) responseObj.get("docs");
		if (jsonArray == null)
			return null;
		
		JSONObject firstElement = (JSONObject) jsonArray.get(0);
		if (firstElement == null)
			return null;
		
		String version = (String) firstElement.get("v");
		if (version == null || version.isEmpty())
			return null;
		
		return version;
	}
	
}
