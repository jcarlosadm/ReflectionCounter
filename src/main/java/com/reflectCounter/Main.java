package com.reflectCounter;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.reflectCounter.util.OcurrencesCsv;
import com.reflectCounter.util.ErrorReport;
import com.reflectCounter.util.Folders;

import br.com.commons.util.PropertiesManager;

public class Main {
	
	public static void main(String[] args) {
		
		if (!Folders.makeFolders()) {
			System.out.println("error to make folder");
			return;
		}
		
		OcurrencesCsv csvBuilder = null;
		ErrorReport errorReport = ErrorReport.getInstance();
		try {
			csvBuilder = new OcurrencesCsv(new File(Folders.OUTPUTS_FOLDER + File.separator + "results.csv"));
			errorReport.initialize(new File(Folders.OUTPUTS_FOLDER + File.separator + "errors.txt"));
		} catch (Exception e1) {
			System.out.println("error to create one or more of report files");
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
			errorReport.close();
		} catch (Exception e) {
			System.out.println("error to close one or more of report files");
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
