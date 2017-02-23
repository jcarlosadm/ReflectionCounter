package com.reflectCounter.util.repositoryBuilder;

import java.io.File;

public abstract class RepoBuilder {

	protected String projectFolder = "";
	
	public RepoBuilder(String projectFolder) {
		// TODO Auto-generated constructor stub
		this.projectFolder = projectFolder;
		
	}
	
	public abstract boolean build();
	
	public abstract File getJar();
	
}
