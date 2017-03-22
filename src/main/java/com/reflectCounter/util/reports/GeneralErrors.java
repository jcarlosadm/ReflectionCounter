package com.reflectCounter.util.reports;

import java.io.File;

import com.reflectCounter.util.Folders;

public class GeneralErrors extends FileReport {

	private static final String FILENAME = "general_errors.txt";
	
	private static GeneralErrors instance = null;
	
	protected GeneralErrors(File file) throws Exception {
		super(file);
	}
	
	public synchronized static GeneralErrors getInstance() throws Exception {
		if (instance == null)
			instance = new GeneralErrors(new File(Folders.REPOS_FOLDER + File.separator + FILENAME));
		return instance;
	}
	
	@Override
	public synchronized void write(String string) throws Exception {
		super.write(string + System.lineSeparator());
	}

}
