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
	private String repoFolderName = "";
	
	private RepoBuilder repoBuilderInstance = null;

	public Repository(String urlProject) {
		this.urlProject = urlProject;
		this.repoFolderName = this.getRepoFolderName(urlProject);
	}

	private boolean checkIfIsGradle() {
		File file = new File(this.repoFolderName + File.separator + "build.gradle");

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
		return this.checkIfIsMaven();
	}

	public boolean isGradleProject() {
		return this.checkIfIsGradle();
	}

	public RepoBuilder getRepoBuilderInstance() {
		if (this.repoBuilderInstance == null) {
			if (this.checkIfIsGradle())
				this.repoBuilderInstance = new GradleBuilder(this.repoFolderName);
			else if(this.checkIfIsMaven())
				this.repoBuilderInstance = new MavenBuilder(this.repoFolderName);
		}
		
		return repoBuilderInstance;
	}
}
