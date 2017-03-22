package com.reflectCounter.util.reports;

import java.io.File;

public class MethodErrors extends CsvReport {

	private static final String FILENAME = "method_errors.csv";

	private static final String SEPARATOR = ";";

	private static final String HEADER = "Project" + SEPARATOR + "ClassName" + SEPARATOR + "Method" + SEPARATOR
			+ "Error";

	private static MethodErrors instance = null;

	protected MethodErrors(File file, String separator, String header) throws Exception {
		super(file, separator, header);
	}

	public synchronized MethodErrors getInstance() throws Exception {
		if (instance == null)
			instance = new MethodErrors(new File(FILENAME), SEPARATOR, HEADER);
		return instance;
	}

	public synchronized void write(String projectName, String className, String methodName, String error)
			throws Exception {
		this.write(projectName, className, methodName, error);
	}

}
