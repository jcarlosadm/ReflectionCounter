package com.reflectCounter.util.reports;

import java.io.File;

import com.reflectCounter.util.Folders;

public class MethodCounter extends CsvReport {

	private static final String FILENAME = "method_counter.csv";

	private static final String SEPARATOR = ";";

	private static final String HEADER = "Project" + SEPARATOR + "ClassName" + SEPARATOR + "Method" + SEPARATOR
			+ "Count";

	private static MethodCounter instance = null;

	protected MethodCounter(File file, String separator, String header) throws Exception {
		super(file, separator, header);
	}

	public static synchronized MethodCounter getInstance() throws Exception {
		if (instance == null)
			instance = new MethodCounter(new File(Folders.OUTPUTS_FOLDER + File.separator + FILENAME), SEPARATOR, HEADER);
		return instance;
	}

	public synchronized void write(String projectName, String currentClass, String method, int count) throws Exception {
		if (count > 0)
			this.write(projectName, currentClass, method, "" + count);
	}
}
