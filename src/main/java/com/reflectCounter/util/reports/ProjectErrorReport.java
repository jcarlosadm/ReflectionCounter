package com.reflectCounter.util.reports;

import java.io.File;

/**
 * List of projects with errors
 *
 */
public class ProjectErrorReport extends CsvReport {

	private static final String FILENAME = "projects_with_errors.csv";

	private static final String SEPARATOR = ";";

	private static final String HEADER = "Project" + SEPARATOR + "Error";

	private static ProjectErrorReport instance = null;

	protected ProjectErrorReport(File file, String separator, String header) throws Exception {
		super(file, separator, header);
	}

	public synchronized static ProjectErrorReport getInstance() throws Exception {
		if (instance == null)
			instance = new ProjectErrorReport(new File(FILENAME), SEPARATOR, HEADER);

		return instance;
	}
	
	public synchronized void write(String projectName, String error) throws Exception {
		this.write(projectName, error);
	}

}
