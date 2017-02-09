package com.reflectCounter.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;

public class Repository {

	private String urlProject = "";
	private String repoFolderName = "";
	private boolean isMavenProject = false;
	private boolean isGradleProject = false;

	public Repository(String urlProject) {
		this.urlProject = urlProject;
		this.repoFolderName = this.getRepoFolderName(urlProject);

		this.isMavenProject = this.checkIfIsMaven();
		this.isGradleProject = this.checkIfIsGradle();
	}

	private boolean checkIfIsGradle() {
		File file = new File(this.repoFolderName + File.separator + "gradle.build");

		return (file.exists() && !file.isDirectory());
	}

	private boolean checkIfIsMaven() {
		File file = new File(this.repoFolderName + File.separator + "pom.xml");

		return (file.exists() && !file.isDirectory());
	}

	public boolean cloneRepo() {

		File repofolder = new File(this.repoFolderName);
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

	private String getRepoFolderName(String urlProject) {
		if (this.repoFolderName != null && !this.repoFolderName.isEmpty()) {
			return this.repoFolderName;
		}

		String lastRepoFolder;
		if (urlProject.endsWith(".git")) {
			lastRepoFolder = urlProject.substring(urlProject.lastIndexOf("/") + 1, urlProject.lastIndexOf("."));
		} else {
			lastRepoFolder = urlProject.substring(urlProject.lastIndexOf("/") + 1);
		}

		return (Folders.REPOS_FOLDER + File.separator + lastRepoFolder);
	}

	public String getUrlProject() {
		return this.urlProject;
	}

	public String getRepoFolderName() {
		return this.repoFolderName;
	}

	public boolean isMavenProject() {
		return isMavenProject;
	}

	public boolean isGradleProject() {
		return isGradleProject;
	}
}
