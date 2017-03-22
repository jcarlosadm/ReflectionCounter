package com.reflectCounter.util.reports;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public abstract class FileReport {
	
	private BufferedWriter bufferedWriter = null;
	
	protected FileReport(File file) throws Exception {
		this.bufferedWriter = new BufferedWriter(new FileWriter(file));
	}
	
	protected synchronized void write(String string) throws Exception {
		this.bufferedWriter.write(string);
	}
	
	public synchronized void close() throws Exception {
		this.bufferedWriter.close();
	}
}
