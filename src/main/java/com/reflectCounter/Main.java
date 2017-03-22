package com.reflectCounter;

import java.util.List;

import com.reflectCounter.util.Folders;
import com.reflectCounter.util.UrlsGet;
import com.reflectCounter.util.reports.CloseReports;
import com.reflectCounter.util.reports.InitializeReports;

public class Main {

	public static void main(String[] args) {

		try {
			if (!Folders.makeFolders()) {
				System.out.println("error to make folder");
				return;
			}

			InitializeReports.initialize();
			CloseReports.close();

			List<String> urls = UrlsGet.get();
			if (urls == null) {
				System.out.println("error to get urls");
				return;
			}

			for (String url : urls) {
				try {
					(new ProjectRunner(url)).run();
				} catch (Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
