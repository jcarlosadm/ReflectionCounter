package com.reflectCounter.util.reports;

import java.io.File;

public abstract class CsvReport extends FileReport {
	
	protected String header = "";
	
	protected String separator = ",";
	
	protected CsvReport(File file, String separator, String header) throws Exception {
		super(file);
		
		this.header = header;
		this.separator = separator;
		
		if (this.header != null && !this.header.isEmpty())
			this.write(header + System.lineSeparator());
	}
	
	protected synchronized void write(String...strings) throws Exception {
		boolean writeSeparator = false;
		for (String string : strings) {
			if (writeSeparator == true)
				this.write(this.separator);
			
			this.write(string);
			
			if (writeSeparator == false)
				writeSeparator = true;
		}
		
		this.write(System.lineSeparator());
	}
}
