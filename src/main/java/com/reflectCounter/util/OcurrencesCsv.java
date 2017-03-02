package com.reflectCounter.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class OcurrencesCsv {

	private static final String SEPARATOR = ",";
	private static final String HEADER = "Project"+SEPARATOR+"ClassName"+SEPARATOR+"Method"+SEPARATOR+"Count";

	private BufferedWriter bufferedWriter = null;
	
	private String projectName = "";

	public OcurrencesCsv(File csvFile) throws Exception {
		this.bufferedWriter = new BufferedWriter(new FileWriter(csvFile));
		this.bufferedWriter.write(HEADER + System.lineSeparator());
	}

	public void write(String currentClass, String method, int count) throws Exception {
		if (count > 0)
			this.bufferedWriter.write(this.projectName + SEPARATOR + currentClass + SEPARATOR + method
					+ SEPARATOR + count + System.lineSeparator());
	}

	public void close() throws Exception {
		this.bufferedWriter.close();
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
}
