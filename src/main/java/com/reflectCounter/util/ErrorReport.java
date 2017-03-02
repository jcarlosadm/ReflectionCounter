package com.reflectCounter.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class ErrorReport {

	private BufferedWriter bufferedWriter = null;
	
	private static ErrorReport instance = null;
	
	private ErrorReport() {
	}
	
	public static ErrorReport getInstance() {
		if (instance == null) {
			instance = new ErrorReport();
		}
		
		return instance;
	}
	
	public void initialize(File file) throws Exception {
		this.bufferedWriter = new BufferedWriter(new FileWriter(file));
	}
	
	public void write(String string) throws Exception {
		this.bufferedWriter.write(string + System.lineSeparator());
	}
	
	public void close() throws Exception {
		this.bufferedWriter.close();
	}
	
}
