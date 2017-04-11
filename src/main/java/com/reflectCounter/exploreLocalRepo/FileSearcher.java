package com.reflectCounter.exploreLocalRepo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.List;

import com.reflectCounter.util.reports.GeneralErrors;

public class FileSearcher {

	private String filepath = "";
	private ExplorerCounter explorerCounter = null;
	private String fileContent = "";

	public FileSearcher(String filepath, ExplorerCounter explorerCounter) {
		this.filepath = filepath;
		this.explorerCounter = explorerCounter;
	}

	public void run() throws Exception {
		this.fillFileContent();

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
				}
			}
		}
	}

	private void fillFileContent() throws Exception {
		BufferedReader bReader = new BufferedReader(new FileReader(new File(this.filepath)));

		String line = "";
		while ((line = bReader.readLine()) != null) {
			this.fileContent += line;
		}

		bReader.close();
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
		if (className == null || className.isEmpty() || !this.fileContent.contains("import " + className)
				|| method == null)
			return;

		String methodName = method.getName();
		int nMethodArgs = method.getParameterTypes().length;

		if (!this.fileContent.contains(methodName))
			return;

		int methodFreq = this.countMethodFreq(methodName, nMethodArgs);

		if (methodFreq > 0)
			this.explorerCounter.addCount(className, methodName, methodFreq);

	}

	private int countMethodFreq(String methodName, int nMethodArgs) {
		// TODO Auto-generated method stub
		// build regular expression
		
		return 0;
	}

	public static void main(String[] args) {
		String string = "java.lang.reflect.Modifier";

		try {
			Class<?> obj = Class.forName(string);
			Method[] methods = obj.getMethods();
			for (Method method : methods) {
				// public final native void java.lang.Object.wait(long) throws
				// java.lang.InterruptedException
				System.out.println(method.getName());
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
