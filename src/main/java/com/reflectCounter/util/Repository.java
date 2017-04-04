package com.reflectCounter.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;

import com.reflectCounter.gradle.builder.GradleBuilder;
import com.reflectCounter.maven.builder.MavenBuilder;
import com.reflectCounter.util.repositoryBuilder.RepoBuilder;

public class Repository {

	private String urlProject = "";
	private String repoFolderPath = "";
	private String repoFolderName = "";
	private String owner = "";
	
	private RepoBuilder repoBuilderInstance = null;

	public Repository(String urlProject) {
		this.urlProject = urlProject;
		this.repoFolderPath = this.buildRepoFolderPath(urlProject);
		this.buildRepoFolderName();
		this.buildOwnerName();
	}

	private boolean checkIfIsGradle() {
		File file = new File(this.repoFolderPath + File.separator + "build.gradle");

		return (file.exists() && !file.isDirectory());
	}

	private boolean checkIfIsMaven() {
		File file = new File(this.repoFolderPath + File.separator + "pom.xml");

		return (file.exists() && !file.isDirectory());
	}

	public boolean cloneRepo() {

		File repofolder = new File(this.repoFolderPath);
		if (!repofolder.exists() && repofolder.mkdirs()) {
			try {
				Git.cloneRepository().setURI(urlProject).setDirectory(repofolder).call();
			} catch (Exception e) {
				try {
					FileUtils.deleteDirectory(repofolder);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				return false;
			}
		}

		return true;
	}

	private String buildRepoFolderPath(String urlProject) {
		if (this.repoFolderPath != null && !this.repoFolderPath.isEmpty()) {
			return this.repoFolderPath;
		}

		String lastRepoFolder;
		if (urlProject.endsWith(".git")) {
			lastRepoFolder = urlProject.substring(urlProject.lastIndexOf("/") + 1, urlProject.lastIndexOf("."));
		} else {
			lastRepoFolder = urlProject.substring(urlProject.lastIndexOf("/") + 1);
		}

		return (Folders.REPOS_FOLDER + File.separator + lastRepoFolder);
	}
	
	private void buildRepoFolderName() {
		this.repoFolderName = this.repoFolderPath.substring(this.repoFolderPath.lastIndexOf("/") + 1);
	}
	
	private void buildOwnerName() {
		String url = this.urlProject.substring(0, this.urlProject.lastIndexOf("/"));
		this.owner = url.substring(url.lastIndexOf("/")+1);
	}
	
	public String getOwnerName() {
		return this.owner;
	}
	
	public String getRepoFolderName() {
		return this.repoFolderName;
	}

	public String getUrlProject() {
		return this.urlProject;
	}

	public String getRepoFolderPath() {
		return this.repoFolderPath;
	}

	public boolean isMavenProject() {
		return this.checkIfIsMaven();
	}

	public boolean isGradleProject() {
		return this.checkIfIsGradle();
	}

	public RepoBuilder getRepoBuilderInstance() {
		if (this.repoBuilderInstance == null) {
			if (this.checkIfIsGradle())
				this.repoBuilderInstance = new GradleBuilder(this.repoFolderPath);
			else if(this.checkIfIsMaven())
				this.repoBuilderInstance = new MavenBuilder(this.repoFolderPath);
		}
		
		return repoBuilderInstance;
	}
}
