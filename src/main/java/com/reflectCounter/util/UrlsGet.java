package com.reflectCounter.util;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import br.com.commons.util.PropertiesManager;

public abstract class UrlsGet {

	private UrlsGet() {
	}
	
	public static List<String> get() {
		String jsonPath = PropertiesManager.getProperty("url.list.json");
		List<String> urls = new ArrayList<>();

		JSONParser parser = new JSONParser();
		JSONArray jsonArray = null;

		try {
			jsonArray = (JSONArray) parser.parse(new FileReader(jsonPath));
		} catch (Exception e) {
			System.out.println("error to get json file");
			e.printStackTrace();
			return null;
		}

		for (Object object : jsonArray) {
			JSONObject jsonObject = (JSONObject) object;
			String url = (String) jsonObject.get("url");
			urls.add(url);
		}

		return urls;
	}
	
}
