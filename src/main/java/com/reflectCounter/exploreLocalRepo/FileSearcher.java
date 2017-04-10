package com.reflectCounter.exploreLocalRepo;

import java.lang.reflect.Method;
import java.util.List;

import com.reflectCounter.util.reports.GeneralErrors;

public class FileSearcher {

	private String filepath = "";

	public FileSearcher(String filepath) {
		this.filepath = filepath;
	}

	public void run() {
		for (ExplorerMode explorerMode : ExplorerMode.values()) {
			List<String> classNames = explorerMode.getListOfClassNames();

			for (String className : classNames) {
				try {
					this.exploreClass(className);
				} catch (Exception e) {
					String errorM = "class " + className + " not found";
					System.out.println(errorM);

					try {
						GeneralErrors.getInstance().write(errorM);
					} catch (Exception e1) {
					}

					continue;
				}
			}
		}
	}

	private void exploreClass(String className) throws ClassNotFoundException {
		Class<?> classObj = Class.forName(className);
		Method[] methods = classObj.getDeclaredMethods();

		for (Method method : methods) {
			this.findMethodUsage(className, method);
		}
	}

	private void findMethodUsage(String className, Method method) {
		// TODO Auto-generated method stub

	}

}
