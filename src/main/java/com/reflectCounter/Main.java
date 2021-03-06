package com.reflectCounter;

import java.util.List;

import com.reflectCounter.project.ProjectRunner;
import com.reflectCounter.util.Folders;
import com.reflectCounter.util.UrlsGet;
import com.reflectCounter.util.reports.CloseReports;

public class Main {

	public static void main(String[] args) {

		try {
			if (!Folders.makeFolders()) {
				System.out.println("error to make folder");
				return;
			}

			List<String> urls = UrlsGet.get();
			if (urls == null) {
				System.out.println("error to get urls");
				return;
			}

			for (String url : urls) {
				try {
					// TODO run in a new thread
					(new ProjectRunner(url)).run();
				} catch (Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
			
			CloseReports.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
