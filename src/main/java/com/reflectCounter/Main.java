package com.reflectCounter;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.reflectCounter.util.CSVBuilder;
import com.reflectCounter.util.Folders;
import com.reflectCounter.util.PropertiesManager;

public class Main {
	
	public static void main(String[] args) {
		
		if (!Folders.makeFolders()) {
			System.out.println("error to make folder");
			return;
		}
		
		CSVBuilder csvBuilder = null;
		try {
			csvBuilder = new CSVBuilder(new File(Folders.OUTPUTS_FOLDER + File.separator + "results.csv"));
			// TODO write header of csv
		} catch (Exception e1) {
			System.out.println("error to create csv file");
			return;
		}
		
		List<String> urls = getUrls();
		if (urls == null) {
			System.out.println("error to get urls");
			return;
		}
		
		for (String url : urls) {
			try {
				(new ProjectRunner(url, csvBuilder)).run();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		
		try {
			csvBuilder.close();
		} catch (Exception e) {
			System.out.println("error to close csv file");
		}
	}
	
	private static List<String> getUrls() {
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
