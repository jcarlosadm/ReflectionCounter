package com.reflectCounter.project;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.reflectCounter.util.Folders;

public class JarChecker {

	private File folderPath = null;

	public void buildPath(String ownerRepo, String reponame) {
		this.folderPath = new File(Folders.JAR_FOLDER + File.separator + ownerRepo + File.separator + reponame);
	}
	
	private boolean folderExists() throws NullPointerException {
		if (this.folderPath == null)
			throw new NullPointerException();
		
		if (!this.folderPath.exists() || !this.folderPath.isDirectory())
			return false;
		
		return true;
	}
	
	private boolean jarExists() throws NullPointerException {
		if (this.folderExists() == false)
			return false;
		
		File[] files = this.folderPath.listFiles();
		return (files.length > 0);
	}
	
	// TODO make test
	/**
	 * get the first file in jar folder
	 * @return null if jar folder or jar not exists, and the jar in otherwise
	 */
	public File getJar() throws NullPointerException {
		if (this.jarExists() == false)
			return null;
		
		return this.folderPath.listFiles()[0];
	}
	
	// TODO make test
	public File moveJarToDefaultFolder(File jar) throws Exception {
		if (this.folderPath == null)
			throw new NullPointerException();
		
		String name = jar.getName();
		File newJarPath = new File(this.folderPath.getAbsolutePath() + File.separator + name);
		FileUtils.moveFile(jar, newJarPath);
		
		return newJarPath;
	}
}
